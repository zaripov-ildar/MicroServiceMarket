package ru.gb.zaripov.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.zaripov.api.ProductDto;
import ru.gb.zaripov.api.exceptions.ResourceNotFoundException;
import ru.gb.zaripov.core.converters.ProductConverter;
import ru.gb.zaripov.core.entities.Product;
import ru.gb.zaripov.core.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void createNewProduct(ProductDto productDto) {
        productRepository.save(productConverter.toProduct(productDto));
    }

    public void deleteById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Can't find product #" + id);
        }
        productRepository.deleteById(id);
    }
}
