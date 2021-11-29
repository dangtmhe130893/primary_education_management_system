package com.primary_education_system.service;

import com.google.common.collect.Lists;
import com.primary_education_system.entity.SubjectEntity;
import com.primary_education_system.entity.SubjectTeacherEntity;
import com.primary_education_system.entity.user.UserEntity;
import com.primary_education_system.repository.SubjectTeacherRepository;
import com.primary_education_system.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SubjectTeacherService {
    @Autowired
    private SubjectTeacherRepository subjectTeacherRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    public Map<Long, List<UserEntity>> getMapListTeacherBySubjectId(List<Long> listSubjectId) {
        List<SubjectTeacherEntity> listSubjectTeacher = subjectTeacherRepository.findByListSubjectId(listSubjectId);
        List<Long> listTeacherId = listSubjectTeacher
                .stream()
                .map(SubjectTeacherEntity::getTeacherId)
                .collect(Collectors.toList());
        List<UserEntity> listTeacher = userService.findByIdInAndIsDeletedFalse(listTeacherId);
        Map<Long, UserEntity> mapUserById = listTeacher
                .stream()
                .collect(Collectors.toMap(UserEntity::getId, Function.identity()));

        return listSubjectTeacher
                .stream()
                .collect(Collectors.groupingBy(SubjectTeacherEntity::getSubjectId,
                        Collectors.mapping(st -> mapUserById.get(st.getTeacherId()), Collectors.toList())));

    }

    public void removeTeachSubjectId(Long subjectId) {
        subjectTeacherRepository.deleteBySubjectId(subjectId);
    }


    public void setTeacherForSubject(Long subjectId, String listTeacherIdString) {
        String[] arrayId = listTeacherIdString.split(",");
        List<Long> listTeacherIdAfter = Arrays.stream(arrayId)
                .map(Long::parseLong)
                .collect(Collectors.toList());

        List<UserEntity> listTeacherEntityBefore = getListTeacherBySubjectId(subjectId);
        List<Long> listTeacherIdBefore = listTeacherEntityBefore
                .stream()
                .map(UserEntity::getId)
                .collect(Collectors.toList());

        removeTeacherForSubject(listTeacherIdBefore, listTeacherIdAfter, subjectId);

        addTeachForSubject(listTeacherIdBefore, listTeacherIdAfter, subjectId);

    }

    public void addTeachForSubject(List<Long> listTeacherIdBefore, List<Long> listTeacherIdAfter, Long subjectId) {
        List<Long> listTeacherIdAdded = new ArrayList<>();
        listTeacherIdAfter.forEach(idAfter -> {
            if (!listTeacherIdBefore.contains(idAfter)) {
                listTeacherIdAdded.add(idAfter);
            }
        });

        if (listTeacherIdAdded.isEmpty()) {
            return;
        }

        List<SubjectTeacherEntity> listSubjectTeacherAdded = Lists.newArrayListWithCapacity(listTeacherIdAdded.size());
        listTeacherIdAdded.forEach(teacherId -> {
            SubjectTeacherEntity subjectTeacherEntity = new SubjectTeacherEntity(subjectId, teacherId);
            listSubjectTeacherAdded.add(subjectTeacherEntity);
        });

        subjectTeacherRepository.save(listSubjectTeacherAdded);
    }

    private void removeTeacherForSubject(List<Long> listTeacherIdBefore, List<Long> listTeacherIdAfter, Long subjectId) {
        if (listTeacherIdBefore.isEmpty()) {
            return;
        }
        List<Long> listTeacherIdRemoved = Lists.newArrayListWithExpectedSize(listTeacherIdBefore.size());
        listTeacherIdBefore.forEach(idBefore -> {
            if (!listTeacherIdAfter.contains(idBefore)) {
                listTeacherIdRemoved.add(idBefore);
            }
        });
        if (listTeacherIdRemoved.isEmpty()) {
            return;
        }

        subjectTeacherRepository.deleteBySubjectIdAndListTeacherId(subjectId, listTeacherIdRemoved);

    }

    public List<UserEntity> getListTeacherBySubjectId(Long subjectId) {
        List<SubjectTeacherEntity> listSubjectTeacher = subjectTeacherRepository.findBySubjectId(subjectId);
        List<Long> listTeacherId = listSubjectTeacher
                .stream()
                .map(SubjectTeacherEntity::getTeacherId)
                .collect(Collectors.toList());
        return userService.findByIdInAndIsDeletedFalse(listTeacherId);
    }
}
