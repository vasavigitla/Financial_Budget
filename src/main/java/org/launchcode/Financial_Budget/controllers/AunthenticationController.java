package org.launchcode.Financial_Budget.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AunthenticationController {

    @RequestMapping("register")
    public String registration(Model model) {
        model.addAttribute("employers", "hiii");
        return "register";
    }
}
