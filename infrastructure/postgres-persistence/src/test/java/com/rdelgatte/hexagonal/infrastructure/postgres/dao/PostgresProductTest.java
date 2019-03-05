package com.rdelgatte.hexagonal.infrastructure.postgres.dao;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

import com.rdelgatte.hexagonal.domain.Product;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PostgresProductTest {

  private static final UUID ANY_PRODUCT_ID = randomUUID();
  private static final String ANY_PRODUCT_CODE = "ANY_PRODUCT_CODE";
  private static final String ANY_LABEL = "ANY_LABEL";
  private static final Product ANY_PRODUCT = new Product(ANY_PRODUCT_ID, ANY_PRODUCT_CODE, ANY_LABEL, 0.0);

  private static final PostgresProduct ANY_POSTGRES_PRODUCT = new PostgresProduct(ANY_PRODUCT_ID, ANY_PRODUCT_CODE,
      ANY_LABEL, 0.0);

  @Test
  void postgresProduct_toProduct_returnsProduct() {
    assertThat(ANY_POSTGRES_PRODUCT.toProduct()).isEqualTo(ANY_PRODUCT);
  }

  @Test
  void product_fromProduct_returnsPostgresProduct() {
    assertThat(PostgresProduct.fromProduct(ANY_PRODUCT)).isEqualTo(ANY_POSTGRES_PRODUCT);
  }
}