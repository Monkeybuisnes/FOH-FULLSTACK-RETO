package com.empresa.foh.dto;

import lombok.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaDTO {

    private Long id;

    // Se referencía cliente por ID
    @NotNull(message = "El ID de cliente es requerido")
    private Long clienteId;

    // Fecha de la venta; en creación puede ser nula y asignarse en el servicio
    private LocalDateTime fecha;

    @NotNull(message = "Los items de la venta no pueden ser nulos")
    @Size(min = 1, message = "Debe haber al menos un item en la venta")
    @Valid
    private List<VentaItemDTO> items;
}
