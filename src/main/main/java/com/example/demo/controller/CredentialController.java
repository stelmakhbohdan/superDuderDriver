package com.example.demo.controller;

import com.example.demo.model.Credential;
import com.example.demo.service.CredentialService;
import com.example.demo.service.EncryptionService;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    private Logger logger = LoggerFactory.getLogger(CredentialController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private CredentialService credentialService;
    @Autowired
    private EncryptionService encryptionService;

    @PostMapping
    public String createOrUpdateCredential(Credential credential, Authentication authentication, RedirectAttributes redirectAttributes) {

        String secretKey = generateSecretKey();
        String encryptPassword = encryptionService.encryptValue(credential.getPassword(), secretKey);
        credential.setKey(secretKey);
        credential.setPassword(encryptPassword);

        if(credential.getCredentialId().intValue() > 0){
            try {
                credentialService.updateCredential(credential);
                redirectAttributes.addFlashAttribute("successMessage", "Your credentials were updated successful.");
                return "redirect:/home";
            } catch (Exception e) {
                logger.error("Error: " + e.getCause() + ". Message: " + e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong with the credentials update. Please try again!");
                return "redirect:/home";
            }
        }else{
            try {
                String username = authentication.getName();
                int userId = userService.getUser(username).getUserId();
                credential.setUserId(userId);
                credentialService.createCredential(credential);
                redirectAttributes.addFlashAttribute("successMessage", "Your credentials were created successful.");
                return "redirect:/home";
            }  catch (Exception e) {
                logger.error("Error: " + e.getCause() + ". Message: " + e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong with the Credential creation. Please try again!");
                return "redirect:/home";
            }
        }
    }
    @GetMapping("/delete/{credentialId}")
    public String deleteNote(@PathVariable int credentialId, RedirectAttributes redirectAttributes) {
        try {
            credentialService.deleteCredential(credentialId);
            redirectAttributes.addFlashAttribute("successMessage", "Your credentials were deleted successful.");
            return "redirect:/home";
        } catch (Exception e) {
            logger.error("Error: " + e.getCause() + ". Message: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong with the credentials delete. Please try again!");
            return "redirect:/home";
        }
    }
    private String generateSecretKey() {
        try{
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            return Base64.getEncoder().encodeToString(key);
        } catch (Exception e) {
            logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
        }
        return null;
    }
}
