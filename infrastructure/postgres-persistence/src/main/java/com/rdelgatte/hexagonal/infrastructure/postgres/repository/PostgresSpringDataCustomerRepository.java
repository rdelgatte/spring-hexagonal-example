package com.rdelgatte.hexagonal.infrastructure.postgres.repository;

import com.rdelgatte.hexagonal.infrastructure.postgres.dao.PostgresCustomer;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostgresSpringDataCustomerRepository extends JpaRepository<PostgresCustomer, UUID> {

  Optional<PostgresCustomer> findByName(String name);
}
