package com.primary_education_system.service;

import com.google.common.collect.Lists;
import com.primary_education_system.dto.ResponseCase;
import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.dto.pupil_account.ClassIdAndNumberPupil;
import com.primary_education_system.dto.pupil_account.PupilAccountDto;
import com.primary_education_system.dto.pupil_account.PupilAccountImportDto;
import com.primary_education_system.entity.ClassEntity;
import com.primary_education_system.entity.pupil.PupilAccountEntity;
import com.primary_education_system.repository.PupilAccountRepository;
import com.primary_education_system.util.Constant;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        boolean isEmailExist;
        if (id == null) {
            isEmailExist = repository.countByEmail(pupilAccountDto.getEmail()) != 0;
        } else {
            isEmailExist = repository.countByEmailAndId(pupilAccountDto.getEmail(), id) != 0;
        }
        if (isEmailExist) {
            return new ServerResponseDto(ResponseCase.EMAIL_EXISTED);
        }

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
            return Collections.emptyMap();
        }

        List<ClassIdAndNumberPupil> listClassIdAndNumberPupil = repository.getClassIdAndNumberPupil(listClassId);
        if (listClassIdAndNumberPupil.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<Long, Integer> mapNumberPupilByClassId = new HashMap<>();
        listClassIdAndNumberPupil.forEach(object -> {
            mapNumberPupilByClassId.put(object.getClassId(), object.getNumberPupil());
        });
        return mapNumberPupilByClassId;
    }

    public int countNumberPupilInClass(Long classId) {
        return (int) repository.countNumberPupilInClass(classId);
    }

    public ServerResponseDto importExcel(MultipartFile file) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter df = new DataFormatter();
        int[] rowAndColError = new int[2];

        List<PupilAccountImportDto> listPupilAccount = new ArrayList<>();
        int indexRow = 0;

        for (Row row : sheet) {
            if (indexRow == 0) {
                indexRow++;
                continue;
            }
            PupilAccountImportDto pupilAccountImportDto = new PupilAccountImportDto();

            // name
            String name = df.formatCellValue(row.getCell(0)).trim();
            if (isDataImportEmpty(name)) {
                break;
            }
            pupilAccountImportDto.setName(name);


            // grade
            String grade = df.formatCellValue(row.getCell(1)).trim();
            if (isDataImportEmpty(grade)) {
                return returnErrorWhenDataImportEmpty(indexRow + 1, 2);
            }
            Set<String> setGrade = Stream.of("Khối 1", "Khối 2", "Khối 3", "Khối 4", "Khối 5")
                    .collect(Collectors.toCollection(HashSet::new));
            if (!setGrade.contains(grade)) {
                return returnErrorWhenDataImportErrorFormat(indexRow + 1, 2);
            }
            pupilAccountImportDto.setGrade(grade);


            // class
            Set<String> setClassName = classService.getSetClassNameByGrade(grade);
            String className = df.formatCellValue(row.getCell(2)).trim();
            if (isDataImportEmpty(className)) {
                return returnErrorWhenDataImportEmpty(indexRow + 1, 3);
            }
            if (!setClassName.contains(className)) {
                rowAndColError[0] = indexRow + 1;
                rowAndColError[1] = 3;
                return new ServerResponseDto(ResponseCase.NAME_CLASS_NOT_FOUND, rowAndColError);
            }
            pupilAccountImportDto.setClassName(className);


            // email
            String email = df.formatCellValue(row.getCell(3)).trim();
            if (isDataImportEmpty(email)) {
                return returnErrorWhenDataImportEmpty(indexRow + 1, 4);
            }
            if (isEmailExist(email)) {
                rowAndColError[0] = indexRow + 1;
                rowAndColError[1] = 4;
                return new ServerResponseDto(ResponseCase.EMAIL_EXIST, rowAndColError);
            }
            pupilAccountImportDto.setEmail(email);


            // password
            String password = df.formatCellValue(row.getCell(4)).trim();
            if (isDataImportEmpty(password)) {
                return returnErrorWhenDataImportEmpty(indexRow + 1, 5);
            }
            pupilAccountImportDto.setPassword(password);


            // phone
            String phone = df.formatCellValue(row.getCell(5)).trim();
            if (isDataImportEmpty(phone)) {
                return returnErrorWhenDataImportEmpty(indexRow + 1, 6);
            }
            pupilAccountImportDto.setPhone(phone);


            // birthday
            try {
                String birthday = df.formatCellValue(row.getCell(6)).trim();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                pupilAccountImportDto.setBirthday(sdf.parse(birthday));
            } catch (ParseException e) {
                return returnErrorWhenDataImportErrorFormat(indexRow + 1, 7);
            }

            // gender
            String gender = df.formatCellValue(row.getCell(7)).trim();
            if (isDataImportEmpty(gender)) {
                return returnErrorWhenDataImportEmpty(indexRow + 1, 8);
            }
            Set<String> setGender = Stream.of("Nam", "Nữ", "Khác")
                    .collect(Collectors.toCollection(HashSet::new));
            if (!setGender.contains(gender)) {
                return returnErrorWhenDataImportErrorFormat(indexRow + 1, 8);
            }
            pupilAccountImportDto.setGender(gender);

            // address
            String address = df.formatCellValue(row.getCell(8)).trim();
            if (isDataImportEmpty(address)) {
                return returnErrorWhenDataImportEmpty(indexRow + 1, 9);
            }
            pupilAccountImportDto.setAddress(address);


            // fatherName
            String fatherName = df.formatCellValue(row.getCell(9)).trim();
            pupilAccountImportDto.setFatherName(fatherName);


            // motherName
            String motherName = df.formatCellValue(row.getCell(10)).trim();
            pupilAccountImportDto.setMotherName(motherName);

            listPupilAccount.add(pupilAccountImportDto);
        }

        List<String> listEmailImport = listPupilAccount
                .stream()
                .map(PupilAccountImportDto::getEmail)
                .collect(Collectors.toList());
        if (isDuplicateEmailImport(listEmailImport)) {
            return new ServerResponseDto(ResponseCase.SAME_EMAIL);
        }

        return new ServerResponseDto(ResponseCase.SUCCESS, listPupilAccount);
    }

    private boolean isDuplicateEmailImport(List<String> listEmailImport) {
        Set<String> setEmailImport = new HashSet<>(listEmailImport);
        return listEmailImport.size() != setEmailImport.size();
    }

    private boolean isDataImportEmpty(String data) {
        return "".equals(data);
    }

    private boolean isEmailExist(String email) {
        Set<String> setEmailExist = repository.getSetEmailExist();
        return setEmailExist.contains(email);
    }

    private ServerResponseDto returnErrorWhenDataImportEmpty(int row, int col) {
        int[] rowAndColError = new int[2];
        rowAndColError[0] = row;
        rowAndColError[1] = col;
        return new ServerResponseDto(ResponseCase.DATA_IMPORT_EMPTY, rowAndColError);
    }

    private ServerResponseDto returnErrorWhenDataImportErrorFormat(int row, int col) {
        int[] rowAndColError = new int[2];
        rowAndColError[0] = row;
        rowAndColError[1] = col;
        return new ServerResponseDto(ResponseCase.DATA_IMPORT_ERROR_FORMAT, rowAndColError);
    }

    public ServerResponseDto saveList(List<PupilAccountImportDto> listPupilAccountImport) {
        if (listPupilAccountImport.isEmpty()) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }

        List<String> listNameClass = listPupilAccountImport
                .stream()
                .map(PupilAccountImportDto::getClassName)
                .collect(Collectors.toList());
        Map<String, Long> mapClassIdByClassName = classService.getMapClassIdByClassName(listNameClass);

        List<PupilAccountEntity> listPupilAccount = new ArrayList<>();
        listPupilAccountImport.forEach(dto -> {
            PupilAccountEntity pupilEntity = new PupilAccountEntity();
            pupilEntity.setCode(generateCodePupilAccount());
            pupilEntity.setName(dto.getName());
            pupilEntity.setEmail(dto.getEmail());
            pupilEntity.setGrade(dto.getGrade());
            pupilEntity.setClassId(mapClassIdByClassName.get(dto.getClassName()));
            pupilEntity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
            pupilEntity.setRawPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
            pupilEntity.setPhone(dto.getPhone());
            pupilEntity.setBirthday(dto.getBirthday());
            pupilEntity.setGender(Constant.mapGender.get(dto.getGender()));
            pupilEntity.setAddress(dto.getAddress());
            pupilEntity.setFatherName(dto.getFatherName());
            pupilEntity.setMotherName(dto.getMotherName());
            pupilEntity.setChangePassword(false);
            pupilEntity.setStatusUser(2);

            listPupilAccount.add(pupilEntity);
        });
        repository.save(listPupilAccount);
        return new ServerResponseDto(ResponseCase.SUCCESS);
    }

    public PupilAccountEntity findByEmail(String email) {
        return repository.findByEmailAndIsDeletedFalse(email);
    }

    public int countTotalPupil() {
        return (int) repository.countTotalPupil();
    }

    public Map<String, Integer> getMapNumberPupilByGrade() {
        List<Object[]> listObjectGradeAndNumberPupil = repository.getObjectGradeAndNumberPupil();
        Map<String, Integer> result = new HashMap<>();
        listObjectGradeAndNumberPupil.forEach(object -> {
            result.put((String) object[0], ((Long) object[1]).intValue());
        });
        return result;
    }

    public Map<String, List<Long>> getMapListPupilIdByGrade(Set<Long> listTuitionId) {
        List<PupilAccountEntity> listPupil = repository.findByIdInAndIsDeletedFalse(listTuitionId);
        return listPupil
                .stream()
                .collect(Collectors.groupingBy(PupilAccountEntity::getGrade,
                        Collectors.mapping(PupilAccountEntity::getId, Collectors.toList())));
    }

    public Map<Long, List<Long>> getMapListPupilIdByClassId(Set<Long> listTuitionId) {
        List<PupilAccountEntity> listPupil = repository.findByIdInAndIsDeletedFalse(listTuitionId);
        return listPupil
                .stream()
                .collect(Collectors.groupingBy(PupilAccountEntity::getClassId,
                        Collectors.mapping(PupilAccountEntity::getId, Collectors.toList())));
    }

    public List<PupilAccountEntity> getByClassId(Long classId) {
        return repository.findByClassIdAndIsDeletedFalse(classId);
    }

    public List<PupilAccountEntity> findByKeywordAndByIsDeletedFalse(String keyword) {
        return repository.findByKeywordAndIsDeletedFalse(keyword);
    }

    public PupilAccountEntity findById(Long pupilId) {
        return repository.findByIdAndIsDeletedFalse(pupilId);
    }
}
