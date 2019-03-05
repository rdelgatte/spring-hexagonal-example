package com.rdelgatte.hexagonal;

import com.rdelgatte.hexagonal.api.CustomerService;
import com.rdelgatte.hexagonal.api.ProductService;
import com.rdelgatte.hexagonal.domain.Customer;
import com.rdelgatte.hexagonal.domain.Product;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@AllArgsConstructor
public class HexagonalRunner implements CommandLineRunner {

  private ProductService productService;
  private CustomerService customerService;

  public static void main(String[] args) {
    SpringApplication.run(HexagonalRunner.class, args);
  }

  @Override
  public void run(String... args) {
    productService.getAllProducts()
        .map(Product::toString)
        .forEach(log::info);
    String anyLogin = "rdelgatte";
    customerService.signUp(anyLogin);
    productService.createProduct(new Product(UUID.randomUUID(), "01123", "ANY_LABEL_0123", BigDecimal.valueOf(120.90)));
    productService.createProduct(new Product(UUID.randomUUID(), "01456", "ANY_LABEL_0456", BigDecimal.valueOf(130.90)));
    productService.createProduct(new Product(UUID.randomUUID(), "01789", "ANY_LABEL_0789", BigDecimal.valueOf(150.90)));
    productService.getAllProducts()
        .map(Product::toString)
        .forEach(log::info);
    customerService.addProductToCart(anyLogin, "01123");
    customerService.addProductToCart(anyLogin, "01456");
    customerService.addProductToCart(anyLogin, "01789");
    customerService.findCustomer(anyLogin)
        .map(Customer::toString)
        .forEach(log::info);
  }
}
