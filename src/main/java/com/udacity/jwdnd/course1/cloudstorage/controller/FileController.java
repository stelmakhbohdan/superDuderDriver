package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller()
public class FileController {

    private Logger logger = LoggerFactory.getLogger(FileController.class);

    private FileService fileService;
    private FileMapper fileMapper;

    public FileController(FileService fileService, FileMapper fileMapper) {
        this.fileService = fileService;
        this.fileMapper = fileMapper;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, org.springframework.security.core.Authentication authentication, Model model, RedirectAttributes redirectAttributes) {

        String username = authentication.getName();

        if (fileMapper.getFilebyFilename(fileUpload.getOriginalFilename()) != null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot upload two files with the same name");
        } else {
            try {
                fileService.createFile(fileUpload, username);
                redirectAttributes.addFlashAttribute("successMessage", "Your file was successfully added");
            } catch (Exception e) {
                logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong with the file upload... Please try again.");
                return "redirect:/result";
            }
        }
        return "redirect:/result";
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable int fileId, RedirectAttributes redirectAttributes) {
        try {
            fileService.deleteFileByFilename(fileId);
            redirectAttributes.addFlashAttribute("successMessage", "Your file was deleted successfully.");
            return "redirect:/result";
        } catch (Exception e) {
            logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong with the file deletion... Please try again.");
            return "redirect:/result";
        }
    }

    @GetMapping("/view/{fileId}")
    public ResponseEntity<ByteArrayResource> viewFile(@PathVariable int fileId) {
        try {
            File file = fileService.getFileByFileid(fileId);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(file.getContenttype()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(new ByteArrayResource(file.getFiledate()));
        } catch (Exception e) {
            logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
