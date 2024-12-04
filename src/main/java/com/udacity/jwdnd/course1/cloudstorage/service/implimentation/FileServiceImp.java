package com.udacity.jwdnd.course1.cloudstorage.service.implimentation;


import com.udacity.jwdnd.course1.cloudstorage.config.FileStoreConfig;
import com.udacity.jwdnd.course1.cloudstorage.exception.FileStorageException;
import com.udacity.jwdnd.course1.cloudstorage.exception.MyFileNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.utils.AppConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class FileServiceImp implements FileService {
    private Logger logger =  LoggerFactory.getLogger(FileServiceImp.class);
    private final Path fileLocation;

    @Autowired
    private FileMapper fileMapper;

    public FileServiceImp(FileStoreConfig fileLocation) {
        this.fileLocation = Paths.get(fileLocation.getUploadDir()).toAbsolutePath().normalize();
        logger.info("Location is: ++++++++++++++++++++++++++++++++++++++++"+this.fileLocation);
        try{
            Files.createDirectories(this.fileLocation);
            logger.info("Location of the file is: ", this.fileLocation);
        } catch (IOException e) {
            throw new FileStorageException("Could not create a folder to store files", e);
        }
    }

    @Override
    public String storeFile(MultipartFile file, User owner) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        logger.info("The size of the file is: ++++++++++++++++++",file.getSize());
        logger.info("Content type file is: ++++++++++++++++++",file.getContentType());
        logger.info("Name is: ++++++++++++++++++",fileName);
        logger.info("Content : ++++++++++++++++++",file.getBytes());
        try {
            Path targetLocation = this.fileLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            fileMapper.insertFileUrl(new File(null,fileName,file.getContentType(),""+file.getSize(),owner.getUserId(),file.getBytes()));
        }catch (IOException ex){
            throw new FileStorageException(String.format(AppConstant.FILE_STORAGE_EXCEPTION, fileName), ex);
        }

        return fileName;
    }

    @Override
    public Resource loadFile(String fileName) {
        try {
            Path filePath = this.fileLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException(AppConstant.FILE_NOT_FOUND + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException(AppConstant.FILE_NOT_FOUND + fileName, ex);
        }
    }

    @Override
    public File findFile(String fileName) {
        return fileMapper.getFile(fileName);
    }

    @Override
    public List<File> getAllFiles(int userId){
        return fileMapper.getAllFiles(userId);
    }

    @Override
    public Integer deleteFile(int id) {

        return fileMapper.deleteFile(id);
    }
}
