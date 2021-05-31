package org.launchcode.Financial_Budget.models.data;

import org.launchcode.Financial_Budget.models.Category;
import org.launchcode.Financial_Budget.models.Expense;
import org.launchcode.Financial_Budget.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
    public interface CategoryRepository extends CrudRepository<Category, Integer> {

        public List<Category> findAllByUsers_Id(int userId);

    @Query(
            value = "SELECT * FROM CATEGORY C WHERE C.USERS_ID = :userId AND C.ID NOT IN " +
                    "(SELECT CATEGORY_ID FROM EXPENSE E WHERE E.USERS_ID = :userId)",
            nativeQuery = true)
    public List<Category> findAllAvailableCategories(@Param("userId") int userId);


}
