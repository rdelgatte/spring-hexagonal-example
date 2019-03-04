package com.rdelgatte.hexagonal.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.vavr.API;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

class CartTest {

  private static final String ANY_CODE = "ANY_CODE";
  private static final String ANY_OTHER_CODE = "ANY_OTHER_CODE";
  private static final Product ANY_PRODUCT = new Product().withCode(ANY_CODE).withPrice(10.0);
  private static final Product ANY_OTHER_PRODUCT = new Product().withCode(ANY_OTHER_CODE).withPrice(5.95);

  @Test
  void emptyCart_getTotal_returnsZero() {
    assertThat(new Cart().getTotalAmount()).isEqualTo(0.0);
  }

  @Test
  void cartWithProducts_getTotal_returnsTotalAmount() {
    List<Product> products = API.List(ANY_PRODUCT, ANY_OTHER_PRODUCT);
    assertThat(new Cart().withProducts(products).getTotalAmount()).isEqualTo(15.95);
  }
}