package com.primary_education_system.api;

import com.primary_education_system.config.security.CustomUserDetails;
import com.primary_education_system.dto.ResponseCase;
import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.dto.account.AccountRequestDto;
import com.primary_education_system.dto.account.ChangePasswordRequestDto;
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
import java.util.List;

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
    public ResponseEntity<ServerResponseDto> save(@RequestBody AccountRequestDto saveDto) throws ParseException {
        return ResponseEntity.ok().body(userService.save(saveDto));
    }

    @GetMapping("/getDetail")
    public UserEntity getDetail(@RequestParam("userId") Long userId) {
        return userService.getDetail(userId);
    }

    @PostMapping("/delete")
    public ResponseEntity<ServerResponseDto> delete(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok().body(userService.delete(userId));
    }

    @GetMapping("/profile")
    public UserEntity getProfile(@AuthenticationPrincipal CustomUserDetails userDetail) {
        return userService.getDetail(userDetail.getUserId());
    }

    @PostMapping("/updateProfile")
    public ResponseEntity<ServerResponseDto> updateProfile(@RequestBody AccountRequestDto saveDto,
                                                           @AuthenticationPrincipal CustomUserDetails userDetail) throws ParseException {
        return ResponseEntity.ok().body(userService.updateProfile(userDetail.getUserId(), saveDto));
    }

    @PostMapping(value = "/setPassword")
    public ResponseEntity<ServerResponseDto> setPassword(@RequestParam("token") String token,
                                                         @RequestParam("password") String password) {
        return ResponseEntity.ok().body(userService.setPassword(token, password));
    }

    @GetMapping("/getListTeacher")
    public ResponseEntity<ServerResponseDto> getListTeacher() {
        return ResponseEntity.ok(new ServerResponseDto(ResponseCase.SUCCESS, userService.getListTeacher()));
    }

    @GetMapping("/getListTeacherForSubject/{subjectId}")
    public List<UserEntity> getListTeacherForSubject(@PathVariable Long subjectId) {
        return userService.getListTeacherForSubject(subjectId);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<ServerResponseDto> changePassword(@RequestBody ChangePasswordRequestDto changePasswordRequestDto,
                                                            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseEntity.ok(userService.changePassword(changePasswordRequestDto, customUserDetails));
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<ServerResponseDto> forgotPassword(@RequestParam String email) {
        return ResponseEntity.ok(userService.forgotPassword(email));
    }

    @GetMapping("/getListHomeroomTeacher")
    public ResponseEntity<ServerResponseDto> getListHomeroomTeacher() {
        return ResponseEntity.ok(new ServerResponseDto(ResponseCase.SUCCESS, userService.getListHomeroomTeacher()));
    }
}
