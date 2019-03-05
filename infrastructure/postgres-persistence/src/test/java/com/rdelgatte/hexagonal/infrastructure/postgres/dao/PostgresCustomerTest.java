package com.rdelgatte.hexagonal.infrastructure.postgres.dao;

import static io.vavr.API.List;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

import com.rdelgatte.hexagonal.domain.Customer;
import com.rdelgatte.hexagonal.domain.Product;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PostgresCustomerTest {


  private static final UUID ANY_PRODUCT_ID = randomUUID();
  private static final String ANY_PRODUCT_CODE = "ANY_PRODUCT_CODE";
  private static final String ANY_LABEL = "ANY_LABEL";
  private static final Product ANY_PRODUCT = new Product(ANY_PRODUCT_ID, ANY_PRODUCT_CODE, ANY_LABEL, 0.0);
  private static final PostgresProduct ANY_POSTGRES_PRODUCT = new PostgresProduct(ANY_PRODUCT_ID, ANY_PRODUCT_CODE,
      ANY_LABEL, 0.0);
  private static final UUID ANY_CUSTOMER_ID = randomUUID();
  private static final String ANY_LOGIN = "ANY_LOGIN";
  private static final Customer ANY_CUSTOMER = new Customer(ANY_CUSTOMER_ID, ANY_LOGIN, List(ANY_PRODUCT));
  private static final PostgresCustomer ANY_POSTGRES_CUSTOMER = new PostgresCustomer(ANY_CUSTOMER_ID, ANY_LOGIN,
      List.of(ANY_POSTGRES_PRODUCT));

  @Test
  void postgresCustomer_toCustomer_returnsCustomer() {
    assertThat(ANY_POSTGRES_CUSTOMER.toCustomer()).isEqualTo(ANY_CUSTOMER);
  }

  @Test
  void product_fromCustomer_returnsPostgresCustomer() {
    assertThat(PostgresCustomer.fromCustomer(ANY_CUSTOMER)).isEqualTo(ANY_POSTGRES_CUSTOMER);
  }
}