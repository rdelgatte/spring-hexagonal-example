package com.rdelgatte.hexagonal.infrastructure.postgres.dao;

import static io.vavr.collection.List.ofAll;
import static java.util.UUID.randomUUID;

import com.rdelgatte.hexagonal.domain.Customer;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Wither
public class PostgresCustomer {

  @Id
  private UUID id = randomUUID();
  private String name = "";
  @ManyToMany
  private List<PostgresProduct> cart = List.of();

  public static PostgresCustomer fromCustomer(Customer customer) {
    return new PostgresCustomer()
        .withId(customer.getId())
        .withName(customer.getName())
        .withCart(customer.getCart()
            .map(PostgresProduct::fromProduct)
            .toJavaList()
        );
  }

  public Customer toCustomer() {
    return new Customer()
        .withId(id)
        .withName(name)
        .withCart(ofAll(cart).map(PostgresProduct::toProduct));
  }
}
