package com.primary_education_system.api;

import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.entity.TuitionEntity;
import com.primary_education_system.service.TuitionService;
import com.primary_education_system.util.PageableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tuition")
public class Tuition_API {

    @Autowired
    private TuitionService tuitionService;

    @GetMapping("/getPage")
    public Page<TuitionEntity> getPage(@RequestParam int size, @RequestParam int page,
                                       @RequestParam String sortDir, @RequestParam String sortField) {
        Pageable pageable = PageableUtils.from(page, size, sortDir, sortField);
        return tuitionService.getPage(pageable);
    }

    @PostMapping("/save")
    public ResponseEntity<ServerResponseDto> save(@RequestBody TuitionEntity tuitionEntity) {
        return ResponseEntity.ok(tuitionService.save(tuitionEntity));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ServerResponseDto> detail(@PathVariable Long id) {
        return ResponseEntity.ok(tuitionService.detail(id));
    }

}
