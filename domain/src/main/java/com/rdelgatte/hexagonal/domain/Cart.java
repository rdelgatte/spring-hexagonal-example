package com.rdelgatte.hexagonal.domain;

import static io.vavr.API.List;

import io.vavr.collection.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Wither
public class Cart {

  private UUID id = UUID.randomUUID();
  private List<Product> products = List();

  double getTotalAmount() {
    return products.map(Product::getPrice).foldLeft(0.0, Double::sum);
  }
}
