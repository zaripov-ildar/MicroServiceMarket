package ru.gb.zaripov.core.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gb.zaripov.api.ProductDto;

import java.util.List;

@Data
@AllArgsConstructor
public class PageDto {
    private List<ProductDto> products;
    private int page;
    private int pageSize;
    private int totalPages;


}
