package com.primary_education_system.service.material;

import com.google.common.collect.Lists;
import com.primary_education_system.dto.ResponseCase;
import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.dto.material.SaveMaterialDto;
import com.primary_education_system.entity.ClassEntity;
import com.primary_education_system.entity.SubjectEntity;
import com.primary_education_system.entity.material.ClassMaterialEntity;
import com.primary_education_system.entity.material.MaterialEntity;
import com.primary_education_system.repository.material.MaterialRepository;
import com.primary_education_system.service.ClassService;
import com.primary_education_system.service.FileService;
import com.primary_education_system.service.SubjectService;
import com.primary_education_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
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

    @Autowired
    private ClassService classService;

    @Autowired
    private ClassMaterialService classMaterialService;

    public Page<MaterialEntity> getPageMaterial(Pageable pageable, String keyword, Long subjectId, String grade, Long classId, String type) {
        List<Long> listSubjectIdFilter = new ArrayList<>();
        List<Long> listClassIdFilter = new ArrayList<>();

        if (subjectId == 0) {
            listSubjectIdFilter = subjectService
                    .getList()
                    .stream()
                    .map(SubjectEntity::getId)
                    .collect(Collectors.toList());
            listSubjectIdFilter.add(0L);
        } else {
            listSubjectIdFilter.add(subjectId);
        }

        if (classId == 0) {
            listClassIdFilter = classService.getALlClassId();
            listClassIdFilter.add(0L);
        } else {
            listClassIdFilter.add(classId);
        }


        Page<MaterialEntity> result = materialRepository.getPage(keyword, listSubjectIdFilter, listClassIdFilter, grade, type, pageable);
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
        List<Long> listMaterialId = result.getContent()
                .stream()
                .map(MaterialEntity::getId)
                .collect(Collectors.toList());

        Map<Long, String> mapNameSubjectById = subjectService.getMapNameSubjectById(listSubjectId);
        Map<Long, String> mapUserNameById = userService.getMapNameById(listUserCreateId);
        Map<Long, List<ClassEntity>> mapListClassByMaterialId = classMaterialService.getMapListClassByMaterialId(listMaterialId);
        result.getContent().forEach(material -> {
            material.setSubjectName(mapNameSubjectById.get(material.getSubjectId()));
            material.setCreator(mapUserNameById.get(material.getCreatedByUserId()));
            material.setListClassSelected(mapListClassByMaterialId.get(material.getId()));
        });
        return result;
    }

    @Transactional
    public ServerResponseDto save(SaveMaterialDto saveDto, Long useId) throws IOException {
        String grade = saveDto.getGrade();
        List<Long> listClassIdAfter;
        List<Long> listClassIdBefore;

        String stringListClassId = saveDto.getStringListClassId();
        if ("".equals(stringListClassId)) {
            listClassIdAfter = classService.getListClassIdByGrade(grade);
        } else {
            List<String> listClassIdString = Arrays.asList(stringListClassId.split(","));
            listClassIdAfter = listClassIdString
                    .stream()
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
        }

        Long materialId = saveDto.getId();

        MaterialEntity materialEntity;
        if (materialId != null) {
            materialEntity = materialRepository.findByIdAndIsDeletedFalse(materialId);
            listClassIdBefore = classMaterialService.getListClassIdByMaterialId(materialId);
        } else {
            materialEntity = new MaterialEntity();
            materialEntity.setCreatedTime(new Date());
            materialEntity.setCode(generateCode());
            materialEntity.setCreatedByUserId(useId);
            listClassIdBefore = Collections.EMPTY_LIST;
        }
        materialEntity.setUpdatedTime(new Date());
        materialEntity.setSubjectId(saveDto.getSubjectId());
        materialEntity.setGrade(grade);
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
        materialEntity = materialRepository.save(materialEntity);
        Long materialIdSaved = materialEntity.getId();

        saveClassMaterial(materialIdSaved, listClassIdBefore, listClassIdAfter);

        return new ServerResponseDto(ResponseCase.SUCCESS);
    }

    private void saveClassMaterial(Long materialId, List<Long> listClassIdBefore, List<Long> listClassIdAfter) {
        /* add new */
        List<Long> listClassIdNew = Lists.newArrayListWithCapacity(listClassIdAfter.size());
        listClassIdAfter.forEach(classIdAfter -> {
            if (!listClassIdBefore.contains(classIdAfter)) {
                listClassIdNew.add(classIdAfter);
            }
        });
        List<ClassMaterialEntity> listClassMaterialNew = Lists.newArrayListWithCapacity(listClassIdNew.size());
        listClassIdNew.forEach(classIdNew -> {
            ClassMaterialEntity classMaterialEntity = new ClassMaterialEntity(classIdNew, materialId, new Date());
            listClassMaterialNew.add(classMaterialEntity);
        });
        classMaterialService.saveAll(listClassMaterialNew);
        /**/

        /* remove */
        List<Long> listClassIdRemoved = Lists.newArrayListWithCapacity(listClassIdBefore.size());
        listClassIdBefore.forEach(classIdBefore -> {
            if (!listClassIdAfter.contains(classIdBefore)) {
                listClassIdRemoved.add(classIdBefore);
            }
        });
        if (!listClassIdRemoved.isEmpty()) {
            classMaterialService.deleteByListClassId(listClassIdRemoved);
        }
        /**/

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
        List<ClassEntity> listClassCanSelect = classService.getListByGrade(materialEntity.getGrade());
        materialEntity.setListClassCanSelect(listClassCanSelect);

        List<Long> listMaterialId = Arrays.asList(materialEntity.getId());
        Map<Long, List<ClassEntity>> mapListClassByMaterialId = classMaterialService.getMapListClassByMaterialId(listMaterialId);
        materialEntity.setListClassSelected(mapListClassByMaterialId.get(materialEntity.getId()));

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
