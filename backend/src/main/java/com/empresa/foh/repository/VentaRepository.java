package com.empresa.foh.repository;

import com.empresa.foh.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    // Buscar ventas entre dos fechas
    List<Venta> findByFechaBetween(LocalDateTime desde, LocalDateTime hasta);
}
