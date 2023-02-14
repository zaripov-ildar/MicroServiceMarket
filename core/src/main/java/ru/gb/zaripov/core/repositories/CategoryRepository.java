package ru.gb.zaripov.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.zaripov.core.entities.Category;
import ru.gb.zaripov.core.services.CategoryService;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByTitle(String categoryTitle);
}
