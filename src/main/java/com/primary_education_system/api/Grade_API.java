package com.primary_education_system.api;

import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/grade")
public class Grade_API {
    @Autowired
    private GradeService gradeService;

    @GetMapping("/getList")
    public ResponseEntity<ServerResponseDto> getList() {
        return ResponseEntity.ok(gradeService.getList());
    }
}
