package ru.gb.zaripov.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.zaripov.core.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
