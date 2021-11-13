package com.primary_education_system.api;

import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.dto.classs.ClassDto;
import com.primary_education_system.entity.ClassEntity;
import com.primary_education_system.service.ClassService;
import com.primary_education_system.util.PageableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

@RestController
@RequestMapping("/api/class")
public class Class_API {

    @Autowired
    private ClassService classService;

    @GetMapping("/getPage")
    public Page<ClassEntity> getPage(@RequestParam int size, @RequestParam int page,
                                     @RequestParam String sortDir, @RequestParam String sortField) {
        Pageable pageable = PageableUtils.from(page, size, sortDir, sortField);
        return classService.getPage(pageable);
    }

    @GetMapping("/getList")
    public ResponseEntity<ServerResponseDto> getList() {
        return ResponseEntity.ok(classService.getList());
    }

    @PostMapping("/save")
    public ResponseEntity<ServerResponseDto> save(@RequestBody ClassDto classDto) {
        return ResponseEntity.ok(classService.save(classDto));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ServerResponseDto> detail(@PathVariable Long id) {
        return ResponseEntity.ok(classService.detail(id));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<ServerResponseDto> delete(@PathVariable Long id) {
        return ResponseEntity.ok(classService.delete(id));
    }

}
