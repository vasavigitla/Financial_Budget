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
import java.util.*;

@Controller
public class ExpenseController {

    private static final Random RANDOM = new Random(System.currentTimeMillis());

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
        return "updateExpense";
    }

    @GetMapping("/deleteExpense/{id}")
    public String deleteExpense(@PathVariable (value="id") int id, Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        Expense expense=getExpenseById(id);
        expenseRepository.delete(expense);
        model.addAttribute("expenseList", expenseRepository.findAllByUsers_Id(user.getId()));
        return "redirect:../expense-view";
    }
    @RequestMapping("/piechart-view")
    public String getPieChart(Model model,HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
       // Optional<Expense> result=expenseRepository.findById(user.getId());
       // if (result.isPresent()) {
           // Expense expense = (Expense) result.get();

            //List<List<Expense>> listOfLists = new ArrayList<>();
            List<Expense> expense1 = new ArrayList<>();
            expense1=expenseRepository.findAllByUsers_Id(user.getId());
           // listOfLists.add(expense1);

        List<Object> listOfMixedTypes = new ArrayList<>();
        List<List<Object>>listOfMixedTypes1 = new ArrayList<>();
        //ArrayList<String> listOfStrings = new ArrayList<String>();
        //ArrayList<Integer> listOfIntegers = new ArrayList<Integer>();



        for (Expense list : expense1) {

            listOfMixedTypes = new ArrayList<>();
            listOfMixedTypes.add(list.getCategory().getCategoryName());
            listOfMixedTypes.add(list.getExpense_amount());
            listOfMixedTypes1.add( listOfMixedTypes);
        }

        System.out.println(listOfMixedTypes1);

       // }

        model.addAttribute("chartData",  listOfMixedTypes1);
           // model.addAttribute("expenseList", expense);



        List<List<Expense>> listOfLists1 = new ArrayList<>();
        List<String> expense2 = new ArrayList<>();
            //getChartData(listOfLists);
//        listOfLists.forEach((list) -> {
//            list.forEach(
//                    (num) -> expense2.add(num.getCategory().getCategoryName())
//
//
//
//            );
//        });
        
        return "viewPieChart";
    }

    private List<List<Object>> getChartData() {
        return List.of(
                List.of("Mushrooms", RANDOM.nextInt(5)),
                List.of("Onions", RANDOM.nextInt(5)),
                List.of("Olives", RANDOM.nextInt(5)),
                List.of("Zucchini", RANDOM.nextInt(5)),
                List.of("Pepperoni", RANDOM.nextInt(5))
        );
    }

    @PostMapping("/saveExpense")
    public String saveExpense(@ModelAttribute @Valid Expense expense,
                               Errors errors, Model model, @RequestParam int categoryId,HttpServletRequest request) {
            if (errors.hasErrors()) {
            model.addAttribute("title", "Add Expense");
            return "addExpense";
            }
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
            return "redirect:/expense-view";
             } else {
            return "redirect:../";

         }

      }


    @PostMapping("/updateExpense")
    public String updateExpense(@ModelAttribute @Valid Expense expense,
                               Errors errors, Model model, HttpServletRequest request) {

           if(expense.getId()!=0) {
            HttpSession session = request.getSession();
            User user = authenticationController.getUserFromSession(session);
            expense.setUsers(user);
            Optional<Expense> updateExpenseOptional = this.expenseRepository.findById(expense.getId());
               if(updateExpenseOptional.isPresent()) {
                   Expense updateExpense = updateExpenseOptional.get();
                   updateExpense.setExpense_amount(expense.getExpense_amount());
                   model.addAttribute("expenseList", this.expenseRepository.save(updateExpense));
               }
            model.addAttribute("expenseList", this.expenseRepository.findAllByUsers_Id(user.getId()));
        }

    return "viewExpense";
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
