package com.rdelgatte.hexagonal.api;

import static io.vavr.API.List;
import static io.vavr.API.None;
import static io.vavr.API.Option;

import com.rdelgatte.hexagonal.domain.Cart;
import com.rdelgatte.hexagonal.domain.Customer;
import com.rdelgatte.hexagonal.domain.Product;
import com.rdelgatte.hexagonal.spi.CustomerRepository;
import com.rdelgatte.hexagonal.spi.ProductRepository;
import io.vavr.control.Option;

public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;
  private final ProductRepository productRepository;

  public CustomerServiceImpl(CustomerRepository customerRepository,
      ProductRepository productRepository) {
    this.customerRepository = customerRepository;
    this.productRepository = productRepository;
  }

  @Override
  public Option<Customer> findCustomer(String login) {
    return customerRepository.findByLogin(login);
  }

  @Override
  public Customer signIn(String login) {
    if (login.isBlank()) {
      throw new IllegalArgumentException("Customer login should not be blank");
    }
    if (customerRepository.findByLogin(login).isDefined()) {
      throw new IllegalArgumentException("Customer already exists so you can't sign in");
    }
    return customerRepository.save(new Customer().withLogin(login));
  }

  @Override
  public Customer addProductToCart(String login, String productCode) {
    Customer customer = customerRepository.findByLogin(login)
        .getOrElseThrow(() -> new IllegalArgumentException("The customer does not exist"));

    Product product = productRepository.findProductByCode(productCode)
        .getOrElseThrow(() -> new IllegalArgumentException("The product does not exist"));

    Customer customerToUpdate = customer.withCart(Option(customer.getCart()
            .map(cart -> cart.withProducts(cart.getProducts().append(product)))
            .getOrElse(new Cart().withProducts(List(product)))
        )
    );
    return customerRepository.save(customerToUpdate);
  }

  @Override
  public Customer emptyCart(String login) {
    Customer customerToUpdate = customerRepository.findByLogin(login)
        .map(customer -> customer.withCart(None()))
        .getOrElseThrow(() -> new IllegalArgumentException("The customer does not exist"));
    return customerRepository.save(customerToUpdate);
  }
}
