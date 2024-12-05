package com.example.demo.controller;


import com.example.demo.exception.FileStorageException;
import com.example.demo.model.File;
import com.example.demo.model.User;
import com.example.demo.service.FileService;
import com.example.demo.service.UserService;
import com.example.demo.service.implimentation.FileServiceImp;
import com.example.demo.utils.AppConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {
    private Logger logger =  LoggerFactory.getLogger(FileController.class);
    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;


    @PostMapping
    public String addFile(@RequestParam(value = AppConstant.FILE_PARAM)MultipartFile file,
                                RedirectAttributes redirectAttributes) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = userService.getUser(currentPrincipalName);
       ;
            if(!file.isEmpty() &&  user != null){
                if(file.getSize() > 10000000){
                    redirectAttributes.addFlashAttribute("errorMessage","The file exceeds allowed size. Please try again");
                    return "redirect:/home";
                }
            if(fileService.findFile(file.getOriginalFilename()) != null){
                redirectAttributes.addFlashAttribute("errorMessage","The file exist. Please check the name");
                return "redirect:/home";
            }

                String savedFile=fileService.storeFile(file,user);
                logger.info("Saved filename: "+savedFile);
                redirectAttributes.addFlashAttribute("successMessage","The file"+savedFile+" saved successful.");
                return "redirect:/home" ;
        }
        redirectAttributes.addFlashAttribute("errorMessage","The were an error during saving the file. Please try again");
        return "redirect:/home";

    }
    @GetMapping("/{name}")
    public String getFile(@PathVariable String name, Model model){

        model.addAttribute("file",fileService.findFile(name));
        return "result";
    }


    @GetMapping("/delete/{id}")
    public String deleteFile(@PathVariable int id, Model model){
        model.addAttribute("successMessage",fileService.deleteFile(id));
        return "result";
    }

    @GetMapping("/download/{name}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String name){
        Resource file =fileService.loadFile(name);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
            .body(file);
    }
}
