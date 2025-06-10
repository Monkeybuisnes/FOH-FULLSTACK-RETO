package com.empresa.foh.dto;

import lombok.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaItemDTO {

    // Id del producto que se vende
    @NotNull(message = "El ID de producto es requerido")
    private Long productoId;

    @NotNull(message = "La cantidad es requerida")
    @Positive(message = "La cantidad debe ser mayor que cero")
    private Integer cantidad;

    // Precio unitario en el momento de la venta.
    @NotNull(message = "El precio unitario es requerido")
    @Positive(message = "El precio unitario debe ser mayor que cero")
    private BigDecimal precioUnitario;
}
