package com.primary_education_system.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class FileService {

    @Value("${application.folderStorageFile}")
    private String folderStorageFile;

    public String uploadFile(String sub, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        log.info("Start upload file: {}", fileName);
        String PATH = folderStorageFile + sub;

        File directory = new File(PATH);
        if (!directory.exists()) {
            directory.mkdir();
        }

        File convertFile = new File(folderStorageFile + sub + UUID.randomUUID() + fileName.replaceAll("\\s+", ""));
        convertFile.createNewFile();
        FileOutputStream out = new FileOutputStream(convertFile);
        out.write(file.getBytes());
        out.close();
        log.info("Upload success!!!");
        return convertFile.getAbsolutePath();
    }

}
