package com.rdelgatte.hexagonal.domain;

import static io.vavr.API.None;
import static java.util.UUID.randomUUID;

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
public class Customer {

  private UUID id = randomUUID();
  private String login = "";
  private Option<Cart> cart = None();
}
