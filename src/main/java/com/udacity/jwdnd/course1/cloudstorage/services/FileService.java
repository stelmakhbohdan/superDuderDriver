package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;
    private UserMapper userMapper;

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    public List<File> getAllUserFiles(int userId) {
        return fileMapper.getFiles(userId);
    }

    public void createFile(MultipartFile fileUpload, String username) throws IOException {
        int userId = userMapper.getUserIdByUsername(username);
        File newFile = new File(null,
                fileUpload.getOriginalFilename(),
                fileUpload.getContentType(),
                String.valueOf(fileUpload.getSize()),
                userId,
                fileUpload.getBytes()
        );
        fileMapper.addFile(newFile);
    }

    public File getFileByFileid(int fileId) {
        return fileMapper.getFile(fileId);
    }

    public void deleteFileByFilename(int fileId) {
        fileMapper.deleteFiles(fileId);
    }
}
