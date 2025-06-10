package com.empresa.foh.service;

import com.empresa.foh.dto.VentaDTO;
import com.empresa.foh.dto.VentaItemDTO;
import com.empresa.foh.entity.Cliente;
import com.empresa.foh.entity.Producto;
import com.empresa.foh.entity.Venta;
import com.empresa.foh.entity.VentaItem;
import com.empresa.foh.repository.ClienteRepository;
import com.empresa.foh.repository.ProductoRepository;
import com.empresa.foh.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;

    public VentaDTO create(VentaDTO dto) {
        // Verificar existencia de cliente
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + dto.getClienteId()));

        // Crear entidad Venta
        Venta venta = new Venta();
        // Fecha: si el DTO trae fecha nula, asignar ahora; de otro modo, tomarla.
        LocalDateTime fecha = dto.getFecha() != null ? dto.getFecha() : LocalDateTime.now();
        venta.setFecha(fecha);
        venta.setCliente(cliente);

        // Mapear items
        List<VentaItem> items = dto.getItems().stream()
                .map(itemDto -> {
                    // Verificar existencia de producto
                    Producto producto = productoRepository.findById(itemDto.getProductoId())
                            .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + itemDto.getProductoId()));

                    // Opcional: verificar stock suficiente:
                    if (producto.getStock() < itemDto.getCantidad()) {
                        throw new IllegalArgumentException("Stock insuficiente para producto ID: " + producto.getId());
                    }
                    // Crear VentaItem
                    VentaItem item = new VentaItem();
                    item.setVenta(venta);
                    item.setProducto(producto);
                    item.setCantidad(itemDto.getCantidad());
                    // Tomar precioUnitario desde DTO o desde entidad producto:
                    item.setPrecioUnitario(itemDto.getPrecioUnitario());
                    // Opcional: descontar stock
                    producto.setStock(producto.getStock() - itemDto.getCantidad());
                    // productoRepository.save(producto); // se guardará al final en la transacción si es detach/detached
                    return item;
                })
                .collect(Collectors.toList());

        venta.setItems(items);

        // Guardar venta y cascada de items
        Venta ventaGuardada = ventaRepository.save(venta);

        // Los cambios de stock en producto se persistirán gracias a la transacción y entidades gestionadas
        return toDTO(ventaGuardada);
    }

    public VentaDTO getById(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con ID: " + id));
        return toDTO(venta);
    }

    public List<VentaDTO> getByFechaRange(LocalDateTime desde, LocalDateTime hasta) {
        List<Venta> ventas = ventaRepository.findByFechaBetween(desde, hasta);
        return ventas.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Mapeo entidad -> DTO
    private VentaDTO toDTO(Venta entidad) {
        List<VentaItemDTO> itemsDto = entidad.getItems().stream()
                .map(item -> VentaItemDTO.builder()
                        .productoId(item.getProducto().getId())
                        .cantidad(item.getCantidad())
                        .precioUnitario(item.getPrecioUnitario())
                        .build())
                .collect(Collectors.toList());

        return VentaDTO.builder()
                .id(entidad.getId())
                .clienteId(entidad.getCliente().getId())
                .fecha(entidad.getFecha())
                .items(itemsDto)
                .build();
    }
}
