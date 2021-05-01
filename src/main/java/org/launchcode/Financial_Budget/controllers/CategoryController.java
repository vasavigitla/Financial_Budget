package org.launchcode.Financial_Budget.controllers;

import org.launchcode.Financial_Budget.models.Category;
import org.launchcode.Financial_Budget.models.data.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping("/category-view")
    public String list(Model model) {
        model.addAttribute("categoryList", categoryRepository.findAll());
        return "view";
    }

    @GetMapping("/addCategory")
    public String addCategory(Model model) {
        Category category=new Category();
        model.addAttribute("category", category);
        return "addCategory";
    }


    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute @Valid Category category,
                              Errors errors, Model model) {
        model.addAttribute("categoryList", categoryRepository.save(category));
        return "view";
    }
}
