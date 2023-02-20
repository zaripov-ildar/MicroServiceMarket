package ru.gb.zaripov.core.converters;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.gb.zaripov.api.ProductDto;
import ru.gb.zaripov.core.dtos.PageDto;

@Component
public class PageConverter {

    public PageDto toPageDto(Page<ProductDto> page) {
        return new PageDto(
                page.toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages()
        );
    }
}
