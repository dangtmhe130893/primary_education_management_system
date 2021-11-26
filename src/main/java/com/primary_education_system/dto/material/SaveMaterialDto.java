package com.primary_education_system.dto.material;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class SaveMaterialDto {
    private Long id;
    private Long subjectId;
    private String grade;
    private String name;
    private String type;
    private String content;
    private MultipartFile file;
    private String stringListClassId;
}
