package org.launchcode.Financial_Budget.models;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Expense extends AbstractEntity {

    @NotNull
    private int expense_amount;

    @NotNull
    private String category;

    public int getExpense_amount() {
        return expense_amount;
    }

    public void setExpense_amount(int expense_amount) {
        this.expense_amount = expense_amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
