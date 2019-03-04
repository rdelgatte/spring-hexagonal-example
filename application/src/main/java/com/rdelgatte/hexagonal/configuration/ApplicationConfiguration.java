package com.rdelgatte.hexagonal.configuration;

import com.rdelgatte.hexagonal.api.CustomerService;
import com.rdelgatte.hexagonal.api.CustomerServiceImpl;
import com.rdelgatte.hexagonal.api.ProductService;
import com.rdelgatte.hexagonal.api.ProductServiceImpl;
import com.rdelgatte.hexagonal.infrastructure.memory.InMemoryCustomerRepository;
import com.rdelgatte.hexagonal.infrastructure.memory.InMemoryProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

  private static final InMemoryProductRepository inMemoryProductRepository = new InMemoryProductRepository();

  private InMemoryCustomerRepository getCustomerRepository() {
    return new InMemoryCustomerRepository();
  }

  @Bean
  CustomerService customerService() {
    return new CustomerServiceImpl(getCustomerRepository(), inMemoryProductRepository);
  }

  @Bean
  ProductService productService() {
    return new ProductServiceImpl(inMemoryProductRepository);
  }
}
