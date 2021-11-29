package com.primary_education_system.service;

import com.google.common.collect.Lists;
import com.primary_education_system.dto.ResponseCase;
import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.dto.room.RoomDto;
import com.primary_education_system.entity.RoomEntity;
import com.primary_education_system.repository.RoomRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
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

    public ServerResponseDto importExcel(MultipartFile file) throws IOException, InvalidFormatException {

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter df = new DataFormatter();

        Set<String> setRoom = new HashSet<>();
        int numberRow = 0;
        for (Row row : sheet) {
            String nameRoom = df.formatCellValue(row.getCell(0)).trim();
            if ("".equals(nameRoom)) continue;

            if (isRoomExist(nameRoom)) {
                return new ServerResponseDto(ResponseCase.NAME_ROOM_EXIST);
            }
            setRoom.add(nameRoom);
            numberRow++;
        }

        if (numberRow > setRoom.size()) {
            return new ServerResponseDto(ResponseCase.SAME_NAME_ROOM);
        }
        return new ServerResponseDto(ResponseCase.SUCCESS, setRoom);
    }

    private boolean isRoomExist(String roomName) {
        List<RoomEntity> listRoomExist = roomRepository.findByIsDeletedFalse();
        List<String> listNameRoomExist = listRoomExist
                .stream()
                .map(RoomEntity::getName)
                .collect(Collectors.toList());

        return listNameRoomExist
                .stream()
                .anyMatch(r -> r.equals(roomName));
    }

    public ServerResponseDto saveList(List<String> listRoom) {
        List<RoomEntity> listRoomEntity = Lists.newArrayListWithCapacity(listRoom.size());
        listRoom.forEach(room -> {
            RoomEntity roomEntity = new RoomEntity(room, new Date(), new Date());
            listRoomEntity.add(roomEntity);
        });

        roomRepository.save(listRoomEntity);
        return new ServerResponseDto(ResponseCase.SUCCESS);
    }
}
