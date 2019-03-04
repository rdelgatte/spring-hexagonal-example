package com.rdelgatte.hexagonal.infrastructure.postgres.repository;

import static io.vavr.collection.List.ofAll;
import static io.vavr.control.Option.ofOptional;

import com.rdelgatte.hexagonal.domain.Product;
import com.rdelgatte.hexagonal.infrastructure.postgres.dao.PostgresProduct;
import com.rdelgatte.hexagonal.spi.ProductRepository;
import io.vavr.collection.List;
import io.vavr.control.Option;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * Implements the SPI {@link ProductRepository} as Postgres provider.
 */
@Repository
@AllArgsConstructor
public class PostgresProductRepository implements ProductRepository {

  private PostgresSpringDataProductRepository postgresSpringDataProductRepository;

  @Override
  public Product addProduct(Product product) {
    return postgresSpringDataProductRepository.save(PostgresProduct.fromProduct(product))
        .toProduct();
  }

  @Override
  public void deleteProduct(UUID productId) {
    postgresSpringDataProductRepository.deleteById(productId);
  }

  @Override
  public Option<Product> findProductByCode(String code) {
    return ofOptional(postgresSpringDataProductRepository.findByCode(code).map(PostgresProduct::toProduct));
  }

  @Override
  public List<Product> findAllProducts() {
    return ofAll(postgresSpringDataProductRepository.findAll())
        .map(PostgresProduct::toProduct);
  }
}
