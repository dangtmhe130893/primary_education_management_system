package com.primary_education_system.api;

import com.primary_education_system.config.security.CustomUserDetails;
import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.service.HistoryPayTuitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pupil")
public class Pupil_API {

    @Autowired
    private HistoryPayTuitionService historyPayTuitionService;

    @GetMapping("/tuition")
    public ResponseEntity<ServerResponseDto> getTuition(@AuthenticationPrincipal CustomUserDetails currentUser) {
        return ResponseEntity.ok(historyPayTuitionService.findByPupilId(currentUser.getUserId()));
    }

    @GetMapping("/time_table")
    public ResponseEntity<ServerResponseDto> getTimeTable(@AuthenticationPrincipal CustomUserDetails currentUser) {
        return ResponseEntity.ok(historyPayTuitionService.findByPupilId(currentUser.getUserId()));
    }

}
