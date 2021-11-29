package com.primary_education_system.api;

import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.dto.room.RoomDto;
import com.primary_education_system.entity.RoomEntity;
import com.primary_education_system.service.RoomService;
import com.primary_education_system.util.PageableUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/room")
public class Room_API {
    @Autowired
    private RoomService roomService;

    @GetMapping("/getPage")
    public Page<RoomEntity> getPage(@RequestParam int size, @RequestParam int page,
                                    @RequestParam String sortDir, @RequestParam String sortField,
                                    @RequestParam String keyword) {
        Pageable pageable = PageableUtils.from(page, size, sortDir, sortField);
        return roomService.getPage(pageable, keyword);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ServerResponseDto> detail(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.detail(id));
    }

    @PostMapping("/save")
    public ResponseEntity<ServerResponseDto> save(@RequestBody RoomDto roomDto) {
        return ResponseEntity.ok(roomService.save(roomDto));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<ServerResponseDto> delete(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.delete(id));
    }

    @GetMapping("/getList")
    public ResponseEntity<ServerResponseDto> getList() {
        return ResponseEntity.ok(roomService.getList());
    }

    @GetMapping("/getListForClass")
    public ResponseEntity<ServerResponseDto> getListRoomForClass() {
        return ResponseEntity.ok(roomService.getListRoomForClass());
    }

    @PostMapping("/importExcel")
    public ResponseEntity<ServerResponseDto> importExcel(@RequestPart MultipartFile file) throws IOException, InvalidFormatException {
        return ResponseEntity.ok(roomService.importExcel(file));
    }

    @PostMapping("/saveList")
    public ResponseEntity<ServerResponseDto> saveList(@RequestBody List<String> listRoom) {
        return ResponseEntity.ok(roomService.saveList(listRoom));
    }
}
