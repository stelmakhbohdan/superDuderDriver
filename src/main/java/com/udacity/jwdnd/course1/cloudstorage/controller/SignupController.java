package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
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

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getSignupPage() {
        return "signup";
    }

    @PostMapping
    public String signupUser(@ModelAttribute User user, Model model){
        String signupErrorMsg = null; // this variable is read by signup.html
        String returnPage = null;

        if (!userService.isUsernameAvailable(user.getUsername()))
            signupErrorMsg = "The username already exists!";

        if (signupErrorMsg == null) {
            int rowsAdded = userService.createUser(user);
            if (rowsAdded < 0) {
                signupErrorMsg = "There was an error signing you up. Please try again.";
            }
        }

        if (signupErrorMsg == null) {
            model.addAttribute("signupSuccess", true);
            returnPage = "login";
        } else {
            model.addAttribute("signupErrorMsg", signupErrorMsg);
            returnPage = "signup";
        }

        return returnPage;
    }
}