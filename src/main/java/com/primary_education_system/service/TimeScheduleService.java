package com.primary_education_system.service;

import com.primary_education_system.dto.ResponseCase;
import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.dto.time_schedule.InfoTimeScheduleTeacherDto;
import com.primary_education_system.dto.time_schedule.TimeScheduleRequestDto;
import com.primary_education_system.dto.time_schedule.TimeScheduleResponseDto;
import com.primary_education_system.entity.ClassEntity;
import com.primary_education_system.entity.FrameTimeScheduleEntity;
import com.primary_education_system.entity.TimeScheduleEntity;
import com.primary_education_system.entity.pupil.PupilAccountEntity;
import com.primary_education_system.entity.user.UserEntity;
import com.primary_education_system.enum_type.DayOfWeek;
import com.primary_education_system.repository.TimeScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TimeScheduleService {
    @Autowired
    private TimeScheduleRepository timeScheduleRepository;

    @Autowired
    private FrameTimeScheduleService frameTimeScheduleService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private UserService userService;

    @Autowired
    private ClassService classService;

    @Autowired
    private PupilAccountService pupilAccountService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private SubjectTeacherService subjectTeacherService;

    public ServerResponseDto findByPupilId(Long pupilId) {
        PupilAccountEntity pupil = pupilAccountService.findById(pupilId);
        if (pupil == null) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }
        return getTimeSchedule(pupil.getClassId());
    }

    public ServerResponseDto getTimeSchedule(Long classId) {
        ClassEntity classEntity = classService.findById(classId);
        String roomNameDefault = classService.getRoomNameByClassId(classEntity.getId());
        String homeRoomTeacher = userService.getNameUserById(classEntity.getHomeroomTeacherId());

        List<TimeScheduleEntity> listTimeSchedule = timeScheduleRepository.findByClassIdAndIsDeletedFalse(classId);

        List<Long> listSubjectId = listTimeSchedule
                .stream()
                .map(TimeScheduleEntity::getSubjectId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<Long> listTeacherId = listTimeSchedule
                .stream()
                .map(TimeScheduleEntity::getTeacherId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Map<Long, String> mapNameSubjectById = subjectService.getMapNameSubjectById(listSubjectId);
        Map<Long, String> mapNameTeacherById = userService.getMapNameTeacherById(listTeacherId);

        listTimeSchedule.forEach(timeScheduleEntity -> {
            timeScheduleEntity.setNameSubject(mapNameSubjectById.getOrDefault(timeScheduleEntity.getSubjectId(), ""));
            timeScheduleEntity.setNameTeacher(mapNameTeacherById.getOrDefault(timeScheduleEntity.getTeacherId(), ""));
        });
        setRoomNameForTimeSchedule(listTimeSchedule);

        TimeScheduleResponseDto result = new TimeScheduleResponseDto(homeRoomTeacher, roomNameDefault, classEntity.getName(), listTimeSchedule);
        return new ServerResponseDto(ResponseCase.SUCCESS, result);
    }

    public ServerResponseDto getTimeScheduleForTeacher(Long teacherId) {
        List<TimeScheduleEntity> listTimeSchedule = timeScheduleRepository.findByTeacherIdAndIsDeletedFalse(teacherId);

        List<Long> listClassId = listTimeSchedule
                .stream()
                .map(TimeScheduleEntity::getClassId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<Long> listSubjectId = listTimeSchedule
                .stream()
                .map(TimeScheduleEntity::getSubjectId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Map<Long, ClassEntity> mapClassById = classService.getMapClassById(listClassId);
        Map<Long, String> mapNameSubjectBySubjectId = subjectService.getMapNameSubjectById(listSubjectId);

        listTimeSchedule.forEach(timeScheduleEntity -> {
            timeScheduleEntity.setClassName(mapClassById.get(timeScheduleEntity.getClassId()).getName());
            timeScheduleEntity.setNameSubject(mapNameSubjectBySubjectId.get(timeScheduleEntity.getSubjectId()));
        });
        setRoomNameForTimeSchedule(listTimeSchedule);

        return new ServerResponseDto(ResponseCase.SUCCESS, listTimeSchedule);
    }

    private List<TimeScheduleEntity> setRoomNameForTimeSchedule(List<TimeScheduleEntity> listTimeSchedule) {
        List<Long> listClassId = listTimeSchedule
                .stream()
                .map(TimeScheduleEntity::getClassId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<Long> listRoomId = listTimeSchedule
                .stream()
                .map(TimeScheduleEntity::getRoomId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Map<Long, String> mapNameRoomByRoomId = roomService.getMapNameRoomByRoomId(listRoomId);
        Map<Long, String> mapNameRoomByClassId = classService.getMapNameRoomByClassId(listClassId);

        listTimeSchedule.forEach(timeScheduleEntity -> {
            Long roomId = timeScheduleEntity.getRoomId();
            if (roomId != null) {
                // roomName get by roomId
                timeScheduleEntity.setShowRoomName(true);
                timeScheduleEntity.setRoomName(mapNameRoomByRoomId.get(roomId));
            } else {
                // roomName default
                timeScheduleEntity.setShowRoomName(false);
                timeScheduleEntity.setRoomName(mapNameRoomByClassId.get(timeScheduleEntity.getClassId()));
            }
        });
        return listTimeSchedule;
    }

    public void createTimeSchedule(Long classId) {
        List<FrameTimeScheduleEntity> listFrameTime = frameTimeScheduleService.findAll();
        List<TimeScheduleEntity> listTimeSchedule = new ArrayList<>();

        for (FrameTimeScheduleEntity frameTime : listFrameTime) {
            Long frameTimeId = frameTime.getId();
            for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
                TimeScheduleEntity timeSchedule = new TimeScheduleEntity(dayOfWeek, frameTimeId, null, null, classId);
                timeSchedule.setCreatedTime(new Date());
                timeSchedule.setUpdatedTime(new Date());
                listTimeSchedule.add(timeSchedule);
            }
        }

        timeScheduleRepository.save(listTimeSchedule);
    }

    public ServerResponseDto save(TimeScheduleRequestDto timeScheduleRequestDto) {
        TimeScheduleEntity timeScheduleEntity = timeScheduleRepository
                .findByIdAndIsDeletedFalse(timeScheduleRequestDto.getTimeScheduleId());

        if (timeScheduleEntity == null) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }

        /*
         * trùng lịch nếu: + khác classId
         *                 + trùng dayOfWeek
         *                 + trùng frameTimeId
         *                 + trùng teacherId
         * */
        Long classId = timeScheduleEntity.getClassId();
        DayOfWeek dayOfWeek = timeScheduleEntity.getDayOfWeek();
        Long frameTimeId = timeScheduleEntity.getFrameTimeId();
        Long teacherId = timeScheduleRequestDto.getTeacherId();

        if (checkSameTimeSchedule(classId, dayOfWeek, frameTimeId, teacherId)) {
            UserEntity teacherDuplicate = userService.findByIdAndIsDeletedFalse(teacherId);
            return new ServerResponseDto(ResponseCase.SAME_TIME_SCHEDULE, teacherDuplicate.getName());
        }

        /* check trùng phòng học */
        Long roomId = timeScheduleRequestDto.getRoomId();
        if (roomId == null) {
            roomId = classService.getRoomIdByClassId(classId);
        }
        if (checkSameRoom(classId, dayOfWeek, frameTimeId, roomId)) {
            String nameRoomSame = roomService.getNameById(roomId);
            return new ServerResponseDto(ResponseCase.SAME_NAME_ROOM, nameRoomSame);
        }


        timeScheduleEntity.setSubjectId(timeScheduleRequestDto.getSubjectId());
        timeScheduleEntity.setTeacherId(teacherId);
        timeScheduleEntity.setRoomId(roomId);

        timeScheduleRepository.save(timeScheduleEntity);
        return new ServerResponseDto(ResponseCase.SUCCESS);
    }

    private boolean checkSameRoom(Long classId, DayOfWeek dayOfWeek, Long frameTimeId, Long roomId) {
        List<TimeScheduleEntity> listTimeScheduleToCheck = timeScheduleRepository.findToCheckSameRoom(classId, dayOfWeek, frameTimeId);

        Set<Long> listRoomId = listTimeScheduleToCheck
                .stream()
                .map(TimeScheduleEntity::getRoomId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        List<Long> listClassId = listTimeScheduleToCheck
                .stream()
                .filter(t -> t.getRoomId() == null)
                .map(TimeScheduleEntity::getClassId)
                .collect(Collectors.toList());

        Map<Long, Long> mapRoomIdByClassId = classService.getMapRoomIdByClassId(listClassId);
        listClassId.forEach(classsId -> listRoomId.add(mapRoomIdByClassId.get(classsId)));

        return listRoomId.contains(roomId);
    }

    private boolean checkSameTimeSchedule(Long classId, DayOfWeek dayOfWeek, Long frameTimeId, Long teacherId) {
        int numberSameTimeSchedule = (int) timeScheduleRepository.countSameTimeSchedule(classId, dayOfWeek, frameTimeId, teacherId);
        return numberSameTimeSchedule > 0;
    }

    public ServerResponseDto detail(Long timeScheduleId) {
        TimeScheduleEntity timeScheduleEntity = timeScheduleRepository.findByIdAndIsDeletedFalse(timeScheduleId);
        if (timeScheduleEntity == null) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }

        String nameFrameTime = frameTimeScheduleService.getNameFrameTimeById(timeScheduleEntity.getFrameTimeId());
        timeScheduleEntity.setNameFrameTime(nameFrameTime);

        Long subjectId = timeScheduleEntity.getSubjectId();
        List<UserEntity> listTeacher = subjectTeacherService.getListTeacherBySubjectId(subjectId);
        timeScheduleEntity.setListTeacher(listTeacher);
        return new ServerResponseDto(ResponseCase.SUCCESS, timeScheduleEntity);
    }

    public void deleteTimeScheduleByClassId(Long classId) {
        List<TimeScheduleEntity> listTimeSchedule = timeScheduleRepository.findByClassIdAndIsDeletedFalse(classId);
        listTimeSchedule.forEach(timeSchedule -> timeSchedule.setDeleted(true));
        timeScheduleRepository.save(listTimeSchedule);
    }

    public Page<InfoTimeScheduleTeacherDto> getInfoTimeScheduleTeacher(Long teacherId, Pageable pageable) {
        Page<InfoTimeScheduleTeacherDto> page = timeScheduleRepository.getInfoTimeScheduleTeacher(teacherId, pageable);

        List<Long> listClassId = page.getContent()
                .stream()
                .map(InfoTimeScheduleTeacherDto::getClassId)
                .collect(Collectors.toList());
        Map<Long, ClassEntity> mapClassById = classService.getMapClassById(listClassId);

        Map<Long, Integer> mapNumberPupilByClassId = pupilAccountService.getMapNumberPupilByClassId(listClassId);

        Map<Long, String> mapNameRoomByRoomId = roomService.getMapNameRoomByRoomId();

        page.forEach(row -> {
            ClassEntity classEntity = mapClassById.get(row.getClassId());
            row.setNameClass(classEntity.getName());
            row.setSeoNameClass(classEntity.getSeo());
            row.setNameRoom(mapNameRoomByRoomId.get(classEntity.getRoomId()));
            row.setNumberPupil(mapNumberPupilByClassId.getOrDefault(classEntity.getId(), 0));
        });
        return page;
    }
}
