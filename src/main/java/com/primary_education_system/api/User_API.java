package com.primary_education_system.api;

import com.primary_education_system.entity.user.UserEntity;
import com.primary_education_system.service.UserService;
import com.primary_education_system.util.PageableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


}
