package org.launchcode.Financial_Budget.models.data;

import org.launchcode.Financial_Budget.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

}