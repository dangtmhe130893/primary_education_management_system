package com.primary_education_system.repository;

import com.primary_education_system.entity.ClassEntity;
import com.primary_education_system.entity.RoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    @Query(value = "select r from RoomEntity r " +
            "where r.name like concat('%', ?1, '%') and r.isDeleted = false ")
    Page<RoomEntity> getPage(String keyword, Pageable pageable);

    RoomEntity findByIdAndIsDeletedFalse(Long id);

    List<RoomEntity> findByIsDeletedFalse();

    List<RoomEntity> findByIdInAndIsDeletedFalse(List<Long> listRoomId);

    @Query(value = "select r.name from RoomEntity r " +
            "where r.id = ?1 and r.isDeleted = false")
    String getNameById(Long roomId);
}
