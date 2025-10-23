package com.cultivapp.cultivapp.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cultivapp.cultivapp.dto.CultivoDTO;
import com.cultivapp.cultivapp.model.Cultivo;
import com.cultivapp.cultivapp.repository.CultivoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CultivoService {


    private final CultivoRepository cultivoRepository;


    public List<CultivoDTO> getAllCultivos() {
    List<Cultivo> cultivos = cultivoRepository.findAll();

        return cultivos.stream().map(c -> new CultivoDTO(
            c.getId(),
            c.getNombre(),
            c.getFechaSiembra(),
            c.getAreaHectareas(),
            c.getEtapaActual(),
            c.getEstado(),
            c.getRendimientoKg(),
            c.getFechaCreacion(),
            c.getFechaActualizacion(),
            c.getEspecie() != null ? c.getEspecie().getNombre() : null,
            c.getEspecie() != null ? c.getEspecie().getImagenUrl() : null,
            c.getUsuario() != null ? c.getUsuario().getId() : null
        )).collect(Collectors.toList());
    }

    public List<CultivoDTO> getCultivosByUsuarioId(Integer usuarioId) {
    List<Cultivo> cultivos = cultivoRepository.findByUsuarioId(usuarioId);

    if (cultivos.isEmpty()) {
        throw new CultivoNotFoundException(usuarioId);
    }

    return cultivos.stream().map(c -> new CultivoDTO(
        c.getId(),
        c.getNombre(),
        c.getFechaSiembra(),
        c.getAreaHectareas(),
        c.getEtapaActual(),
        c.getEstado(),
        c.getRendimientoKg(),
        c.getFechaCreacion(),
        c.getFechaActualizacion(),
        c.getEspecie() != null ? c.getEspecie().getNombre() : null,
        c.getEspecie() != null ? c.getEspecie().getImagenUrl() : null,
        c.getUsuario() != null ? c.getUsuario().getId() : null
    )).collect(Collectors.toList());
}
    

    public Cultivo getCultivoById(Integer id) {
        Cultivo cultivo = cultivoRepository.findById(id)
                .orElseThrow(() -> new CultivoNotFoundException(id));
        if (cultivo.getEspecie() != null) {
            cultivo.getEspecie().getNombre();
        }
        return cultivo;
    }

    public com.cultivapp.cultivapp.dto.CultivoDetailDTO getCultivoDetailById(Integer id) {
        Cultivo cultivo = cultivoRepository.findById(id)
                .orElseThrow(() -> new CultivoNotFoundException(id));
        
        return com.cultivapp.cultivapp.dto.CultivoDetailDTO.builder()
            .id(cultivo.getId())
            .nombre(cultivo.getNombre())
            .fechaSiembra(cultivo.getFechaSiembra())
            .areaHectareas(cultivo.getAreaHectareas())
            .etapaActual(cultivo.getEtapaActual())
            .estado(cultivo.getEstado())
            .rendimientoKg(cultivo.getRendimientoKg())
            .fechaCreacion(cultivo.getFechaCreacion())
            .fechaActualizacion(cultivo.getFechaActualizacion())
            .especie(com.cultivapp.cultivapp.dto.CultivoDetailDTO.EspecieInfo.builder()
                .id(cultivo.getEspecie().getId())
                .nombre(cultivo.getEspecie().getNombre())
                .nombreCientifico(cultivo.getEspecie().getNombreCientifico())
                .imagenUrl(cultivo.getEspecie().getImagenUrl())
                .cicloDias(cultivo.getEspecie().getCicloDias())
                .build())
            .usuario(com.cultivapp.cultivapp.dto.CultivoDetailDTO.UsuarioInfo.builder()
                .id(cultivo.getUsuario().getId())
                .email(cultivo.getUsuario().getEmail())
                .build())
            .build();
    }


    public Cultivo createCultivo(Cultivo cultivo) {
        return cultivoRepository.save(cultivo);
    }


    public Cultivo updateCultivo(Integer id, Cultivo updated) {
        Cultivo existing = getCultivoById(id);

        // Prevent editing archived crops
        if (existing.getEstado() == com.cultivapp.cultivapp.model.enums.Estado.COSECHADO ||
            existing.getEstado() == com.cultivapp.cultivapp.model.enums.Estado.PERDIDO) {
            throw new ArchivedCropException("No editable mientras esté archivado");
        }

        existing.setNombre(updated.getNombre());
        existing.setEspecie(updated.getEspecie());
        existing.setUsuario(updated.getUsuario());
        existing.setFechaSiembra(updated.getFechaSiembra());
        existing.setAreaHectareas(updated.getAreaHectareas());
        existing.setEtapaActual(updated.getEtapaActual());
        existing.setEstado(updated.getEstado());
        existing.setRendimientoKg(updated.getRendimientoKg());

        return cultivoRepository.save(existing);
    }

    public Cultivo toggleEstado(Integer id) {
        Cultivo existing = getCultivoById(id);
        
        // Toggle between ACTIVO and COSECHADO
        if (existing.getEstado() == com.cultivapp.cultivapp.model.enums.Estado.ACTIVO) {
            existing.setEstado(com.cultivapp.cultivapp.model.enums.Estado.COSECHADO);
        } else {
            existing.setEstado(com.cultivapp.cultivapp.model.enums.Estado.ACTIVO);
        }
        
        return cultivoRepository.save(existing);
    }


    public void deleteCultivo(Integer id) {
        Cultivo existing = getCultivoById(id);
        cultivoRepository.delete(existing);
    }

    // ---- Custom Exceptions ----

    public static class CultivoNotFoundException extends RuntimeException {
        public CultivoNotFoundException(Integer id) {
            super("Cultivo con ID " + id + " no encontrado.");
        }
    }

    public static class ArchivedCropException extends RuntimeException {
        public ArchivedCropException(String message) {
            super(message);
        }
    }
}
