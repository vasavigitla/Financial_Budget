package org.launchcode.Financial_Budget.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category extends AbstractEntity{

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "category_description")
    private String categoryDescription;

    @ManyToOne
   // @NotNull(message = "Category is required")
    @JoinColumn
    private User users;



    public Category() {

    }

    public Category(String categoryName, String categoryDescription) {
        super();
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }


    
}
