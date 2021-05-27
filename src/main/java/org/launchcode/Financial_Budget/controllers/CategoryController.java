package org.launchcode.Financial_Budget.controllers;

import org.launchcode.Financial_Budget.controllers.AunthenticationController;
import com.mysql.cj.Session;
import org.launchcode.Financial_Budget.models.Category;
import org.launchcode.Financial_Budget.models.Expense;
import org.launchcode.Financial_Budget.models.User;
import org.launchcode.Financial_Budget.models.data.CategoryRepository;
import org.launchcode.Financial_Budget.models.data.ExpenseRepository;
import org.launchcode.Financial_Budget.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller

public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    AunthenticationController authenticationController;

    @RequestMapping("/category-view")
    public String getCategory(Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        model.addAttribute("categoryList", categoryRepository.findAllByUsers_Id(user.getId()));
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
    public String deleteCategory(@PathVariable (value="id") int id, Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        Category category=getCategoryById(id);
        Expense expense=expenseRepository.findByCategory_Id(id);
        if ((expense!=null))
        {
            expenseRepository.delete(expense);
        }
        categoryRepository.delete(category);
        model.addAttribute("categoryList", categoryRepository.findAllByUsers_Id(user.getId()));
        return "redirect:../category-view";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute @Valid Category category,
                               Errors errors, Model model, HttpServletRequest request) {

        if(category.getId()!=0) {
            HttpSession session = request.getSession();
            User user = authenticationController.getUserFromSession(session);
            category.setUsers(user);
            Category updateCategory = new Category();
            updateCategory.setId(category.getId());
            updateCategory.setCategoryName(category.getCategoryName());
            updateCategory.setCategoryDescription(category.getCategoryDescription());
            updateCategory.setUsers(category.getUsers());
            model.addAttribute("categoryList", this.categoryRepository.save(updateCategory));
            model.addAttribute("categoryList", categoryRepository.findAllByUsers_Id(user.getId()));
        }
        else
        {   HttpSession session = request.getSession();
            User user = authenticationController.getUserFromSession(session);
            category.setUsers(user);
            List<Category> categories=new ArrayList<>();
            categories=categoryRepository.findAllByUsers_Id(user.getId());
            for (Category cat : categories) {
                if(cat.getCategoryName().equals(category.getCategoryName()))
                {
                    errors.rejectValue("categoryName", " categoryName.alreadyexists", "A category with that categoryname already exists");
                    System.out.println("A category with that categoryname already exists");
                    return "addCategory";
                }
            }
            model.addAttribute("categoryList", this.categoryRepository.save(category));
            model.addAttribute("categoryList", categoryRepository.findAllByUsers_Id(user.getId()));
        }

        return "redirect:category-view";
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
