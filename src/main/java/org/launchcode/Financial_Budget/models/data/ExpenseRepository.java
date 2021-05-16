package org.launchcode.Financial_Budget.models.data;

import org.launchcode.Financial_Budget.models.Category;
import org.launchcode.Financial_Budget.models.Expense;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Integer> {

    public Expense findByCategory_Id(int category_id);

}
