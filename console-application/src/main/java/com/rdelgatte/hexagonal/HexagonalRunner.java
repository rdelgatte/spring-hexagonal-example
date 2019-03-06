package com.rdelgatte.hexagonal;

import static java.util.UUID.randomUUID;

import com.rdelgatte.hexagonal.api.CustomerService;
import com.rdelgatte.hexagonal.api.ProductService;
import com.rdelgatte.hexagonal.domain.Product;
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
    createProduct(new Product(randomUUID(), "1", "Free product", BigDecimal.ZERO));
    createProduct(new Product(randomUUID(), "2", "Product we give you money to buy it", BigDecimal.valueOf(-1)));
    createProduct(new Product(randomUUID(), "3", "Real product", BigDecimal.valueOf(13.05)));
  }

  private void createProduct(Product product) {
    Try.of(() -> productService.createProduct(product))
        .onFailure(throwable -> log.error(throwable.getMessage()))
        .map(Product::toString)
        .onSuccess(log::info);
  }
}
