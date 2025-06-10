package com.empresa.foh.repository;

import com.empresa.foh.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Se pueden agregar métodos de búsqueda adicionales si se requiere.
}
