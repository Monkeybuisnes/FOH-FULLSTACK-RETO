package com.empresa.foh.controller;

import com.empresa.foh.dto.VentaDTO;
import com.empresa.foh.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
@Validated
public class VentaController {

    private final VentaService ventaService;

    @PostMapping
    public ResponseEntity<VentaDTO> crear(@Valid @RequestBody VentaDTO dto) {
        VentaDTO creado = ventaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaDTO> obtenerPorId(@PathVariable Long id) {
        VentaDTO dto = ventaService.getById(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * Obtener ventas en un rango de fechas:
     * - Formato de parámetros: yyyy-MM-dd
     * - Ejemplo: /api/ventas?desde=2025-06-01&hasta=2025-06-30
     */
    @GetMapping
    public ResponseEntity<List<VentaDTO>> obtenerPorRangoFecha(
            @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta) {

        // Convertir LocalDate a LocalDateTime de inicio y fin de día
        LocalDateTime desde = fechaDesde.atStartOfDay();
        LocalDateTime hasta = fechaHasta.atTime(LocalTime.MAX);
        List<VentaDTO> lista = ventaService.getByFechaRange(desde, hasta);
        return ResponseEntity.ok(lista);
    }
}
