package com.empresa.foh.dto;

import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {

    private Long id;

    @NotBlank(message = "El nombre del producto no puede estar vacío")
    private String nombre;

    @NotNull(message = "El precio es requerido")
    @PositiveOrZero(message = "El precio debe ser cero o positivo")
    private BigDecimal precio;

    @NotNull(message = "El stock es requerido")
    @PositiveOrZero(message = "El stock debe ser cero o positivo")
    private Integer stock;
}

