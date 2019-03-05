package com.rdelgatte.hexagonal.infrastructure.memory;

import static io.vavr.API.List;
import static io.vavr.API.None;
import static io.vavr.API.Option;
import static java.math.BigDecimal.ZERO;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

import com.rdelgatte.hexagonal.domain.Customer;
import com.rdelgatte.hexagonal.domain.Product;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryCustomerRepositoryTest {

  private static final UUID ANY_CUSTOMER_ID = randomUUID();
  private static final String ANY_NAME = "ANY_NAME";
  private static final Customer ANY_CUSTOMER = new Customer(ANY_CUSTOMER_ID, ANY_NAME, List());
  private static final UUID ANY_OTHER_CUSTOMER_ID = randomUUID();
  private static final String ANY_OTHER_LOGIN = "ANY_OTHER_LOGIN";
  private static final Customer ANY_OTHER_CUSTOMER = new Customer(ANY_OTHER_CUSTOMER_ID, ANY_OTHER_LOGIN, List());
  private static final UUID ANY_PRODUCT_ID = randomUUID();
  private static final String ANY_PRODUCT_CODE = "ANY_PRODUCT_CODE";
  private static final String ANY_LABEL = "ANY_LABEL";
  private static final Product ANY_PRODUCT = new Product(ANY_PRODUCT_ID, ANY_PRODUCT_CODE, ANY_LABEL, ZERO);
  private InMemoryCustomerRepository cut;

  @BeforeEach
  void setUp() {
    cut = new InMemoryCustomerRepository(List());
  }

  /**
   * {@link InMemoryCustomerRepository#save(Customer)}
   */
  @Test
  void unknownCustomer_save_addCustomerToRepository() {
    assertThat(cut.save(ANY_CUSTOMER)).isEqualTo(ANY_CUSTOMER);
    assertThat(cut.getCustomers()).isEqualTo(List(ANY_CUSTOMER));
  }

  @Test
  void existingCustomer_save_updateCustomerToRepository() {
    cut = new InMemoryCustomerRepository().withCustomers(List(ANY_CUSTOMER));
    Customer updatedCustomer = ANY_CUSTOMER.withCart(List(ANY_PRODUCT));
    assertThat(cut.save(updatedCustomer)).isEqualTo(updatedCustomer);
    assertThat(cut.getCustomers()).isEqualTo(List(updatedCustomer));
  }

  /**
   * {@link InMemoryCustomerRepository#findById(UUID)}
   */
  @Test
  void unknownCustomer_findById_returnsNone() {
    assertThat(cut.findById(randomUUID())).isEqualTo(None());
  }

  @Test
  void existingCustomer_findById_returnsCustomer() {
    cut = new InMemoryCustomerRepository().withCustomers(List(ANY_CUSTOMER));
    assertThat(cut.findById(ANY_CUSTOMER_ID)).isEqualTo(Option(ANY_CUSTOMER));
  }

  /**
   * {@link InMemoryCustomerRepository#findByLogin(String)}
   */
  @Test
  void unknownCustomer_findByLogin_returnsNone() {
    assertThat(cut.findByLogin(ANY_NAME)).isEqualTo(None());
  }

  @Test
  void existingCustomer_findByLogin_returnsCustomer() {
    cut = new InMemoryCustomerRepository().withCustomers(List(ANY_CUSTOMER));
    assertThat(cut.findByLogin(ANY_NAME)).isEqualTo(Option(ANY_CUSTOMER));
  }

  /**
   * {@link InMemoryCustomerRepository#findAll()}
   */
  @Test
  void emptyRepository_findAll_returnsEmptyList() {
    assertThat(cut.findAll()).isEqualTo(List());
  }

  @Test
  void customers_findAll_returnsAllCustomers() {
    cut = new InMemoryCustomerRepository().withCustomers(List(ANY_CUSTOMER, ANY_OTHER_CUSTOMER));
    assertThat(cut.findAll()).isEqualTo(List(ANY_CUSTOMER, ANY_OTHER_CUSTOMER));
  }
}