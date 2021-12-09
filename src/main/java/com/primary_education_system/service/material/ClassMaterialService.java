package com.primary_education_system.service.material;

import com.google.common.collect.Lists;
import com.primary_education_system.entity.ClassEntity;
import com.primary_education_system.entity.material.ClassMaterialEntity;
import com.primary_education_system.repository.material.ClassMaterialRepository;
import com.primary_education_system.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClassMaterialService {
    @Autowired
    private ClassMaterialRepository classMaterialRepository;

    @Autowired
    private ClassService classService;

    public void saveAll(List<ClassMaterialEntity> listClassMaterial) {
        classMaterialRepository.save(listClassMaterial);
    }

    public Map<Long, List<ClassEntity>> getMapListClassByMaterialId(List<Long> listMaterialId) {
        List<ClassMaterialEntity> listClassMaterial = classMaterialRepository.findByMaterialIdIn(listMaterialId);
        Map<Long, List<Long>> mapListClassIdByMaterialId = listClassMaterial
                .stream()
                .collect(Collectors.groupingBy(ClassMaterialEntity::getMaterialId,
                        Collectors.mapping(ClassMaterialEntity::getClassId, Collectors.toList())));
        List<Long> listClassId = listClassMaterial
                .stream()
                .map(ClassMaterialEntity::getClassId)
                .collect(Collectors.toList());
        Map<Long, ClassEntity> mapClassEntityByClassId = classService.getMapClassById(listClassId);

        Map<Long, List<ClassEntity>> result = new HashMap<>();
        listClassMaterial.forEach(classMaterial -> {
            List<Long> listClassIdByMaterialId = mapListClassIdByMaterialId.get(classMaterial.getMaterialId());

            List<ClassEntity> listClass = Lists.newArrayListWithCapacity(listClassIdByMaterialId.size());
            listClassIdByMaterialId.forEach(classId -> {
                listClass.add(mapClassEntityByClassId.get(classId));
            });

            result.put(classMaterial.getMaterialId(), listClass);
        });

        return result;
    }

    public List<Long> getListClassIdByMaterialId(Long materialId) {
        List<ClassMaterialEntity> listClassMaterial = classMaterialRepository.getByMaterialId(materialId);
        return listClassMaterial
                .stream()
                .map(ClassMaterialEntity::getClassId)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteByListClassId(List<Long> listClassIdRemoved) {
        classMaterialRepository.deleteByListClassId(listClassIdRemoved);
    }

    @Transactional
    public void deleteMaterialRelatedClass(Long classId) {
        classMaterialRepository.deleteByClassId(classId);
    }
}
