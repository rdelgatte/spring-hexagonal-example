package com.rdelgatte.hexagonal.domain;

import static io.vavr.API.List;
import static java.util.UUID.randomUUID;

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
public class Customer {

  private UUID id = randomUUID();
  private String login = "";
  private List<Product> products = List();
}
