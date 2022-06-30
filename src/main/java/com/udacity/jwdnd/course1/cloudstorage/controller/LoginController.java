package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticatedUserService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {
    @Autowired
    AuthenticationManager authenticationManager;

    Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    @GetMapping(value={"/login", "/"})
    public String loginView() {
        return "login";
    }

    @RequestMapping("/login-user")
    public String loginUser(@RequestParam("userName") String username,
                            @RequestParam("password") String password) {
        Authentication result = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(result);

        log.info("LOGIN CONTROLLER");
        Users user = userService.getUser(username);
        authenticatedUserService.setUser(username);
        log.info(authenticatedUserService.getLoggedInUser().toString());
        if (user == null) {
            return "error";
        } else
            return "redirect:/home";
    }
}
