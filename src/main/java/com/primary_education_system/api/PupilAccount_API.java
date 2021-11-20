package com.primary_education_system.api;

import com.primary_education_system.entity.pupil.PupilAccountEntity;
import com.primary_education_system.service.PupilAccountService;
import com.primary_education_system.util.PageableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pupil_account")
public class PupilAccount_API {

    @Autowired
    private PupilAccountService pupilAccountService;

    @GetMapping("/getPage")
    public Page<PupilAccountEntity> getPagePupilAccount(@RequestParam String search,
                                                        @RequestParam int size, @RequestParam int page,
                                                        @RequestParam String sortDir, @RequestParam String sortField) {
        Pageable pageable = PageableUtils.from(page, size, sortDir, sortField);
        return pupilAccountService.getPagePupilAccount(pageable, search);
    }

}
