package org.launchcode.Financial_Budget.models.data;

import org.launchcode.Financial_Budget.models.Category;
import org.launchcode.Financial_Budget.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
    public interface CategoryRepository extends CrudRepository<Category, Integer> {

}
