package org.launchcode.Financial_Budget.controllers;

import org.launchcode.Financial_Budget.models.Category;
import org.launchcode.Financial_Budget.models.Expense;
import org.launchcode.Financial_Budget.models.User;
import org.launchcode.Financial_Budget.models.data.CategoryRepository;
import org.launchcode.Financial_Budget.models.data.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class ExpenseController {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    AunthenticationController authenticationController;

    @RequestMapping("/expense-view")
    public String viewExpenseForm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        model.addAttribute("expenseList",expenseRepository.findAllByUsers_Id(user.getId()));
        return "viewExpense";
    }

    @GetMapping("/addExpense")
    public String addExpense(Model model,HttpServletRequest request) {
        Expense expense=new Expense();
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        model.addAttribute("categories", categoryRepository.findAllByUsers_Id(user.getId()));
        model.addAttribute("expense", expense);
        return "addExpense";
    }

    @GetMapping("/showFormForExpenseUpdate/{id}")
    public String showFormForExpenseUpdate(@PathVariable (value="id") int id, Model model) {
        Expense expense=getExpenseById(id);
        model.addAttribute("expense", expense);
        return "addExpense";
    }

    @PostMapping("/saveExpense")
    public String saveCategory(@ModelAttribute @Valid Expense expense,
                               Errors errors, Model model, @RequestParam int categoryId,HttpServletRequest request) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Expense");
            return "addExpense";
        }

      /*  if(expense.getId()!=0) {
            HttpSession session = request.getSession();
            User user = authenticationController.getUserFromSession(session);
            //expense.setUsers(user);
            Expense updateExpense = new Expense();
            updateCategory.setId(category.getId());
            updateCategory.setCategoryName(category.getCategoryName());
            updateCategory.setCategoryDescription(category.getCategoryDescription());
            updateCategory.setUsers(category.getUsers());
            model.addAttribute("categoryList", this.categoryRepository.save(updateCategory));
            model.addAttribute("categoryList", categoryRepository.findAllByUsers_Id(user.getId()));
        }*/
             HttpSession session = request.getSession();
             User user = authenticationController.getUserFromSession(session);
            Optional optCategory = categoryRepository.findById(categoryId);
            if (optCategory.isPresent()) {
            Category category = (Category) optCategory.get();
            model.addAttribute("category", category);
            expense.setCategory(category);
            expense.setUsers(user);
            model.addAttribute("expenseList", this.expenseRepository.save(expense));
            model.addAttribute("expenseList",expenseRepository.findAllByUsers_Id(user.getId()));
            return "viewExpense";
             } else {
            return "redirect:../";

         }

      }

    public Expense getExpenseById(int id)
    {
        Optional<Expense> opt=expenseRepository.findById(id);
        Expense expense=null;
        if(opt.isPresent())
        {
            expense=opt.get();
        }
        else
        {
            throw new RuntimeException("Expense not found::"+id);
        }
        return expense;
    }
}