package com.primary_education_system.service;

import com.primary_education_system.entity.SubjectEntity;
import com.primary_education_system.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    public List<SubjectEntity> getList() {
        return subjectRepository.findByIsDeletedFalse();
    }
}
