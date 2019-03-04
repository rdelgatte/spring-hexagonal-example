package com.rdelgatte.hexagonal.infrastructure.postgres.repository;

import com.rdelgatte.hexagonal.infrastructure.postgres.dao.PostgresProduct;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostgresSpringDataProductRepository extends JpaRepository<PostgresProduct, UUID> {

  Optional<PostgresProduct> findByCode(String code);
}
