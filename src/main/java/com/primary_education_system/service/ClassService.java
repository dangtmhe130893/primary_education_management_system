package com.primary_education_system.service;

import com.github.slugify.Slugify;
import com.primary_education_system.dto.ResponseCase;
import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.dto.classs.ClassDto;
import com.primary_education_system.entity.ClassEntity;
import com.primary_education_system.entity.RoomEntity;
import com.primary_education_system.entity.user.UserEntity;
import com.primary_education_system.repository.ClassRepository;
import com.primary_education_system.service.material.ClassMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ClassService {
    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private TimeScheduleService timeScheduleService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Autowired
    private PupilAccountService pupilAccountService;

    @Autowired
    private ClassMaterialService classMaterialService;

    public Page<ClassEntity> getPage(Pageable pageable, String keyword) {
        Page<ClassEntity> pageResult = classRepository.getPage(keyword, pageable);
        List<Long> listRoomId = pageResult.getContent()
                .stream()
                .map(ClassEntity::getRoomId)
                .collect(Collectors.toList());
        List<Long> listHomeroomTeacherId = pageResult.getContent()
                .stream()
                .map(ClassEntity::getHomeroomTeacherId)
                .collect(Collectors.toList());
        Map<Long, String> mapRoomNameByRoomId = roomService.getMapNameRoomByRoomId(listRoomId);
        Map<Long, String> mapHomeRoomTeacherById = userService.getMapNameTeacherById(listHomeroomTeacherId);

        pageResult.forEach(classs -> {
            classs.setRoomName(mapRoomNameByRoomId.get(classs.getRoomId()));
            classs.setHomeroomTeacher(mapHomeRoomTeacherById.get(classs.getHomeroomTeacherId()));
        });
        return pageResult;
    }

    @Transactional
    public ServerResponseDto save(ClassDto classDto) {

        Long classId = classDto.getId();
        boolean isUpdate = classId != null;

        ClassEntity classEntity;
        String oldNameClass;
        Long homeroomTeacherIdOld = null;
        Long roomIdOld = null;
        if (isUpdate) {
            classEntity = classRepository.findByIdAndIsDeletedFalse(classId);
            oldNameClass = classEntity.getName();
            homeroomTeacherIdOld = classEntity.getHomeroomTeacherId();
            roomIdOld = classEntity.getRoomId();
        } else {
            classEntity = new ClassEntity();
            classEntity.setCreatedTime(new Date());
            oldNameClass = "";
        }

        if (checkIsDuplicateNameClass(classDto.getNameClass(), oldNameClass)) {
            return new ServerResponseDto(ResponseCase.SAME_NAME_CLASS);
        }

        classEntity.setSeo(new Slugify().slugify(classDto.getNameClass()));
        classEntity.setUpdatedTime(new Date());
        classEntity.setName(classDto.getNameClass());
        classEntity.setGrade(classDto.getGrade());
        Long roomIdNew = classDto.getRoomId();
        classEntity.setRoomId(roomIdNew);
        Long homeroomTeacherIdNew = classDto.getHomeroomTeacherId();
        classEntity.setHomeroomTeacherId(homeroomTeacherIdNew);

        /* set teacherNew is homeroomTeacher and remove teacherOld is homeroomTeacher */
        if (homeroomTeacherIdOld != homeroomTeacherIdNew) {
            userService.setTeacherIsHomeRoomTeacher(homeroomTeacherIdOld, homeroomTeacherIdNew);
        }

        /* set room is selected */
        if (roomIdOld != roomIdNew) {
            roomService.setRoomIsSelected(roomIdOld, roomIdNew);
        }

        classEntity = classRepository.save(classEntity);

        if (!isUpdate) {
            /* create time schedule */
            timeScheduleService.createTimeSchedule(classEntity.getId());
        }

        return new ServerResponseDto(ResponseCase.SUCCESS);
    }

    private boolean checkIsDuplicateNameClass(String newNameClass, String oldNameClass) {
        List<ClassEntity> listClassSaved = classRepository.findByIsDeletedFalse();
        return listClassSaved
                .stream()
                .anyMatch(classSaved -> classSaved.getName().equals(newNameClass) && !oldNameClass.equals(newNameClass));
    }

    public ServerResponseDto detail(Long id) {
        ClassEntity classEntity = classRepository.findByIdAndIsDeletedFalse(id);
        if (classEntity == null) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }
        UserEntity homeroomTeacherCurrent = userService.findByIdAndIsDeletedFalse(classEntity.getHomeroomTeacherId());
        classEntity.setHomeroomTeacherCurrent(homeroomTeacherCurrent);

        RoomEntity roomCurrent = roomService.findByIdAndIsDeletedFalse(classEntity.getRoomId());
        classEntity.setRoomCurrent(roomCurrent);

        return new ServerResponseDto(ResponseCase.SUCCESS, classEntity);
    }

    @Transactional
    public ServerResponseDto delete(Long classId) {
        ClassEntity classEntity = classRepository.findByIdAndIsDeletedFalse(classId);
        if (classEntity == null) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }
        classEntity.setDeleted(true);
        classRepository.save(classEntity);

        /* delete Time schedule */
        timeScheduleService.deleteTimeScheduleByClassId(classId);

        /* update status homeroom */
        userService.updateStatusHomeroom(classEntity.getHomeroomTeacherId());

        /* update status room */
        roomService.updateStatusIsSelected(classEntity.getRoomId());

        /* delete all pupil in class */
        pupilAccountService.deleteAllPupilInClass(classId);

        /* delete Material related to class */
        classMaterialService.deleteMaterialRelatedClass(classId);

        return new ServerResponseDto(ResponseCase.SUCCESS);
    }

    public ServerResponseDto getList() {
        return new ServerResponseDto(ResponseCase.SUCCESS, classRepository.findByIsDeletedFalse());
    }

    public ServerResponseDto getListByGradeIdStr(String grade) {
        List<ClassEntity> listClass;
        if ("0".equals(grade) || "".equals(grade)) {
            listClass = classRepository.findByIsDeletedFalse();
        } else {
            listClass = classRepository.findByGradeAndIsDeletedFalse(grade);
        }
        return new ServerResponseDto(ResponseCase.SUCCESS, listClass);
    }

    public List<ClassEntity> getListByGrade(String grade) {
        return classRepository.findByGradeAndIsDeletedFalse(grade);
    }

    public Map<Long, String> getMapClassNameByClassId(List<Long> listClassId) {
        List<ClassEntity> listClassEntity = classRepository.findByIdInAndIsDeletedFalse(listClassId);
        return listClassEntity
                .stream()
                .collect(Collectors.toMap(ClassEntity::getId, ClassEntity::getName));
    }

    public Map<Long, ClassEntity> getMapClassById(List<Long> listClassId) {
        List<ClassEntity> listClassEntity = classRepository.findByIdInAndIsDeletedFalse(listClassId);
        return listClassEntity
                .stream()
                .collect(Collectors.toMap(ClassEntity::getId, Function.identity()));
    }

    public List<Long> getALlClassId() {
        List<ClassEntity> listClassEntity = classRepository.findByIsDeletedFalse();
        return listClassEntity
                .stream()
                .map(ClassEntity::getId)
                .collect(Collectors.toList());
    }

    public ClassEntity getBySeo(String seoNameClass) {
        return classRepository.findBySeoAndIsDeletedFalse(seoNameClass);
    }

    public List<Long> getListClassIdByGrade(String grade) {
        List<ClassEntity> listClass = getListByGrade(grade);
        return listClass
                .stream()
                .map(ClassEntity::getId)
                .collect(Collectors.toList());
    }

    public Map<String, List<ClassEntity>> getMapListClassByGrade(List<String> listGrade) {
        List<ClassEntity> listClass = classRepository.findByListGrade(listGrade);
        return listClass
                .stream()
                .collect(Collectors.groupingBy(ClassEntity::getGrade));
    }

    public ClassEntity findById(Long classId) {
        return classRepository.findByIdAndIsDeletedFalse(classId);
    }

    public Map<Long, String> getMapNameRoomByClassId(List<Long> listClassId) {
        List<ClassEntity> listClass = classRepository.findByIdInAndIsDeletedFalse(listClassId);
        List<Long> listRoomId = listClass
                .stream()
                .map(ClassEntity::getRoomId)
                .collect(Collectors.toList());
        Map<Long, String> mapNameRoomByRoomId = roomService.getMapNameRoomByRoomId(listRoomId);

        Map<Long, String> mapResult = new HashMap<>();
        listClass.forEach(classs -> {
            mapResult.put(classs.getId(), mapNameRoomByRoomId.get(classs.getRoomId()));
        });

        return mapResult;
    }

    public String getRoomNameByClassId(Long id) {
        ClassEntity classEntity = classRepository.findByIdAndIsDeletedFalse(id);
        return roomService.getNameById(classEntity.getRoomId());
    }

    public ClassEntity findByHomeroomTeacherId(Long homeroomTeacherId) {
        return classRepository.findByHomeroomTeacherIdAndIsDeletedFalse(homeroomTeacherId);
    }

    public Set<String> getSetClassNameByGrade(String grade) {
        return classRepository.getSetClassNameByGrade(grade);
    }

    public Map<String, Long> getMapClassIdByClassName(List<String> listNameClass) {
        List<ClassEntity> listClass = classRepository.findByListNameClass(listNameClass);
        return listClass
                .stream()
                .collect(Collectors.toMap(ClassEntity::getName, ClassEntity::getId));
    }

    public Map<Long, String> getMapGradeByClassId(List<Long> listClassId) {
        List<ClassEntity> listClass = classRepository.findByIdInAndIsDeletedFalse(listClassId);
        return listClass
                .stream()
                .collect(Collectors.toMap(ClassEntity::getId, ClassEntity::getGrade));
    }

    public String getClassNameByClassId(Long classId) {
        ClassEntity classEntity = classRepository.findByIdAndIsDeletedFalse(classId);
        if (classEntity == null) {
            return "";
        }
        return classEntity.getName();
    }

    public Long getRoomIdByClassId(Long classId) {
        ClassEntity classEntity = classRepository.findByIdAndIsDeletedFalse(classId);
        return classEntity.getRoomId();
    }

    public Map<Long, Long> getMapRoomIdByClassId(List<Long> listClassId) {
        List<ClassEntity> listClass = classRepository.findByIdInAndIsDeletedFalse(listClassId);
        return listClass
                .stream()
                .collect(Collectors.toMap(ClassEntity::getId, ClassEntity::getRoomId));
    }
}
