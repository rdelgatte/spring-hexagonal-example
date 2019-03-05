package com.rdelgatte.hexagonal.api;


import static io.vavr.API.List;
import static io.vavr.API.None;
import static io.vavr.API.Option;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import com.rdelgatte.hexagonal.domain.Product;
import com.rdelgatte.hexagonal.spi.ProductRepository;
import java.math.BigDecimal;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

  private static final String ANY_PRODUCT_CODE = "ANY_PRODUCT_CODE";
  private static final String ANY_PRODUCT_LABEL = "ANY_PRODUCT_LABEL";
  private static final Product ANY_PRODUCT = new Product()
      .withCode(ANY_PRODUCT_CODE)
      .withLabel(ANY_PRODUCT_LABEL)
      .withPrice(BigDecimal.TEN);
  private ProductServiceImpl cut;
  @Mock
  private ProductRepository productRepositoryMock;

  @BeforeEach
  void setUp() {
    cut = new ProductServiceImpl(productRepositoryMock);
  }

  /**
   * {@link ProductServiceImpl#createProduct(Product)}
   */
  @Test
  void productAlreadyExists_throwsException() {
    when(productRepositoryMock.findProductByCode(ANY_PRODUCT_CODE)).thenReturn(Option(ANY_PRODUCT));

    IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> cut.createProduct(ANY_PRODUCT));
    assertThat(illegalArgumentException.getMessage())
        .isEqualTo("Product " + ANY_PRODUCT.getCode() + " already exists so you can't create it");
    verifyNoMoreInteractions(productRepositoryMock);
  }

  @Test
  void productWithoutCode_throwsException() {
    IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> cut.createProduct(new Product()));

    assertThat(illegalArgumentException.getMessage()).isEqualTo("There is no code for the product");
    verifyZeroInteractions(productRepositoryMock);
  }

  @Test
  void zeroPricedProduct_createProduct_throwsException() {
    IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> cut.createProduct(ANY_PRODUCT.withPrice(BigDecimal.ZERO)));

    assertThat(illegalArgumentException.getMessage()).isEqualTo("Product should be priced");
    verifyZeroInteractions(productRepositoryMock);
  }

  @Test
  void unknownValidProduct_createProduct() {
    when(productRepositoryMock.findProductByCode(ANY_PRODUCT_CODE)).thenReturn(None());
    when(productRepositoryMock.addProduct(ANY_PRODUCT)).thenReturn(ANY_PRODUCT);

    Assertions.assertThat(cut.createProduct(ANY_PRODUCT)).isEqualTo(ANY_PRODUCT);
    verify(productRepositoryMock).addProduct(ANY_PRODUCT);
  }

  /**
   * {@link ProductServiceImpl#findProductByCode(String)}
   */
  @Test
  void unknownProduct_returnsNone() {
    when(productRepositoryMock.findProductByCode(ANY_PRODUCT_CODE)).thenReturn(None());

    Assertions.assertThat(cut.findProductByCode(ANY_PRODUCT_CODE)).isEqualTo(None());
    verifyNoMoreInteractions(productRepositoryMock);
  }

  @Test
  void existingProduct_returnsProduct() {
    when(productRepositoryMock.findProductByCode(ANY_PRODUCT_CODE)).thenReturn(Option(ANY_PRODUCT));

    Assertions.assertThat(cut.findProductByCode(ANY_PRODUCT_CODE)).isEqualTo(Option(ANY_PRODUCT));
  }

  /**
   * {@link ProductServiceImpl#deleteProduct(String)}
   */
  @Test
  void deleteUnknownProduct_throwsException() {
    when(productRepositoryMock.findProductByCode(ANY_PRODUCT_CODE)).thenReturn(None());

    IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> cut.deleteProduct(ANY_PRODUCT_CODE));
    assertThat(illegalArgumentException.getMessage()).isEqualTo("Product " + ANY_PRODUCT_CODE + " does not exist");
    verifyNoMoreInteractions(productRepositoryMock);
  }

  @Test
  void deleteNoCode_throwsException() {
    assertThrows(NullPointerException.class, () -> cut.deleteProduct(null));
    verifyZeroInteractions(productRepositoryMock);
  }

  @Test
  void deleteExistingProduct_deleteProduct() {
    when(productRepositoryMock.findProductByCode(ANY_PRODUCT_CODE)).thenReturn(Option(ANY_PRODUCT));

    cut.deleteProduct(ANY_PRODUCT_CODE);

    verify(productRepositoryMock).deleteProduct(ANY_PRODUCT.getId());
  }

  /**
   * {@link ProductServiceImpl#getAllProducts()}
   */
  @Test
  void getAllProducts_returnProducts() {
    when(productRepositoryMock.findAllProducts()).thenReturn(List(ANY_PRODUCT));

    Assertions.assertThat(cut.getAllProducts()).containsExactly(ANY_PRODUCT);
  }
}