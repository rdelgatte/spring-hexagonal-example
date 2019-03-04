package com.rdelgatte.hexagonal.configuration;

import com.rdelgatte.hexagonal.api.CustomerService;
import com.rdelgatte.hexagonal.api.CustomerServiceImpl;
import com.rdelgatte.hexagonal.api.ProductService;
import com.rdelgatte.hexagonal.api.ProductServiceImpl;
import com.rdelgatte.hexagonal.infrastructure.memory.InMemoryCustomerRepository;
import com.rdelgatte.hexagonal.infrastructure.postgres.repository.PostgresProductRepository;
import com.rdelgatte.hexagonal.infrastructure.postgres.repository.PostgresSpringDataProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

  private PostgresProductRepository postgresProductRepository(
      PostgresSpringDataProductRepository postgresSpringDataProductRepository) {
    return new PostgresProductRepository(postgresSpringDataProductRepository);
  }

  private InMemoryCustomerRepository getCustomerRepository() {
    return new InMemoryCustomerRepository();
  }

  @Bean
  CustomerService customerService(PostgresSpringDataProductRepository postgresSpringDataProductRepository) {
    return new CustomerServiceImpl(getCustomerRepository(),
        postgresProductRepository(postgresSpringDataProductRepository));
  }

  @Bean
  ProductService productService(PostgresSpringDataProductRepository postgresSpringDataProductRepository) {
    return new ProductServiceImpl(postgresProductRepository(postgresSpringDataProductRepository));
  }
}
