package org.launchcode.Financial_Budget.models.data;

import org.launchcode.Financial_Budget.models.Expense;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PieChartRepository extends CrudRepository<Expense, Integer> {
}
