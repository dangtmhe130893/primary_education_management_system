package com.primary_education_system.service;

import com.primary_education_system.entity.pupil.PupilAccountEntity;
import com.primary_education_system.repository.PupilAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PupilAccountService {

    @Autowired
    PupilAccountRepository repository;

    public Page<PupilAccountEntity> getPagePupilAccount(Pageable pageable, String keyword) {
        return repository.getPagePupilAccount(keyword, pageable);
    }

}
