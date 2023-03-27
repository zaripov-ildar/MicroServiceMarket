package ru.gb.zaripov.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.gb.zaripov.api.ProductDto;
import ru.gb.zaripov.core.Event.DrugEvent;
import ru.gb.zaripov.core.converters.PageConverter;
import ru.gb.zaripov.core.converters.ProductConverter;
import ru.gb.zaripov.core.dtos.PageDto;
import ru.gb.zaripov.core.services.ProductService;

import java.math.BigDecimal;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ApplicationEventPublisher publisher;
    private final ProductService productService;
    private final ProductConverter productConverter;
    private final PageConverter pageConverter;

    @GetMapping
    public PageDto getAllProducts(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "1") Integer pageSize,
            @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(name = "titlePart", required = false) String titlePart
    ) {
        page = Math.max(1, page);
        pageSize = Math.max(1, pageSize);
        return pageConverter.toPageDto(
                productService.find(page, pageSize, minPrice, maxPrice, titlePart)
                        .map(productConverter::toDto)
        );


    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        ProductDto pDto = productConverter.toDto(productService.findById(id));
        String category = pDto.getCategoryTitle();
        if (category.equals("Drug")){
            publisher.publishEvent(new DrugEvent(this));
        }
        return pDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewProduct(@RequestBody ProductDto productDto) {
        productService.createNewProduct(productDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }
}
