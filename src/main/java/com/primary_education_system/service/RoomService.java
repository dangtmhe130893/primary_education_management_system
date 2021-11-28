package com.primary_education_system.service;

import com.primary_education_system.dto.ResponseCase;
import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.dto.room.RoomDto;
import com.primary_education_system.entity.ClassEntity;
import com.primary_education_system.entity.RoomEntity;
import com.primary_education_system.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public Page<RoomEntity> getPage(Pageable pageable, String keyword) {
        return roomRepository.getPage(keyword, pageable);
    }

    public ServerResponseDto detail(Long id) {
        RoomEntity roomEntity = roomRepository.findByIdAndIsDeletedFalse(id);
        if (roomEntity == null) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }
        return new ServerResponseDto(ResponseCase.SUCCESS, roomEntity);
    }

    public ServerResponseDto save(RoomDto roomDto) {
        Long roomId = roomDto.getId();
        boolean isUpdate = roomId != null;

        RoomEntity roomEntity;
        if (isUpdate) {
            roomEntity = roomRepository.findByIdAndIsDeletedFalse(roomId);
        } else {
            roomEntity = new RoomEntity();
            roomEntity.setCreatedTime(new Date());
        }
        roomEntity.setUpdatedTime(new Date());
        roomEntity.setName(roomDto.getName());

        roomRepository.save(roomEntity);
        return new ServerResponseDto(ResponseCase.SUCCESS);
    }

    public ServerResponseDto delete(Long id) {
        RoomEntity roomEntity = roomRepository.findByIdAndIsDeletedFalse(id);
        if (roomEntity == null) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }
        roomEntity.setDeleted(true);
        roomRepository.save(roomEntity);
        return new ServerResponseDto(ResponseCase.SUCCESS);
    }

    public Map<Long, String> getMapNameRoomByRoomId() {
        List<RoomEntity> listRoom = roomRepository.findByIsDeletedFalse();
        return getMapNameRoomByRoomIdCommon(listRoom);
    }

    public Map<Long, String> getMapNameRoomByRoomId(List<Long> listRoomId) {
        List<RoomEntity> listRoom = roomRepository.findByIdInAndIsDeletedFalse(listRoomId);
        return getMapNameRoomByRoomIdCommon(listRoom);
    }

    private Map<Long, String> getMapNameRoomByRoomIdCommon(List<RoomEntity> listRoomEntity) {
        return listRoomEntity
                .stream()
                .collect(Collectors.toMap(RoomEntity::getId, RoomEntity::getName));
    }

    public ServerResponseDto getList() {
        return new ServerResponseDto(ResponseCase.SUCCESS, roomRepository.findByIsDeletedFalse());
    }

    public ServerResponseDto getListRoomForClass() {
        return new ServerResponseDto(ResponseCase.SUCCESS, roomRepository.getListRoomForClass());
    }

    public String getNameById(Long roomId) {
        return roomRepository.getNameById(roomId);
    }


    public void setRoomIsSelected(Long roomIdOld, Long roomIdNew) {
        if (roomIdOld != null) {
            RoomEntity roomOld = roomRepository.findByIdAndIsDeletedFalse(roomIdOld);
            roomOld.setSelected(false);
            roomRepository.save(roomOld);
        }

        RoomEntity roomNew = roomRepository.findByIdAndIsDeletedFalse(roomIdNew);
        if (roomNew == null) {
            return;
        }
        roomNew.setSelected(true);
        roomRepository.save(roomNew);
    }

    public RoomEntity findByIdAndIsDeletedFalse(Long roomId) {
        return roomRepository.findByIdAndIsDeletedFalse(roomId);
    }
}
