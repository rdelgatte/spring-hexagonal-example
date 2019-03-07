package com.rdelgatte.hexagonal.infrastructure.memory;

import com.rdelgatte.hexagonal.domain.Product;
import com.rdelgatte.hexagonal.spi.ProductRepository;
import io.vavr.API;
import io.vavr.collection.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Wither
public class InMemoryProductRepository implements ProductRepository {

  private List<Product> inMemoryProducts = API.List();

}
