package com.mycompany.interviewtask.repository;

import com.mycompany.interviewtask.repository.model.CustomerDO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomersRepository extends CrudRepository<CustomerDO, Long> {
}
