package com.primary_education_system.repository;

import com.primary_education_system.entity.user.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    List<RoleEntity> findByNameIn(Collection<String> listName);
}
