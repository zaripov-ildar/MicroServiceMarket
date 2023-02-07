package ru.gb.zaripov.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gb.zaripov.api.ProductDto;
import ru.gb.zaripov.core.entities.Product;
import ru.gb.zaripov.core.exceptions.ResourceNotFoundException;
import ru.gb.zaripov.core.services.CategoryService;

@Component
@RequiredArgsConstructor
public class ProductConverter {
    private final CategoryService categoryService;

    public ProductDto toDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getTitle(),
                product.getPrice(),
                product.getCategory().getTitle());
    }

    public Product toProduct(ProductDto productDto) {
        return new Product(
                productDto.getTitle(),
                productDto.getPrice(),
                categoryService.findByTitle(productDto.getCategoryTitle())
                        .orElseThrow(
                                () -> new ResourceNotFoundException("Can't find category #" + productDto.getCategoryTitle())
                        )
        );
    }
}
