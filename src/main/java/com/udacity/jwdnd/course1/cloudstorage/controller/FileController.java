package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileController {
    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/file/{fileName:.+}")
    public ResponseEntity<Resource> viewFile(@PathVariable String fileName, org.springframework.security.core.Authentication authentication){
        Files file = fileService.loadFile(fileName);
        try{
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getFileName())
                    .contentLength(file.getFileData().length)
                    .body(new ByteArrayResource(file.getFileData()));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/file/delete/{fileName:.+}")
    public String deleteFile(@PathVariable String fileName, org.springframework.security.core.Authentication authentication, RedirectAttributes redirectAttributes){
        fileService.deleteFile(fileName);
        redirectAttributes.addFlashAttribute("deleteSuccess","Successfully deleted " + fileName);
        return "redirect:/result";
    }

    @PostMapping("/file/upload")
    public String uploadFile(@RequestParam("fileUpload")MultipartFile file, Authentication authentication, RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message","Please upload a file!");
            return "redirect:/home";
        }else if(!fileService.isFileNameAvailable(file.getOriginalFilename())){
            redirectAttributes.addFlashAttribute("message","File with this name already exists!");
            return "redirect:/home";
        }
        int row = fileService.uploadNewFile(file);
        if(row < 0){
            redirectAttributes.addFlashAttribute("message","Please try again!");
            return "redirect:/home";
        }
        redirectAttributes.addFlashAttribute("message","Succesfully uploaded " + file.getOriginalFilename());
        return "redirect:/home";
    }

}
