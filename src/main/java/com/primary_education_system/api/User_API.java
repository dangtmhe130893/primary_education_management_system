package com.primary_education_system.api;

import com.primary_education_system.config.security.CustomUserDetails;
import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.dto.account.AccountRequestDto;
import com.primary_education_system.dto.account.AccountResponseDto;
import com.primary_education_system.entity.user.UserEntity;
import com.primary_education_system.service.UserService;
import com.primary_education_system.util.PageableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/user")
public class User_API {

    @Autowired
    private UserService userService;

    @GetMapping("/getListAccount")
    public Page<UserEntity> getListCompanyAdmin(@RequestParam String search,
                                                @RequestParam int size, @RequestParam int page,
                                                @RequestParam String sortDir, @RequestParam String sortField) {
        Pageable pageable = PageableUtils.from(page, size, sortDir, sortField);
        return userService.getListAccount(pageable, search);
    }

    @PostMapping("/save")
    public ResponseEntity<ServerResponseDto> save(@RequestBody AccountRequestDto saveDto) {
        return ResponseEntity.ok().body(userService.save(saveDto));
    }

    @GetMapping("/getDetail")
    public AccountResponseDto getDetail(@RequestParam("userId") Long userId) {
        return userService.getDetail(userId);
    }

    @PostMapping("/delete")
    public ResponseEntity<ServerResponseDto> delete(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok().body(userService.delete(userId));
    }

    @GetMapping("/profile")
    public AccountResponseDto getProfile(@AuthenticationPrincipal CustomUserDetails userDetail) {
        return userService.getDetail(userDetail.getUserId());
    }

    @PostMapping("/updateProfile")
    public ResponseEntity<ServerResponseDto> updateProfile(@RequestBody AccountRequestDto saveDto,
                                                           @AuthenticationPrincipal CustomUserDetails userDetail) throws ParseException {
        return ResponseEntity.ok().body(userService.updateProfile(userDetail.getUserId(), saveDto));
    }

}
