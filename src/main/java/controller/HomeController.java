package controller;

import com.udacity.jwdnd.course1.cloudstorage.services.*;
import model.Credential;
import model.File;
import model.Note;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    private FileService fileService;
    private NoteService noteService;
    private EncryptionService encryptionService;
    private CredentialService credentialService;
    private AuthenticatedUserService authenticatedUserService;

    public HomeController(FileService fileService, NoteService noteService
            , EncryptionService encryptionService, CredentialService credentialService
            , AuthenticatedUserService authenticatedUserService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.encryptionService = encryptionService;
        this.credentialService = credentialService;
        this.authenticatedUserService = authenticatedUserService;
    }

    public String getHomePage(@ModelAttribute Note note, @ModelAttribute Credential credential
            , Model model, Authentication authentication){
        model.addAttribute("name",authenticatedUserService.getLoggedInUserId());
        List<File> files;
        try {
            files = fileService.loadFiles();
        }catch (NullPointerException e){
            files = new ArrayList<>();
        }

        List<Note> notes;
        try {
            notes = noteService.getNotes();
        }catch (NullPointerException e){
            notes = new ArrayList<>();
        }

        List<Credential> credentials;
        try {
            credentials = credentialService.getCredential();
        }catch (NullPointerException e){
            credentials = new ArrayList<>();
        }
        //model.add()

        model.addAttribute("files",files);
        model.addAttribute("filesSize",files.size());
        model.addAttribute("notes",notes);
        model.addAttribute("noteSize",notes.size());
        model.addAttribute("credentials",credentials);
        model.addAttribute("credentialsSize",credentials.size());
        return "home";
    }
}
