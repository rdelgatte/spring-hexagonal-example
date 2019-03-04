package com.rdelgatte.hexagonal.spi;

import com.rdelgatte.hexagonal.domain.Product;
import io.vavr.collection.List;
import io.vavr.control.Option;
import java.util.UUID;

public interface ProductRepository {

  Product addProduct(Product product);

  void deleteProduct(UUID productId);

  Option<Product> findProductByCode(String code);

  List<Product> findAllProducts();
}
