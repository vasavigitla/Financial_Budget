package org.launchcode.Financial_Budget.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Expense extends AbstractEntity {

    @NotNull
    private int expense_amount;

    @OneToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    // @NotNull(message = "Category is required")
    @JoinColumn
    private User users;

    public int getExpense_amount() {
        return expense_amount;
    }

    public void setExpense_amount(int expense_amount) {
        this.expense_amount = expense_amount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
   }


}
