package com.example.demo.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class FileStoreConfig {


    private String uploadDir="./uploads/";

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
