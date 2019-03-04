package com.rdelgatte.hexagonal.api;

import com.rdelgatte.hexagonal.domain.Customer;
import io.vavr.control.Option;

public interface CustomerService {

  Option<Customer> findCustomer(String login);

  Customer signIn(String login);

  Customer addProductToCart(String login, String productCode);

  Customer emptyCart(String login);
}
