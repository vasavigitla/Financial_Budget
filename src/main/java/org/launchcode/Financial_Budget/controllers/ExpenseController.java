package org.launchcode.Financial_Budget.controllers;

import org.launchcode.Financial_Budget.models.Category;
import org.launchcode.Financial_Budget.models.Expense;
import org.launchcode.Financial_Budget.models.User;
import org.launchcode.Financial_Budget.models.data.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ExpenseController {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryRepository expenseRepository;
    @Autowired
    AunthenticationController authenticationController;

    @RequestMapping("/expense-view")
    public String viewExpenseForm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        //model.addAttribute("categoryList", categoryRepository.findAllByUsers_Id(user.getId()));
        return "viewExpense";
    }
    @GetMapping("/addExpense")
    public String addExpense(Model model) {
        Expense expense=new Expense();
        model.addAttribute("expense", expense);
        return "addExpense";
    }

}
