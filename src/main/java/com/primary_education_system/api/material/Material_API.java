package com.primary_education_system.api.material;

import com.primary_education_system.config.security.CustomUserDetails;
import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.dto.material.SaveMaterialDto;
import com.primary_education_system.entity.material.MaterialEntity;
import com.primary_education_system.service.material.MaterialService;
import com.primary_education_system.util.PageableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/material")
public class Material_API {

    @Autowired
    private MaterialService materialService;

    @GetMapping("/getPage")
    public Page<MaterialEntity> getPageMaterial(@RequestParam int size, @RequestParam int page,
                                                @RequestParam String sortDir, @RequestParam String sortField,
                                                @RequestParam String search,
                                                @RequestParam Long subjectId,
                                                @RequestParam String grade,
                                                @RequestParam String type) {
        Pageable pageable = PageableUtils.from(page, size, sortDir, sortField);
        return materialService.getPageMaterial(pageable, search, subjectId, grade, type);
    }

    @PostMapping("/save")
    public ResponseEntity<ServerResponseDto> save(@ModelAttribute SaveMaterialDto saveDto,
                                                  @AuthenticationPrincipal CustomUserDetails currentUser) throws IOException {
        return ResponseEntity.ok(materialService.save(saveDto, currentUser.getUserId()));
    }


    @GetMapping("/download/{code}")
    public ResponseEntity<Object> download(@PathVariable String code) throws IOException {
        return materialService.downloadFile(code);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ServerResponseDto> detail(@PathVariable Long id) {
        return ResponseEntity.ok(materialService.detail(id));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<ServerResponseDto> delete(@PathVariable Long id) {
        return ResponseEntity.ok(materialService.delete(id));
    }

}
