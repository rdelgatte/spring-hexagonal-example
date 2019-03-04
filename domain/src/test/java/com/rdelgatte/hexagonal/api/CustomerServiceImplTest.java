package com.rdelgatte.hexagonal.api;

import static io.vavr.API.List;
import static io.vavr.API.None;
import static io.vavr.API.Option;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import com.rdelgatte.hexagonal.domain.Customer;
import com.rdelgatte.hexagonal.domain.Product;
import com.rdelgatte.hexagonal.spi.CustomerRepository;
import com.rdelgatte.hexagonal.spi.ProductRepository;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

  private static final UUID ANY_PRODUCT_ID = randomUUID();
  private static final UUID ANY_OTHER_PRODUCT_ID = randomUUID();
  private static final String ANY_PRODUCT_CODE = "ANY_PRODUCT_CODE";
  private static final String ANY_OTHER_PRODUCT_CODE = "ANY_OTHER_PRODUCT_CODE";
  private static final String ANY_LABEL = "ANY_LABEL";
  private static final String ANY_OTHER_LABEL = "ANY_OTHER_LABEL";
  private static final Product ANY_PRODUCT = new Product(ANY_PRODUCT_ID, ANY_PRODUCT_CODE, ANY_LABEL, 0.0);
  private static final Product ANY_OTHER_PRODUCT = new Product(ANY_OTHER_PRODUCT_ID, ANY_OTHER_PRODUCT_CODE,
      ANY_OTHER_LABEL, 5.0);
  private static final UUID ANY_CUSTOMER_ID = randomUUID();
  private static final String ANY_LOGIN = "ANY_LOGIN";
  private static final Customer ANY_CUSTOMER = new Customer(ANY_CUSTOMER_ID, ANY_LOGIN, List());

  private CustomerServiceImpl cut;
  @Mock
  private CustomerRepository customerRepositoryMock;
  @Mock
  private ProductRepository productRepositoryMock;
  @Captor
  private ArgumentCaptor<Customer> customerCaptor;

  @BeforeEach
  void setUp() {
    cut = new CustomerServiceImpl(customerRepositoryMock, productRepositoryMock);
  }

  /**
   * {@link CustomerServiceImpl#signIn(String)}
   */
  @Test
  void customerAlreadyExists_throwsException() {
    when(customerRepositoryMock.findByLogin(ANY_LOGIN)).thenReturn(Option(ANY_CUSTOMER));

    IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> cut.signIn(ANY_LOGIN));
    assertThat(illegalArgumentException.getMessage()).isEqualTo("Customer already exists so you can't sign in");
    verifyNoMoreInteractions(customerRepositoryMock);
    verifyZeroInteractions(productRepositoryMock);
  }

  @Test
  void blankLogin_throwsException() {
    IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> cut.signIn(""));

    assertThat(illegalArgumentException.getMessage()).isEqualTo("Customer login should not be blank");
    verifyNoMoreInteractions(customerRepositoryMock);
    verifyZeroInteractions(productRepositoryMock);
  }

  @Test
  void validUnknownCustomer_signIn() {
    Customer expected = new Customer().withLogin(ANY_LOGIN);
    when(customerRepositoryMock.findByLogin(ANY_LOGIN)).thenReturn(None());
    when(customerRepositoryMock.save(any())).thenReturn(expected);

    Customer customer = cut.signIn(ANY_LOGIN);

    verify(customerRepositoryMock).save(customerCaptor.capture());
    assertThat(customerCaptor.getValue().getLogin()).isEqualTo(ANY_LOGIN);
    assertThat(customerCaptor.getValue().getProducts()).isEqualTo(List());
    assertThat(customer.getLogin()).isEqualTo(ANY_LOGIN);
    verifyZeroInteractions(productRepositoryMock);
  }

  /**
   * {@link CustomerServiceImpl#addProductToCart(String, String)}
   */

  @Test
  void unknownCustomer_addProductToCart_throwsIllegalArgumentException() {
    when(customerRepositoryMock.findByLogin(ANY_LOGIN)).thenReturn(None());

    IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> cut.addProductToCart(ANY_LOGIN, ANY_PRODUCT_CODE));
    assertThat(illegalArgumentException.getMessage()).isEqualTo("The customer does not exist");
    verifyNoMoreInteractions(customerRepositoryMock);
    verifyZeroInteractions(productRepositoryMock);
  }

  @Test
  void unknownProduct_addProductToCart_throwsIllegalArgumentException() {
    when(customerRepositoryMock.findByLogin(ANY_LOGIN)).thenReturn(Option(ANY_CUSTOMER));
    when(productRepositoryMock.findProductByCode(ANY_PRODUCT_CODE)).thenReturn(None());

    IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> cut.addProductToCart(ANY_LOGIN, ANY_PRODUCT_CODE));
    assertThat(illegalArgumentException.getMessage()).isEqualTo("The product does not exist");
    verifyNoMoreInteractions(customerRepositoryMock);
    verifyNoMoreInteractions(productRepositoryMock);
  }

  @Test
  void existingProductAndCustomer_addProductToCart_returnsUpdatedCustomer() {
    Customer expected = ANY_CUSTOMER.withProducts(List(ANY_PRODUCT));
    when(customerRepositoryMock.findByLogin(ANY_LOGIN)).thenReturn(Option(ANY_CUSTOMER));
    when(productRepositoryMock.findProductByCode(ANY_PRODUCT_CODE)).thenReturn(Option(ANY_PRODUCT));
    when(customerRepositoryMock.save(any())).thenReturn(expected);

    Customer customer = cut.addProductToCart(ANY_LOGIN, ANY_PRODUCT_CODE);
    verify(customerRepositoryMock).save(customerCaptor.capture());
    Customer savedCustomer = customerCaptor.getValue();
    assertThat(savedCustomer.getProducts()).containsExactly(ANY_PRODUCT);
    assertThat(customer.getProducts()).containsExactly(ANY_PRODUCT);
  }

  @Test
  void existingProductAndCustomerWithCart_addProductToCart_returnsUpdatedCustomer() {
    Customer existing = ANY_CUSTOMER.withProducts(List(ANY_PRODUCT));
    Customer expected = existing.withProducts(List(ANY_PRODUCT, ANY_OTHER_PRODUCT));
    when(customerRepositoryMock.findByLogin(ANY_LOGIN)).thenReturn(Option(existing));
    when(productRepositoryMock.findProductByCode(ANY_OTHER_PRODUCT_CODE)).thenReturn(Option(ANY_OTHER_PRODUCT));
    when(customerRepositoryMock.save(any())).thenReturn(expected);

    Customer customer = cut.addProductToCart(ANY_LOGIN, ANY_OTHER_PRODUCT_CODE);
    verify(customerRepositoryMock).save(customerCaptor.capture());
    Customer savedCustomer = customerCaptor.getValue();
    assertThat(savedCustomer.getProducts()).containsExactly(ANY_PRODUCT, ANY_OTHER_PRODUCT);
    assertThat(customer.getProducts()).containsExactly(ANY_PRODUCT, ANY_OTHER_PRODUCT);
  }

  /**
   * {@link CustomerServiceImpl#emptyCart(String)}
   */

  @Test
  void unknownCustomer_emptyCart_throwsIllegalArgumentException() {
    when(customerRepositoryMock.findByLogin(ANY_LOGIN)).thenReturn(None());

    IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> cut.emptyCart(ANY_LOGIN));
    assertThat(illegalArgumentException.getMessage()).isEqualTo("The customer does not exist");
    verifyNoMoreInteractions(customerRepositoryMock);
  }

  @Test
  void existingCustomer_emptyCart_returnsCustomerWithEmptiedCart() {
    Customer expected = ANY_CUSTOMER.withProducts(List());
    when(customerRepositoryMock.findByLogin(ANY_LOGIN)).thenReturn(Option(ANY_CUSTOMER));
    when(customerRepositoryMock.save(any())).thenReturn(expected);

    Customer customer = cut.emptyCart(ANY_LOGIN);
    assertThat(customer).isEqualTo(expected);
    verify(customerRepositoryMock).save(expected);
  }

  /**
   * {@link CustomerServiceImpl#findCustomer(String)}
   */

  @Test
  void unknownCustomer_findCustomer_returnsNone() {
    when(customerRepositoryMock.findByLogin(ANY_LOGIN)).thenReturn(None());

    assertTrue(cut.findCustomer(ANY_LOGIN).isEmpty());
  }

  @Test
  void existingCustomer_findCustomer_returnsCustomer() {
    when(customerRepositoryMock.findByLogin(ANY_LOGIN)).thenReturn(Option(ANY_CUSTOMER));

    assertThat(cut.findCustomer(ANY_LOGIN)).isEqualTo(Option(ANY_CUSTOMER));
  }
}
