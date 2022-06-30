package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private FilesMapper filesMapper;
    private UsersMapper usersMapper;
    private AuthenticatedUserService authenticatedUser;

    public FileService(FilesMapper filesMapper, UsersMapper usersMapper, AuthenticatedUserService authenticatedUser) {
        this.filesMapper = filesMapper;
        this.usersMapper = usersMapper;
        this.authenticatedUser = authenticatedUser;
    }

    public boolean isFileNameAvailable(String fileName){ return filesMapper.getFile(authenticatedUser.getLoggedInUserId(), fileName) == null; }

    public int uploadNewFile(MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            return filesMapper.addFile(new Files(fileName,
                    file.getContentType(),
                    Long.toString(file.getSize()),
                    authenticatedUser.getLoggedInUserId(),
                    file.getBytes()
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Files loadFile(String fileName){
        Files file = null;
        try{
            file = filesMapper.getFile(authenticatedUser.getLoggedInUserId(), fileName);
        }catch (NullPointerException e){
            e.printStackTrace();
            throw e;
        }
        return file;
    }

    public List<Files> loadFiles(){
        List<Files> files = null;
        try{
            files = filesMapper.getFiles(authenticatedUser.getLoggedInUserId());
        }catch (NullPointerException e){
            e.printStackTrace();
            throw e;
        }
        return files;
    }

    public void deleteFile(String fileName){
        filesMapper.deleteFile(fileName);
    }
}