package ru.gb.zaripov.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.zaripov.api.ProductDto;
import ru.gb.zaripov.core.converters.ProductConverter;
import ru.gb.zaripov.core.exceptions.ResourceNotFoundException;
import ru.gb.zaripov.core.repositories.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(productConverter::toDto)
                .collect(Collectors.toList());
    }

    public ProductDto findById(Long id) {
        return productConverter.toDto(productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Can't find product #" + id)));
    }

    public void createNewProduct(ProductDto productDto) {
        productRepository.save(productConverter.toProduct(productDto));
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
