package com.rdelgatte.hexagonal.infrastructure.postgres.dao;

import static java.math.BigDecimal.ZERO;
import static java.util.UUID.randomUUID;

import com.rdelgatte.hexagonal.domain.Product;
import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Wither
public class PostgresProduct {

  @Id
  private UUID id = randomUUID();
  private String code = "";
  private String label = "";
  private BigDecimal price = ZERO;

  public static PostgresProduct fromProduct(Product product) {
    return new PostgresProduct()
        .withId(product.getId())
        .withCode(product.getCode())
        .withLabel(product.getLabel())
        .withPrice(product.getPrice());
  }

  public Product toProduct() {
    return new Product(id, code, label, price);
  }
}
