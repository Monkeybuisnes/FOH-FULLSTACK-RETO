package com.empresa.foh.service;

import com.empresa.foh.dto.ProductoDTO;
import com.empresa.foh.entity.Producto;
import com.empresa.foh.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductoService {

    private final ProductoRepository productoRepository;

    public List<ProductoDTO> getAll() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ProductoDTO getById(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
        return toDTO(producto);
    }

    public ProductoDTO create(ProductoDTO dto) {
        Producto entidad = toEntity(dto);
        Producto guardado = productoRepository.save(entidad);
        return toDTO(guardado);
    }

    public ProductoDTO update(Long id, ProductoDTO dto) {
        Producto existente = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
        existente.setNombre(dto.getNombre());
        existente.setPrecio(dto.getPrecio());
        existente.setStock(dto.getStock());
        Producto actualizado = productoRepository.save(existente);
        return toDTO(actualizado);
    }

    public void delete(Long id) {
        Producto existente = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
        productoRepository.delete(existente);
    }

    // Mapeo entidad -> DTO
    private ProductoDTO toDTO(Producto entidad) {
        return ProductoDTO.builder()
                .id(entidad.getId())
                .nombre(entidad.getNombre())
                .precio(entidad.getPrecio())
                .stock(entidad.getStock())
                .build();
    }

    // Mapeo DTO -> entidad
    private Producto toEntity(ProductoDTO dto) {
        return Producto.builder()
                .nombre(dto.getNombre())
                .precio(dto.getPrecio())
                .stock(dto.getStock())
                .build();
    }
}
