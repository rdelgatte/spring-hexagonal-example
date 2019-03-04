package com.rdelgatte.hexagonal.spi;

import com.rdelgatte.hexagonal.domain.Customer;
import io.vavr.collection.List;
import io.vavr.control.Option;
import java.util.UUID;

public interface CustomerRepository {

  Customer save(Customer customer);

  Option<Customer> findById(UUID id);

  Option<Customer> findByLogin(String login);

  List<Customer> findAll();
}
