package com.primary_education_system.api;

import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.dto.pupil_account.PupilAccountDto;
import com.primary_education_system.entity.pupil.PupilAccountEntity;
import com.primary_education_system.service.PupilAccountService;
import com.primary_education_system.util.PageableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pupil_account")
public class PupilAccount_API {

    @Autowired
    private PupilAccountService pupilAccountService;

    @GetMapping("/getPage")
    public Page<PupilAccountEntity> getPagePupilAccount(@RequestParam String search,
                                                        @RequestParam int size, @RequestParam int page,
                                                        @RequestParam String sortDir, @RequestParam String sortField,
                                                        @RequestParam Long classId,
                                                        @RequestParam String grade) {
        Pageable pageable = PageableUtils.from(page, size, sortDir, sortField);
        return pupilAccountService.getPagePupilAccount(pageable, search, grade, classId);
    }

    @PostMapping("/save")
    public ResponseEntity<ServerResponseDto> save(@RequestBody PupilAccountDto pupilAccountDto) {
        return ResponseEntity.ok(pupilAccountService.save(pupilAccountDto));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ServerResponseDto> detail(@PathVariable Long id) {
        return ResponseEntity.ok(pupilAccountService.detail(id));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<ServerResponseDto> delete(@PathVariable Long id) {
        return ResponseEntity.ok(pupilAccountService.delete(id));
    }

}
