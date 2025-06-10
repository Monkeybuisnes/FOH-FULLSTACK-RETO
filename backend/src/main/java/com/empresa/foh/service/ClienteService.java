package com.empresa.foh.service;

import com.empresa.foh.dto.ClienteDTO;
import com.empresa.foh.entity.Cliente;
import com.empresa.foh.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<ClienteDTO> getAll() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ClienteDTO getById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
        return toDTO(cliente);
    }

    public ClienteDTO create(ClienteDTO dto) {
        // Opcional: verificar unicidad de email
        clienteRepository.findByEmail(dto.getEmail()).ifPresent(c -> {
            throw new IllegalArgumentException("Ya existe un cliente con email: " + dto.getEmail());
        });

        Cliente entidad = toEntity(dto);
        Cliente guardado = clienteRepository.save(entidad);
        return toDTO(guardado);
    }

    public ClienteDTO update(Long id, ClienteDTO dto) {
        Cliente existente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
        // Verificar si se cambia email a uno existente
        if (!existente.getEmail().equals(dto.getEmail())) {
            clienteRepository.findByEmail(dto.getEmail()).ifPresent(c -> {
                throw new IllegalArgumentException("Ya existe un cliente con email: " + dto.getEmail());
            });
        }
        existente.setNombre(dto.getNombre());
        existente.setEmail(dto.getEmail());
        Cliente actualizado = clienteRepository.save(existente);
        return toDTO(actualizado);
    }

    public void delete(Long id) {
        Cliente existente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
        clienteRepository.delete(existente);
    }

    // Mapeo entidad -> DTO
    private ClienteDTO toDTO(Cliente entidad) {
        return ClienteDTO.builder()
                .id(entidad.getId())
                .nombre(entidad.getNombre())
                .email(entidad.getEmail())
                .build();
    }

    // Mapeo DTO -> entidad (solo campos editables)
    private Cliente toEntity(ClienteDTO dto) {
        return Cliente.builder()
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .build();
    }
}
