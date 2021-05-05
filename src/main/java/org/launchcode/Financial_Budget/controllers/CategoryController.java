package org.launchcode.Financial_Budget.controllers;

import org.launchcode.Financial_Budget.models.Category;
import org.launchcode.Financial_Budget.models.data.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping("/category-view")
    public String getCategory(Model model) {
        model.addAttribute("categoryList", categoryRepository.findAll());
        return "view";
    }

    @GetMapping("/addCategory")
    public String addCategory(Model model) {
        Category category=new Category();
        model.addAttribute("category", category);
        return "addCategory";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable (value="id") int id, Model model) {
        Category category=getCategoryById(id);
        model.addAttribute("category", category);
        return "addCategory";
    }

    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable (value="id") int id, Model model) {

        Category category=getCategoryById(id);
        categoryRepository.delete(category);
        model.addAttribute("categoryList", categoryRepository.findAll());
        return "view";
    }

    @PostMapping("/saveCategory")
    public String saveCategory( @ModelAttribute @Valid Category category,
                              Errors errors, Model model) {
        if(category.getId()!=0) {
            Category updateCategory = new Category();
            updateCategory.setId(category.getId());
            updateCategory.setCategoryName(category.getCategoryName());
            updateCategory.setCategoryDescription(category.getCategoryDescription());
            model.addAttribute("categoryList", this.categoryRepository.save(updateCategory));
        }
        else
        {
            model.addAttribute("categoryList", this.categoryRepository.save(category));
        }
        model.addAttribute("categoryList", categoryRepository.findAll());
        return "view";
    }

    public Category getCategoryById(int id)
    {
        Optional<Category> opt=categoryRepository.findById(id);
        Category category=null;
        if(opt.isPresent())
        {
           category=opt.get();

        }
        else
        {
            throw new RuntimeException("Category not found::"+id);
        }
        return category;
    }
}
