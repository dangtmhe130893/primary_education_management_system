package com.primary_education_system.service;

import com.primary_education_system.dto.ResponseCase;
import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.dto.pupil_account.PupilAccountDto;
import com.primary_education_system.entity.ClassEntity;
import com.primary_education_system.entity.pupil.PupilAccountEntity;
import com.primary_education_system.repository.PupilAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PupilAccountService {

    @Autowired
    private PupilAccountRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private ClassService classService;

    public Page<PupilAccountEntity> getPagePupilAccount(Pageable pageable, String keyword, String grade, Long classId) {
        List<String> listGradeFilter = new ArrayList<>();
        List<Long> listClassIdFilter = new ArrayList<>();
        if ("0".equals(grade)) {
            listGradeFilter = gradeService.getAllNameGrade();
        } else {
            listGradeFilter.add("Khối " + grade);
        }

        if (classId == 0) {
            listClassIdFilter = classService.getALlClassId();
        } else {
            listClassIdFilter.add(classId);
        }


        Page<PupilAccountEntity> pageResult = repository.getPagePupilAccount(keyword, listGradeFilter, listClassIdFilter, pageable);
        List<Long> listClassId = pageResult.getContent()
                .stream()
                .map(PupilAccountEntity::getClassId)
                .collect(Collectors.toList());

        Map<Long, String> mapClassNameByClassId = classService.getMapClassNameByClassId(listClassId);
        pageResult.forEach(pupilAccount -> pupilAccount
                .setClassName(mapClassNameByClassId.getOrDefault(pupilAccount.getClassId(), "")));
        return pageResult;
    }

    public ServerResponseDto save(PupilAccountDto pupilAccountDto) {
        Long id = pupilAccountDto.getId();
        boolean isUpdate = id != null;

        PupilAccountEntity pupilAccountEntity;
        if (isUpdate) {
            pupilAccountEntity = repository.findByIdAndIsDeletedFalse(id);
        } else {
            pupilAccountEntity = new PupilAccountEntity();
            pupilAccountEntity.setCode(generateCodePupilAccount());
            pupilAccountEntity.setCreatedTime(new Date());
        }
        convertDtoToEntity(pupilAccountEntity, pupilAccountDto);
        repository.save(pupilAccountEntity);
        return new ServerResponseDto(ResponseCase.SUCCESS);
    }

    private void convertDtoToEntity(PupilAccountEntity entity, PupilAccountDto dto) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setUsername(dto.getUsername());
        entity.setPhone(dto.getPhone());
        entity.setGender(dto.getSex());
        entity.setAddress(dto.getAddress());
        entity.setFatherName(dto.getFatherName());
        entity.setMotherName(dto.getMotherName());
        entity.setGrade("Khối " + dto.getGrade());
        entity.setClassId(dto.getClassId());
        entity.setChangePassword(false);
        entity.setStatusUser(2);
        entity.setUpdatedTime(new Date());
        if (dto.getPassword() != null) {
            entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
            entity.setRawPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        }
    }

    private String generateCodePupilAccount() {
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

        int random = new Random().nextInt(9999);
        StringBuilder randomStr = new StringBuilder(String.valueOf(random));
        while (randomStr.length() < 4) {
            randomStr.insert(0, "0");
        }
        return randomStr.insert(0, year).toString();
    }

    public ServerResponseDto detail(Long id) {
        PupilAccountEntity pupilAccountEntity = repository.findByIdAndIsDeletedFalse(id);
        List<ClassEntity> listClass = classService.getListByGrade(pupilAccountEntity.getGrade());
        pupilAccountEntity.setListClass(listClass);
        return new ServerResponseDto(ResponseCase.SUCCESS, pupilAccountEntity);
    }

    public ServerResponseDto delete(Long id) {
        PupilAccountEntity pupilAccountEntity = repository.findByIdAndIsDeletedFalse(id);
        if (pupilAccountEntity == null) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }
        pupilAccountEntity.setDeleted(true);
        repository.save(pupilAccountEntity);
        return new ServerResponseDto(ResponseCase.SUCCESS);
    }
}
