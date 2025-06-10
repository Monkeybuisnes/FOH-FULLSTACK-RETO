package com.empresa.foh.entity;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, scale = 2, precision = 19)
    private BigDecimal precio;

    @Column(nullable = false)
    private Integer stock;

    // Opcionalmente, relación inversa con VentaItem, si se desea navegar
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<VentaItem> ventaItems;
}
