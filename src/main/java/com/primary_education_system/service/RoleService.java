package com.primary_education_system.service;

import com.primary_education_system.entity.user.RoleEntity;
import com.primary_education_system.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;


    public List<RoleEntity> findByNameIn(List<String> listName) {
        return roleRepository.findByNameIn(listName);
    }
}
