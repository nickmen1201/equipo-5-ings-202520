package com.cultivapp.cultivapp.services;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cultivapp.cultivapp.dto.CultivoDTO;
import com.cultivapp.cultivapp.dto.CultivoDetailDTO;
import com.cultivapp.cultivapp.models.Cultivo;
import com.cultivapp.cultivapp.models.enums.Estado;
import com.cultivapp.cultivapp.repositories.CultivoRepository;

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
            c.getAreaHectareas(),
            c.getEtapaActual(),
            c.getEstado(),
            c.getRendimientoKg(),
            c.getFechaCreacion(),
            c.getFechaActualizacion(),
            c.getEspecie() != null ? c.getEspecie().getNombre() : null,
            c.getEspecie() != null ? c.getEspecie().getImagenUrl() : null,
            c.getEspecie() != null ? c.getEspecie().getDescripcion() : null,
            c.getUsuario() != null ? c.getUsuario().getId() : null
        )).collect(Collectors.toList());
    }

    public List<CultivoDTO> getCultivosByUsuarioId(Integer usuarioId) {
    List<Cultivo> cultivos = cultivoRepository.findByUsuarioId(usuarioId);
    // Return empty list when no crops found for the user (safer for clients)
    return cultivos.stream().map(c -> new CultivoDTO(
        c.getId(),
        c.getNombre(),
        c.getAreaHectareas(),
        c.getEtapaActual(),
        c.getEstado(),
        c.getRendimientoKg(),
        c.getFechaCreacion(),
        c.getFechaActualizacion(),
        c.getEspecie() != null ? c.getEspecie().getNombre() : null,
        c.getEspecie() != null ? c.getEspecie().getImagenUrl() : null,
        c.getEspecie() != null ? c.getEspecie().getDescripcion() : null,
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

    public CultivoDetailDTO getCultivoDetailById(Integer id) {
        Cultivo cultivo = cultivoRepository.findById(id)
                .orElseThrow(() -> new CultivoNotFoundException(id));
        // Build nested DTOs defensively to avoid NPE when relations are missing
        CultivoDetailDTO.EspecieInfo especieInfo = null;
        if (cultivo.getEspecie() != null) {
            var e = cultivo.getEspecie();
            especieInfo = CultivoDetailDTO.EspecieInfo.builder()
                .id(e.getId())
                .nombre(e.getNombre())
                .nombreCientifico(e.getNombreCientifico())
                .imagenUrl(e.getImagenUrl())
                .cicloDias(e.getCicloDias())
                .build();
        }

        CultivoDetailDTO.UsuarioInfo usuarioInfo = null;
        if (cultivo.getUsuario() != null) {
            var u = cultivo.getUsuario();
            usuarioInfo = CultivoDetailDTO.UsuarioInfo.builder()
                .id(u.getId())
                .email(u.getEmail())
                .build();
        }

        return CultivoDetailDTO.builder()
            .id(cultivo.getId())
            .nombre(cultivo.getNombre())
            .areaHectareas(cultivo.getAreaHectareas())
            .etapaActual(cultivo.getEtapaActual())
            .estado(cultivo.getEstado())
            .rendimientoKg(cultivo.getRendimientoKg())
            .fechaCreacion(cultivo.getFechaCreacion())
            .fechaActualizacion(cultivo.getFechaActualizacion())
            .especie(especieInfo)
            .usuario(usuarioInfo)
            .build();
    }


    public Cultivo createCultivo(Cultivo cultivo) {
        return cultivoRepository.save(cultivo);
    }


    public Cultivo updateCultivo(Integer id, Cultivo updated) {
        Cultivo existing = getCultivoById(id);

        // Prevent editing archived crops
        if (existing.getEstado() == Estado.COSECHADO ||
            existing.getEstado() == Estado.PERDIDO) {
            throw new ArchivedCropException("No editable mientras esté archivado o perdido");
        }

        existing.setNombre(updated.getNombre());
        existing.setEspecie(updated.getEspecie());
        existing.setUsuario(updated.getUsuario());
        existing.setAreaHectareas(updated.getAreaHectareas());
        existing.setEtapaActual(updated.getEtapaActual());
        existing.setEstado(updated.getEstado());
        existing.setRendimientoKg(updated.getRendimientoKg());

        return cultivoRepository.save(existing);
    }

    public Cultivo toggleEstado(Integer id) {
        Cultivo existing = getCultivoById(id);
        
        // Toggle between ACTIVO and COSECHADO
        if (existing.getEstado() == Estado.ACTIVO) {
            existing.setEstado(Estado.COSECHADO);
        } else {
            existing.setEstado(Estado.ACTIVO);
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
