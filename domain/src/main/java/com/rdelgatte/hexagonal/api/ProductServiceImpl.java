package com.rdelgatte.hexagonal.api;

import static java.math.BigDecimal.ZERO;

import com.rdelgatte.hexagonal.domain.Product;
import com.rdelgatte.hexagonal.spi.ProductRepository;
import io.vavr.collection.List;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  public Product createProduct(Product product) {
    if (product.getCode().isEmpty()) {
      throw new IllegalArgumentException("There is no code for the product");
    }
    if (product.getPrice().compareTo(ZERO) <= 0) {
      throw new IllegalArgumentException("Product should be priced");
    }
    Option<Product> productById = productRepository.findProductByCode(product.getCode());
    if (productById.isDefined()) {
      throw new IllegalArgumentException(
          "Product " + product.getCode() + " already exists so you can't create it");
    }
    return productRepository.addProduct(product);
  }

  public void deleteProduct(@NonNull String code) {
    Option<Product> productByCode = findProductByCode(code);
    if (productByCode.isEmpty()) {
      throw new IllegalArgumentException("Product " + code + " does not exist");
    }
    productRepository.deleteProduct(productByCode.get().getId());
  }

  public List<Product> getAllProducts() {
    return productRepository.findAllProducts();
  }

  public Option<Product> findProductByCode(String code) {
    return productRepository.findProductByCode(code);
  }
}
