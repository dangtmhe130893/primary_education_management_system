package com.primary_education_system.api;

import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.dto.history_pay_tuition.RevenueTuitionDto;
import com.primary_education_system.dto.history_pay_tuition.UpdateTuitionRequestDto;
import com.primary_education_system.service.HistoryPayTuitionService;
import com.primary_education_system.util.PageableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/history_pay_tuition")
public class HistoryPayTuition_API {

    @Autowired
    private HistoryPayTuitionService historyPayTuitionService;

    @GetMapping("/getPage")
    public Page<RevenueTuitionDto> getPage(@RequestParam int size, @RequestParam int page,
                                           @RequestParam String sortDir, @RequestParam String sortField,
                                           @RequestParam String type,
                                           @RequestParam(name = "classId", required = false) Long classId,
                                           @RequestParam(name = "keyword", required = false) String keyword) {
        Pageable pageable = PageableUtils.from(page, size, sortDir, sortField);
        return historyPayTuitionService.getPage(type, classId, keyword, pageable);
    }

    @GetMapping("/detail/{pupilId}")
    public ResponseEntity<ServerResponseDto> detail(@PathVariable Long pupilId) {
        return ResponseEntity.ok(historyPayTuitionService.detail(pupilId));
    }

    @PostMapping("/updateTuition")
    public ResponseEntity<ServerResponseDto> updateTuition(@RequestBody UpdateTuitionRequestDto tuitionRequestDto) {
        return ResponseEntity.ok(historyPayTuitionService.updateTuition(tuitionRequestDto));
    }
}
