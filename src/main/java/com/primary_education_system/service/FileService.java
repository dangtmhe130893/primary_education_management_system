package com.primary_education_system.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class FileService {

    @Value("${folderStorageFile}")
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

    public ResponseEntity<Object> downloadFile(String pathFile) throws IOException {
        File file = new File(pathFile);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(
                MediaType.parseMediaType("application/jpg")).body(resource);
    }

}
