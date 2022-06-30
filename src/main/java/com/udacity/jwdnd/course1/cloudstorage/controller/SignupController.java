package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignupController {

    @Autowired
    private UserService userSevice;

//    public SignupController() {
//        this.userSevice = userSevice;
//    }

    @GetMapping()
    public String signupView(){return "signup";}

    @PostMapping()
    public String signupUser(@ModelAttribute Users user, Model model, RedirectAttributes redirectAttributes){
        String signupError = null;

        if(!userSevice.isUsernameAvailable(user.getUserName())){
            signupError = "Sorry, username already exist! try with another username.";
        }

        if(signupError == null){
            int rowsAdded = userSevice.createUser(user);
            if(rowsAdded < 0){
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        if(signupError != null){
            model.addAttribute("signupError",signupError);
            return "signup";
        }

        redirectAttributes.addFlashAttribute("signupSuccess",true);

        return "redirect:/login";
    }

}