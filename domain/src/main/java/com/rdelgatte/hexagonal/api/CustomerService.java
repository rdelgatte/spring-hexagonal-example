package com.rdelgatte.hexagonal.api;

import com.rdelgatte.hexagonal.domain.Customer;
import io.vavr.control.Option;

public interface CustomerService {

  Customer signUp(String name);

  Option<Customer> findCustomer(String name);

  Customer addProductToCart(String name, String productCode);

  Customer emptyCart(String name);
}
