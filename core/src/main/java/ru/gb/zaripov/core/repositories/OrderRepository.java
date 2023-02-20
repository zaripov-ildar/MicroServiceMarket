package ru.gb.zaripov.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.zaripov.core.entities.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUsername(String username);
}
