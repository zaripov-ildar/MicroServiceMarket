package ru.gb.zaripov.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.zaripov.core.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
