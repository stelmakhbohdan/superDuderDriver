package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping("/add")
    public String addCredential(@ModelAttribute("credentialform") CredentialForm credentialForm, org.apache.tomcat.util.net.openssl.ciphers.Authentication authentication, RedirectAttributes redirectAttributes) {

        if (credentialForm.getCredentialId() == null) {
            credentialService.addCredential(credentialForm);
        } else {
            credentialService.updateCredential(credentialForm);
            redirectAttributes.addFlashAttribute("editCredSuccess", "Credential edited.");
            return "redirect:/result";
        }
        return "redirect:/result";
    }

    @GetMapping("/delete/{credentialId:.+}")
    public String deleteNote(@PathVariable Integer credentialId, Authentication authentication, RedirectAttributes redirectAttributes) {
        credentialService.deleteCredential(credentialId);
        redirectAttributes.addFlashAttribute("deleteCredSuccess", "Credential deleted successfully.");
        return "redirect:/result";
    }
}