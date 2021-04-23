package com.antonbaeckelandt.debeziumtest.data;

import com.antonbaeckelandt.debeziumtest.models.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
}
