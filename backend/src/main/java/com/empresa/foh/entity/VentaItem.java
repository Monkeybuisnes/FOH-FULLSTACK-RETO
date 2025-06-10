package com.empresa.foh.entity;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "venta_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación a Venta
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Venta venta;

    // Relación a Producto
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private Integer cantidad;

    // Precio unitario en el momento de la venta (puede tomarse del producto o recibirse en DTO)
    @Column(nullable = false, scale = 2, precision = 19)
    private BigDecimal precioUnitario;
}
