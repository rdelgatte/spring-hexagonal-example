package com.rdelgatte.hexagonal.infrastructure.memory;

import com.rdelgatte.hexagonal.domain.Product;
import com.rdelgatte.hexagonal.spi.ProductRepository;
import io.vavr.API;
import io.vavr.collection.List;
import io.vavr.control.Option;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Wither
public class InMemoryProductRepository implements ProductRepository {

  private List<Product> inMemoryProducts = API.List();

  public Product addProduct(Product product) {
    this.inMemoryProducts = getInMemoryProducts().append(product);
    return product;
  }

  public void deleteProduct(UUID productId) {
    this.inMemoryProducts = getInMemoryProducts().filter(product -> !product.getId().equals(productId));
  }

  public Option<Product> findProductByCode(String code) {
    return getInMemoryProducts().find(product -> product.getCode().equals(code));
  }

  public List<Product> findAllProducts() {
    return getInMemoryProducts();
  }
}
