package com.primary_education_system.api;

import com.primary_education_system.dto.ResponseCase;
import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.dto.subject.SubjectRequestDto;
import com.primary_education_system.entity.SubjectEntity;
import com.primary_education_system.service.SubjectService;
import com.primary_education_system.util.PageableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subject")
public class Subject_API {
    @Autowired
    private SubjectService subjectService;

    @GetMapping("/list")
    public ResponseEntity<ServerResponseDto> getList() {
        return ResponseEntity.ok(new ServerResponseDto(ResponseCase.SUCCESS, subjectService.getList()));
    }

    @GetMapping("/getPage")
    public Page<SubjectEntity> getPage(@RequestParam int size, @RequestParam int page,
                                       @RequestParam String sortDir, @RequestParam String sortField,
                                       @RequestParam String keyword) {
        Pageable pageable = PageableUtils.from(page, size, sortDir, sortField);
        return subjectService.getPage(keyword, pageable);
    }

    @PostMapping("/save")
    public ResponseEntity<ServerResponseDto> save(@RequestBody SubjectRequestDto subjectRequestDto) {
        return ResponseEntity.ok(subjectService.save(subjectRequestDto));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ServerResponseDto> detail(@PathVariable Long id) {
        return ResponseEntity.ok(subjectService.detail(id));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<ServerResponseDto> delete(@PathVariable Long id) {
        return ResponseEntity.ok(subjectService.delete(id));
    }
}
