package com.rdelgatte.hexagonal.infrastructure.memory;

import static io.vavr.API.List;

import com.rdelgatte.hexagonal.domain.Customer;
import com.rdelgatte.hexagonal.spi.CustomerRepository;
import io.vavr.collection.List;
import io.vavr.control.Option;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Wither
public class InMemoryCustomerRepository implements CustomerRepository {

  private List<Customer> customers = List();

  public Customer save(Customer customer) {
    Option<Customer> maybeCustomer = findByLogin(customer.getLogin());
    if (maybeCustomer.isEmpty()) {
      this.customers = getCustomers().append(customer);
    } else {
      this.customers = getCustomers().remove(maybeCustomer.get()).append(customer);
    }
    return customer;
  }

  public Option<Customer> findById(UUID id) {
    return getCustomers().find(customer -> id.equals(customer.getId()));
  }

  public Option<Customer> findByLogin(String login) {
    return getCustomers().find(customer -> login.equals(customer.getLogin()));
  }

  public List<Customer> findAll() {
    return getCustomers();
  }
}
