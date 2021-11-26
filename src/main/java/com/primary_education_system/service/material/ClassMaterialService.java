package com.primary_education_system.service.material;

import com.google.common.collect.Lists;
import com.primary_education_system.entity.ClassEntity;
import com.primary_education_system.entity.material.ClassMaterialEntity;
import com.primary_education_system.repository.material.ClassMaterialRepository;
import com.primary_education_system.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Map<Long, List<String>> getMapListNameClassByMaterialId(List<Long> listMaterialId) {
        List<ClassMaterialEntity> listClassMaterial = classMaterialRepository.findByMaterialIdIn(listMaterialId);
        Map<Long, List<Long>> mapListClassIdByMaterialId = listClassMaterial
                .stream()
                .collect(Collectors.groupingBy(ClassMaterialEntity::getMaterialId,
                        Collectors.mapping(ClassMaterialEntity::getClassId, Collectors.toList())));
        List<Long> listClassId = listClassMaterial
                .stream()
                .map(ClassMaterialEntity::getClassId)
                .collect(Collectors.toList());
        Map<Long, String> mapNameClassByClassId = classService.getMapClassNameByClassId(listClassId);

        Map<Long, List<String>> result = new HashMap<>();
        listClassMaterial.forEach(classMaterial -> {
            List<Long> listClassIdByMaterialId = mapListClassIdByMaterialId.get(classMaterial.getMaterialId());

            List<String> listNameClass = Lists.newArrayListWithCapacity(listClassIdByMaterialId.size());
            listClassIdByMaterialId.forEach(classId -> {
                String nameClass = mapNameClassByClassId.get(classId);
                listNameClass.add(nameClass);
            });

            result.put(classMaterial.getMaterialId(), listNameClass);
        });

        return result;
    }
}
