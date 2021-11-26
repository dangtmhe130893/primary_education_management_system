package com.primary_education_system.repository.material;

import com.primary_education_system.entity.material.MaterialEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface MaterialRepository extends JpaRepository<MaterialEntity, Long> {

    @Query(value = "select * from material as m " +
            "inner join class_material as cm on m.id = cm.material_id " +
            "where (m.name like %?1% or m.code like %?1%) and cm.class_id in ?3 and m.subject_id in ?2 and m.grade like %?4% and m.type like %?5% " +
            "and m.is_deleted = false group by m.id \n#pageable\n",

            countQuery = "select COUNT(m.id) from material m " +
                    "inner join class_material as cm on m.id = cm.material_id " +
                    "where (m.name like %?1% or m.code like %?1%) and cm.class_id in ?3 and m.subject_id in ?2 and m.grade like %?4% and m.type like %?5% " +
                    "and m.is_deleted = false group by m.id \n#pageable\n",

            nativeQuery = true)
    Page<MaterialEntity> getPage(String keyword, Collection<Long> subjectIds, List<Long> listClassIdFilter, String grade, String type, Pageable pageable);

    MaterialEntity findByIdAndIsDeletedFalse(Long id);

    MaterialEntity findByCodeAndIsDeletedFalse(String code);

    int countByCode(String code);

}
