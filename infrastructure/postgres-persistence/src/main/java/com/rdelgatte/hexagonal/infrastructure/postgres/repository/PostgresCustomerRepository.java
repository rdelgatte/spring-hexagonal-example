package com.rdelgatte.hexagonal.infrastructure.postgres.repository;

import static io.vavr.collection.List.ofAll;
import static io.vavr.control.Option.ofOptional;

import com.rdelgatte.hexagonal.domain.Customer;
import com.rdelgatte.hexagonal.infrastructure.postgres.dao.PostgresCustomer;
import com.rdelgatte.hexagonal.spi.CustomerRepository;
import io.vavr.collection.List;
import io.vavr.control.Option;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * Implements the SPI {@link CustomerRepository} as Postgres provider.
 */
@Repository
@AllArgsConstructor
public class PostgresCustomerRepository implements CustomerRepository {

  private PostgresSpringDataCustomerRepository postgresSpringDataCustomerRepository;

  @Override
  public Customer save(Customer customer) {
    return postgresSpringDataCustomerRepository.save(PostgresCustomer.fromCustomer(customer)).toCustomer();
  }

  @Override
  public Option<Customer> findById(UUID id) {
    return ofOptional(postgresSpringDataCustomerRepository.findById(id).map(PostgresCustomer::toCustomer));
  }

  @Override
  public Option<Customer> findByLogin(String login) {
    return ofOptional(postgresSpringDataCustomerRepository.findByName(login).map(PostgresCustomer::toCustomer));
  }

  @Override
  public List<Customer> findAll() {
    return ofAll(postgresSpringDataCustomerRepository.findAll()).map(PostgresCustomer::toCustomer);
  }
}
