package com.primary_education_system.api;

import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.dto.pupil_account.PupilAccountDto;
import com.primary_education_system.dto.pupil_account.PupilAccountImportDto;
import com.primary_education_system.entity.pupil.PupilAccountEntity;
import com.primary_education_system.service.PupilAccountService;
import com.primary_education_system.util.PageableUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

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
    public ResponseEntity<ServerResponseDto> save(@RequestBody PupilAccountDto pupilAccountDto) throws ParseException {
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

    @PostMapping("/importExcel")
    public ResponseEntity<ServerResponseDto> importExcel(@RequestPart MultipartFile file) throws IOException, InvalidFormatException {
        return ResponseEntity.ok(pupilAccountService.importExcel(file));
    }

    @PostMapping("/saveList")
    public ResponseEntity<ServerResponseDto> saveList(@RequestBody List<PupilAccountImportDto> listPupilAccount) {
        return ResponseEntity.ok(pupilAccountService.saveList(listPupilAccount));
    }
}
