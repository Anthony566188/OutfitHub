package br.com.fiap.OutfitHub.dto;

import br.com.fiap.OutfitHub.models.Shirt;
import br.com.fiap.OutfitHub.models.enums.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ShirtRequest(
        @NotBlank(message = "O nome é obrigatório")
        String name,
        Size size,
        @Min(value = 0, message = "O preço não pode ser negativo")
        BigDecimal price
) {

    public Shirt toEntity() {
        return Shirt.builder()
                .name(name)
                .size(size)
                .price(price)
                .build();
    }

}
