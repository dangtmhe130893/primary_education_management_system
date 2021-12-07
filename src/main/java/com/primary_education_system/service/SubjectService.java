package com.primary_education_system.service;

import com.primary_education_system.dto.ResponseCase;
import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.dto.subject.SubjectRequestDto;
import com.primary_education_system.entity.SubjectEntity;
import com.primary_education_system.entity.user.UserEntity;
import com.primary_education_system.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectTeacherService subjectTeacherService;

    public List<SubjectEntity> getList() {
        return subjectRepository.findByIsDeletedFalse();
    }

    public Page<SubjectEntity> getPage(String keyword, Pageable pageable) {
        Page<SubjectEntity> pageSubject = subjectRepository.getPage(keyword, pageable);
        List<Long> listSubjectId = pageSubject.getContent().stream()
                .map(SubjectEntity::getId)
                .collect(Collectors.toList());

        if (listSubjectId.isEmpty()) {
            return pageSubject;
        }

        Map<Long, List<UserEntity>> mapListTeacherBySubjectId = subjectTeacherService.getMapListTeacherBySubjectId(listSubjectId);
        pageSubject.forEach(subjectEntity -> subjectEntity
                .setListTeacherTeaching(mapListTeacherBySubjectId.get(subjectEntity.getId())));
        return pageSubject;
    }

    @Transactional
    public ServerResponseDto save(SubjectRequestDto subjectRequestDto) {
        Long subjectId = subjectRequestDto.getId();
        boolean isUpdate = subjectId != null;

        SubjectEntity subject;
        if (isUpdate) {
            subject = subjectRepository.findByIdAndIsDeletedFalse(subjectId);
        } else {
            subject = new SubjectEntity();
            subject.setCreatedTime(new Date());
        }
        subject.setUpdatedTime(new Date());
        subject.setName(subjectRequestDto.getSubject());
        subject = subjectRepository.save(subject);

        if (!"".equals(subjectRequestDto.getListTeacherIdString())) {
            subjectTeacherService.setTeacherForSubject(subject.getId(), subjectRequestDto.getListTeacherIdString());
        }
        return new ServerResponseDto(ResponseCase.SUCCESS);
    }

    public ServerResponseDto detail(Long id) {
        SubjectEntity subject = subjectRepository.findByIdAndIsDeletedFalse(id);
        List<UserEntity> listTeacherTeaching = subjectTeacherService.getListTeacherBySubjectId(subject.getId());

        List<UserEntity> listTeachCanTeach = userService.getListTeacher();

        subject.setListTeacherTeaching(listTeacherTeaching);
        subject.setListTeacherCanTeach(listTeachCanTeach);
        return new ServerResponseDto(ResponseCase.SUCCESS, subject);
    }

    @Transactional
    public ServerResponseDto delete(Long subjectId) {
        SubjectEntity subject = subjectRepository.findByIdAndIsDeletedFalse(subjectId);
        subject.setDeleted(true);
        subjectRepository.save(subject);

        subjectTeacherService.removeTeachSubjectId(subjectId);

        return new ServerResponseDto(ResponseCase.SUCCESS);
    }

    public Map<Long, String> getMapNameSubjectById(List<Long> listSubjectId) {
        List<SubjectEntity> listSubjectEntity = subjectRepository.findByIdInAndIsDeletedFalse(listSubjectId);
        return listSubjectEntity
                .stream()
                .collect(Collectors.toMap(SubjectEntity::getId, SubjectEntity::getName));
    }

    public List<SubjectEntity> getListByUserId(Long userId) {
        if (userId == 1) {
            return subjectRepository.findByIsDeletedFalse();
        }
        UserEntity userEntity = userService.getDetail(userId);
        if (userEntity == null) {
            return null;
        }
        List<SubjectEntity> listSubject = subjectRepository.getListSubjectByTeacherId(userId);
        return listSubject;
    }

    public String getNameSubjectById(Long teachSubjectId) {
        return subjectRepository.getNameSubjectById(teachSubjectId);
    }

}
