package com.primary_education_system.entity.material;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "class_material")
@Getter
@Setter
public class ClassMaterialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long classId;
    private Long materialId;
    private Date createdTime;

    public ClassMaterialEntity() {
    }

    public ClassMaterialEntity(Long classId, Long materialId, Date createdTime) {
        this.classId = classId;
        this.materialId = materialId;
        this.createdTime = createdTime;
    }
}
