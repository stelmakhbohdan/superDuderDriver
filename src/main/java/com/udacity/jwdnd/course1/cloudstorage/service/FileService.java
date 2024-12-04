package com.udacity.jwdnd.course1.cloudstorage.service;


import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.core.io.Resource;
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
