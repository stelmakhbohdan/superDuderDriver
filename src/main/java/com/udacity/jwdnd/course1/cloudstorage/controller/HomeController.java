package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private FileService fileService;
    @Autowired
    private NoteServices noteServices;
    @Autowired
    private CredentialService credentialService;
    @Autowired
    private AuthenticatedUserService authenticatedUserService;
    @Autowired
    private EncryptionService encryptionService;


    @GetMapping("/home")
    public String getHomePage(@ModelAttribute("noteForm") NoteForm noteForm, @ModelAttribute("credentialForm") CredentialForm credentialForm, Authentication authentication, Model model) {

        model.addAttribute("name", authenticatedUserService.getLoggedInName());

        List<Files> filesList;
        try {
            filesList = fileService.loadFiles();
        } catch (NullPointerException e) {
            filesList = new ArrayList<>();
        }
//
        List<Notes> notesList;

        try {
            notesList = noteServices.getNotes();
        } catch (NullPointerException e) {
            notesList = new ArrayList<>();
        }

        Credentials credentials;

        try {
            credentials = credentialService.getCredentialsEncoded();
        } catch (NullPointerException e) {
            credentials =  new Credentials();
        }

        model.addAttribute("files", filesList);
        model.addAttribute("fileSize", filesList.size());
        model.addAttribute("notes", notesList);
        model.addAttribute("noteSize", notesList.size());
        model.addAttribute("credentials", credentials);
//        model.addAttribute("credentialSize", credentials.size());
        model.addAttribute("encryptService", encryptionService);

        return "home";
    }
}