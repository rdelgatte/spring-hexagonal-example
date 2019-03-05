package com.rdelgatte.hexagonal.domain;

import static java.util.UUID.randomUUID;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Wither
public class Product {

  private UUID id = randomUUID();
  private String code = "";
  private String label = "";
  private BigDecimal price = BigDecimal.ZERO;
}
