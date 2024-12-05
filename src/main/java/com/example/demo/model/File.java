package com.example.demo.model;

import lombok.Data;

import java.sql.Blob;


public @Data class File {
    private Integer fileId;
    private String fileName;
    private Integer owner;
    private String contentType;
    private String fileSize ;

    private byte[] fileData ;

    public File(Integer fileId, String fileName,  String contentType, String fileSize,Integer owner, byte[] fileData) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.owner = owner;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.fileData = fileData;
    }
}
