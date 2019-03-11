package com.rdelgatte.hexagonal;

import static java.util.UUID.randomUUID;

import com.rdelgatte.hexagonal.api.CustomerService;
import com.rdelgatte.hexagonal.api.ProductService;
import com.rdelgatte.hexagonal.domain.Customer;
import com.rdelgatte.hexagonal.domain.Product;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import java.math.BigDecimal;
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
    final Product product1 = new Product(randomUUID(), "1", "Free product", BigDecimal.ZERO);
    final Product product2 = new Product(randomUUID(), "2", "Product we give you money to buy it",
        BigDecimal.valueOf(-20.50));
    final Product product3 = new Product(randomUUID(), "3", "Real product", BigDecimal.valueOf(13.05));
    productAction(() -> productService.createProduct(product1));
    productAction(() -> productService.createProduct(product2));
    productAction(() -> productService.createProduct(product3));

    String customerName = "Rémi";
    customerAction(() -> customerService.signUp(customerName));
    customerAction(() -> customerService.addProductToCart(customerName, "1"));
    customerAction(() -> customerService.addProductToCart(customerName, "2"));
    customerAction(() -> customerService.addProductToCart(customerName, "3"));
  }

  private void productAction(CheckedFunction0<Product> productFunction) {
    Try.of(productFunction)
        .onFailure(throwable -> log.error(throwable.getMessage()))
        .map(Product::toString)
        .onSuccess(log::info);
  }

  private void customerAction(CheckedFunction0<Customer> customerFunction) {
    Try.of(customerFunction)
        .onFailure(throwable -> log.error(throwable.getMessage()))
        .map(Customer::toString)
        .onSuccess(log::info);
  }
}
