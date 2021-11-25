package com.primary_education_system.service.material;

import com.primary_education_system.dto.ResponseCase;
import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.dto.material.SaveMaterialDto;
import com.primary_education_system.entity.SubjectEntity;
import com.primary_education_system.entity.material.MaterialEntity;
import com.primary_education_system.repository.material.MaterialRepository;
import com.primary_education_system.service.FileService;
import com.primary_education_system.service.SubjectService;
import com.primary_education_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private UserService userService;

    public Page<MaterialEntity> getPageMaterial(Pageable pageable, String keyword, Long subjectId, String grade, String type) {
        List<Long> listSubjectIdForSearch = new ArrayList<>();
        if (subjectId == 0) {
            listSubjectIdForSearch = subjectService
                    .getList()
                    .stream()
                    .map(SubjectEntity::getId)
                    .collect(Collectors.toList());
        } else {
            listSubjectIdForSearch.add(subjectId);
        }


        Page<MaterialEntity> result = materialRepository.getPage(keyword, listSubjectIdForSearch, grade, type, pageable);
        if (result.getContent().isEmpty()) {
            return result;
        }
        List<Long> listSubjectId = result.getContent()
                .stream()
                .map(MaterialEntity::getSubjectId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<Long> listUserCreateId = result.getContent()
                .stream()
                .map(MaterialEntity::getCreatedByUserId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        Map<Long, String> mapNameSubjectById = subjectService.getMapNameSubjectById(listSubjectId);
        Map<Long, String> mapUserNameById = userService.getMapNameById(listUserCreateId);
        result.getContent().forEach(material -> {
            material.setSubjectName(mapNameSubjectById.get(material.getSubjectId()));
            material.setCreator(mapUserNameById.get(material.getCreatedByUserId()));
        });
        return result;
    }

    public ServerResponseDto save(SaveMaterialDto saveDto, Long useId) throws IOException {
        Long materialId = saveDto.getId();

        MaterialEntity materialEntity;
        if (materialId != null) {
            materialEntity = materialRepository.findByIdAndIsDeletedFalse(materialId);
        } else {
            materialEntity = new MaterialEntity();
            materialEntity.setCreatedTime(new Date());
            materialEntity.setCode(generateCode());
            materialEntity.setCreatedByUserId(useId);
        }
        materialEntity.setUpdatedTime(new Date());
        materialEntity.setSubjectId(saveDto.getSubjectId());
        materialEntity.setGrade(saveDto.getGrade());
        materialEntity.setName(saveDto.getName());
        materialEntity.setType(saveDto.getType());
        materialEntity.setContent(saveDto.getContent());
        materialEntity.setUpdatedByUserId(useId);
        MultipartFile file = saveDto.getFile();
        if (file != null) {
            String pathFile = fileService.uploadFile("material/", file);
            materialEntity.setFileName(file.getOriginalFilename());
            materialEntity.setLinkFile(pathFile);
        }
        materialRepository.save(materialEntity);
        return new ServerResponseDto(ResponseCase.SUCCESS);
    }

    private String generateCode() {
        Random rnd = new Random();
        int numberRnd;
        do {
            numberRnd = rnd.nextInt(999999);
        } while (isCodeExist("MATERIAL_" + String.format("%06d", numberRnd)));
        return "MATERIAL_" + String.format("%06d", numberRnd);
    }

    private boolean isCodeExist(String code) {
        return materialRepository.countByCode(code) != 0;
    }

    public ResponseEntity<Object> downloadFile(String code) throws IOException {
        MaterialEntity materialEntity = materialRepository.findByCodeAndIsDeletedFalse(code);
        if (materialEntity == null) {
            return null;
        }
        return fileService.downloadFile(materialEntity.getLinkFile());
    }

    public ServerResponseDto detail(Long id) {
        MaterialEntity materialEntity = materialRepository.findByIdAndIsDeletedFalse(id);
        if (materialEntity == null) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }
        return new ServerResponseDto(ResponseCase.SUCCESS, materialEntity);
    }

    public ServerResponseDto delete(Long id) {
        MaterialEntity materialEntity = materialRepository.findByIdAndIsDeletedFalse(id);
        if (materialEntity == null) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }
        materialEntity.setDeleted(true);
        materialRepository.save(materialEntity);
        return new ServerResponseDto(ResponseCase.SUCCESS);
    }

}
