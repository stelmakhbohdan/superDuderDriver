package com.udacity.jwdnd.course1.cloudstorage.services;

import mapper.FileMapper;
import mapper.UserMapper;
import model.File;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public class FileService {
    private FileMapper fileMapper;
    private UserMapper userMapper;
    private AuthenticatedUserService authenticatedUserService;

    public FileService(FileMapper fileMapper, UserMapper userMapper, AuthenticatedUserService authenticatedUserService) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
        this.authenticatedUserService = authenticatedUserService;
    }

    public boolean isFileNameAvailable(String fileName){
        return fileMapper.getFile(authenticatedUserService.getLoggedInUserId(),fileName)==null;
    }

    public File loadFile(String filename){
        File file = null;
        try {
            file = fileMapper.getFile(authenticatedUserService.getLoggedInUserId(),filename);
        }catch (NullPointerException e){
            e.printStackTrace();
            throw e;
        }
        return file;
    }

    public List<File> loadFiles(){
        List<File> files  = null;
        try {
            files = fileMapper.getFiles(authenticatedUserService.getLoggedInUserId());
        }catch (NullPointerException e){
            e.printStackTrace();
            throw e;
        }
        return  files;
    }

    public void deleteFile(String filename){
        fileMapper.deleteFiles(filename);
    }

    public int uploadFile(MultipartFile file){
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            return  fileMapper.addFile(new File(0,filename,file.getContentType()
            ,Long.toString(file.getSize()),authenticatedUserService.getLoggedInUserId(),file.getBytes()));
        }catch (IOException e){
            e.printStackTrace();
        }
        return 0;
    }
}
