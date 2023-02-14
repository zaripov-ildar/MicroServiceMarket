package ru.gb.zaripov.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.zaripov.core.entities.Category;
import ru.gb.zaripov.core.repositories.CategoryRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Optional<Category> findByTitle(String categoryTitle) {
        return categoryRepository.findByTitle(categoryTitle);
    }
}
