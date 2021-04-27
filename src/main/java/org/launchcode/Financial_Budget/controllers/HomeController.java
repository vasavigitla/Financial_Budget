package org.launchcode.Financial_Budget.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value="")
public class HomeController {

    public String index() {
        return "index";
    }

}
