package com.rdelgatte.hexagonal.domain;

import static io.vavr.API.List;
import static java.util.UUID.randomUUID;

import io.vavr.collection.List;
import io.vavr.control.Option;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Wither
public class Customer {

  private UUID id = randomUUID();
  private String name = "";
  private List<Product> cart = List();

  public BigDecimal getCartTotal() {
    return cart.map(Product::getPrice)
        .foldLeft(BigDecimal.ZERO, BigDecimal::add)
        .setScale(2, RoundingMode.CEILING);
  }

  public String toString() {
    return Option.when(cart.isEmpty(), () -> name)
        .getOrElse(
            name + " has products in cart (" + getCartTotal() + "€)= " + cart.map(Product::toString).map(s -> s + "\n")
                .fold("\n", String::concat));
  }
}
