package com.company.voda.controller;

import com.company.voda.model.User;
import com.company.voda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/registration")
    public String register() {

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) {

        if (!userService.addUser(user)) {
            model.addAttribute("message","User exists!");
            return "registration";
        }


        model.addAttribute("message","Activate your account, message sent to your email");

        return "login";
    }

    @GetMapping("/activate/")
    public String activate() {


        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {



        boolean isActivate = userService.activateUser(code);

        if (isActivate) {
            model.addAttribute("message","Successful activation");
            return "login";
        }


        model.addAttribute("message","Something is wrong");
        return "login";
    }

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    public String currentUserName(Principal principal) {
        System.out.println(principal.getName());
        return "home";
    }



}
