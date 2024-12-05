package com.example.demo.service;

import com.example.demo.model.File;
import com.example.demo.model.User;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;


public interface FileService {

    public String storeFile(MultipartFile file, User owner) throws IOException;

    public Resource loadFile(String name);

    public File findFile(String filename);

    public List<File> getAllFiles(int userId);

    Object deleteFile(int id);
}
