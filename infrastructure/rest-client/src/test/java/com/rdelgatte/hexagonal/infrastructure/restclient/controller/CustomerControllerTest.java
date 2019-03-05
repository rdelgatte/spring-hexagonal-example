package com.rdelgatte.hexagonal.infrastructure.restclient.controller;

import static io.vavr.API.List;
import static io.vavr.API.Option;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rdelgatte.hexagonal.api.CustomerService;
import com.rdelgatte.hexagonal.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

  private static final String ANY_NAME = "ANY_NAME";
  private static final String ANY_PRODUCT_CODE = "ANY_PRODUCT_CODE";
  private static final Customer ANY_CUSTOMER = new Customer(randomUUID(), ANY_NAME, List());
  private CustomerController cut;
  @Mock
  private CustomerService customerServiceMock;

  @BeforeEach
  void setUp() {
    cut = new CustomerController(customerServiceMock);
  }

  /**
   * {@link CustomerController#signUp(String)}
   */
  @Test
  void signup() {
    cut.signUp(ANY_NAME);

    verify(customerServiceMock).signUp(ANY_NAME);
  }

  /**
   * {@link CustomerController#addProductToCart(String, String)}
   */
  @Test
  void addProductToCart() {
    cut.addProductToCart(ANY_NAME, ANY_PRODUCT_CODE);

    verify(customerServiceMock).addProductToCart(ANY_NAME, ANY_PRODUCT_CODE);
  }

  /**
   * {@link CustomerController#find(String)}
   */
  @Test
  void find() {
    when(customerServiceMock.findCustomer(ANY_NAME)).thenReturn(Option(ANY_CUSTOMER));

    assertThat(cut.find(ANY_NAME)).isEqualTo(Option(ANY_CUSTOMER));
    verify(customerServiceMock).findCustomer(ANY_NAME);
  }
}