package controller;


import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import model.Credential;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String addCredential(@ModelAttribute Credential credential, Model model,RedirectAttributes redirectAttributes){
        if (credential.getCredentialid()==null){
            credentialService.addCredential(credential);
        }else {
            credentialService.updateCredential(credential);
            redirectAttributes.addFlashAttribute("editCredential","Credential edited successfully!");
            return "redict:/result";
        }
        return "redict:/home";

    }
    @GetMapping("/delete/{credentialId}")
    public String deleteCredentials(@PathVariable Integer credentialId, Authentication authentication, RedirectAttributes redirectAttributes){
        credentialService.deleteCredential(credentialId);
        redirectAttributes.addFlashAttribute("deleteSuccess","Deleted successfully!");
        return "redict/result";
    }
}
