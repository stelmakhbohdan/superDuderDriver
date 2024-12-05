package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private FileService fileService;

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private UserService userService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private EncryptionService encryptionService;

    @GetMapping
    public String homeView(Model model,RedirectAttributes redirectAttributes, Authentication authentication){
        String username = authentication.getName();
        User user =userService.getUser(username);
        if(user==null || username==null ){
            redirectAttributes.addFlashAttribute("errorMessage","Please login first");
            return "redirect:/login";
        }else {
            int userId = user.getUserId();
            model.addAttribute("fileslist", fileService.getAllFiles(userId));
            model.addAttribute("noteslist", noteService.getAllNotes(userId));
            model.addAttribute("credentialslist", credentialService.getCredentials(userId));
            model.addAttribute("encryptionService", encryptionService);
            return "home";
        }
    }
}
