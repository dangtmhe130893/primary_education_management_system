package com.primary_education_system.service;

import com.google.common.collect.Lists;
import com.primary_education_system.dto.ResponseCase;
import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.dto.pupil_account.ClassIdAndNumberPupil;
import com.primary_education_system.dto.pupil_account.PupilAccountDto;
import com.primary_education_system.entity.ClassEntity;
import com.primary_education_system.entity.pupil.PupilAccountEntity;
import com.primary_education_system.repository.PupilAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PupilAccountService {

    @Autowired
    private PupilAccountRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ClassService classService;


    public Page<PupilAccountEntity> getPagePupilAccount(Pageable pageable, String keyword, String grade, Long classId) {
        List<String> listGradeFilter = Lists.newArrayListWithExpectedSize(5);
        List<Long> listClassIdFilter = new ArrayList<>();
        if ("All".equals(grade)) {
            listGradeFilter.add("Khối 1");
            listGradeFilter.add("Khối 2");
            listGradeFilter.add("Khối 3");
            listGradeFilter.add("Khối 4");
            listGradeFilter.add("Khối 5");
        } else {
            listGradeFilter.add(grade);
        }

        if (classId == 0) {
            listClassIdFilter = classService.getALlClassId();
            listClassIdFilter.add(0L);
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

    public ServerResponseDto save(PupilAccountDto pupilAccountDto) throws ParseException {
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

    private void convertDtoToEntity(PupilAccountEntity entity, PupilAccountDto dto) throws ParseException {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setUsername(dto.getUsername());
        entity.setPhone(dto.getPhone());
        entity.setGender(dto.getSex());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        entity.setBirthday(sdf.parse(dto.getBirthday()));
        entity.setAddress(dto.getAddress());
        entity.setFatherName(dto.getFatherName());
        entity.setMotherName(dto.getMotherName());
        entity.setGrade(dto.getGrade());
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

    public Map<Long, Integer> getMapNumberPupilByClassId(List<Long> listClassId) {
        if (listClassId.isEmpty()) {
            return Collections.EMPTY_MAP;
        }

        List<ClassIdAndNumberPupil> listClassIdAndNumberPupil = repository.getClassIdAndNumberPupil(listClassId);
        if (listClassIdAndNumberPupil.isEmpty()) {
            return Collections.EMPTY_MAP;
        }

        Map<Long, Integer> mapNumberPupilByClassId = new HashMap<>();
        listClassIdAndNumberPupil.forEach(object -> {
            mapNumberPupilByClassId.put(object.getClassId(), object.getNumberPupil());
        });
        return mapNumberPupilByClassId;
    }

    public int countNumberPupilInClass(Long classId) {
        System.out.println("sĩ số: " + repository.countNumberPupilInClass(classId));
        return (int) repository.countNumberPupilInClass(classId);
    }
}
