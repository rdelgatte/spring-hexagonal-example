package com.rdelgatte.hexagonal;

import static java.lang.System.out;

import com.rdelgatte.hexagonal.api.CustomerService;
import com.rdelgatte.hexagonal.api.ProductService;
import com.rdelgatte.hexagonal.domain.Customer;
import com.rdelgatte.hexagonal.domain.Product;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HexagonalRunner implements CommandLineRunner {

  @Autowired
  private ProductService productService;
  @Autowired
  private CustomerService customerService;

  public static void main(String[] args) {
    SpringApplication.run(HexagonalRunner.class, args);
  }

  @Override
  public void run(String... args) {
    String anyLogin = "rdelgatte";
    customerService.signIn(anyLogin);
    productService.createProduct(new Product(UUID.randomUUID(), "0123", "ANY_LABEL_0123", 120.90));
    productService.createProduct(new Product(UUID.randomUUID(), "0456", "ANY_LABEL_0456", 130.90));
    productService.createProduct(new Product(UUID.randomUUID(), "0789", "ANY_LABEL_0789", 150.90));
    productService.getAllProducts()
        .map(Product::toString)
        .forEach(out::println);
    customerService.addProductToCart(anyLogin, "0123");
    customerService.addProductToCart(anyLogin, "0456");
    customerService.addProductToCart(anyLogin, "0789");
    customerService.findCustomer(anyLogin).map(Customer::toString).forEach(out::println);
  }
}
