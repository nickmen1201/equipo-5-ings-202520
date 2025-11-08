package com.cultivapp.cultivapp.services;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cultivapp.cultivapp.dto.CultivoDTO;
import com.cultivapp.cultivapp.dto.CultivoDetailDTO;
import com.cultivapp.cultivapp.dto.CultivoRequest;
import com.cultivapp.cultivapp.models.Cultivo;
import com.cultivapp.cultivapp.models.Especie;
import com.cultivapp.cultivapp.models.Usuario;
import com.cultivapp.cultivapp.models.enums.Estado;
import com.cultivapp.cultivapp.repositories.CultivoRepository;
import com.cultivapp.cultivapp.repositories.EspecieRepository;
import com.cultivapp.cultivapp.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CultivoService {

    private final CultivoRepository cultivoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EspecieRepository especieRepository;


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
            .fechaSiembra(cultivo.getFechaCreacion() != null ? cultivo.getFechaCreacion().toLocalDate() : null)
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


    public CultivoDTO createCultivo(CultivoRequest request) {
        // Fetch full entities from database
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        Especie especie = especieRepository.findById(request.getEspecieId())
            .orElseThrow(() -> new RuntimeException("Especie no encontrada"));
        
        // Build Cultivo entity with fechaSiembra defaulting to today if not provided
        Cultivo cultivo = Cultivo.builder()
            .nombre(request.getNombre())
            .areaHectareas(request.getAreaHectareas())
            .etapaActual(request.getEtapaActual())
            .estado(request.getEstado())
            .rendimientoKg(request.getRendimientoKg())
            .fechaSiembra(request.getFechaSiembra() != null ? request.getFechaSiembra() : LocalDate.now())
            .usuario(usuario)
            .especie(especie)
            .build();
        
        Cultivo saved = cultivoRepository.save(cultivo);
        
        // Return DTO to avoid lazy-loading issues
        return new CultivoDTO(
            saved.getId(),
            saved.getNombre(),
            saved.getAreaHectareas(),
            saved.getEtapaActual(),
            saved.getEstado(),
            saved.getRendimientoKg(),
            saved.getFechaCreacion(),
            saved.getFechaActualizacion(),
            saved.getEspecie() != null ? saved.getEspecie().getNombre() : null,
            saved.getEspecie() != null ? saved.getEspecie().getImagenUrl() : null,
            saved.getEspecie() != null ? saved.getEspecie().getDescripcion() : null,
            saved.getUsuario() != null ? saved.getUsuario().getId() : null
        );
    }




    public CultivoDTO updateCultivo(Integer id, CultivoRequest request) {
        Cultivo existing = getCultivoById(id);

        // Prevent editing archived crops
        if (existing.getEstado() == Estado.COSECHADO ||
            existing.getEstado() == Estado.PERDIDO) {
            throw new ArchivedCropException("No editable mientras esté archivado o perdido");
        }

        // Debug logging
        System.out.println("UPDATE REQUEST - ID: " + id);
        System.out.println("  Current area: " + existing.getAreaHectareas());
        System.out.println("  New area from request: " + request.getAreaHectareas());

        // Fetch full entities from database
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        Especie especie = especieRepository.findById(request.getEspecieId())
            .orElseThrow(() -> new RuntimeException("Especie no encontrada"));

        // Update fields
        existing.setNombre(request.getNombre());
        existing.setAreaHectareas(request.getAreaHectareas());
        System.out.println("  After setter: " + existing.getAreaHectareas());
        existing.setEtapaActual(request.getEtapaActual());
        existing.setEstado(request.getEstado());
        existing.setRendimientoKg(request.getRendimientoKg());
        if (request.getFechaSiembra() != null) {
            existing.setFechaSiembra(request.getFechaSiembra());
        }
        existing.setUsuario(usuario);
        existing.setEspecie(especie);

        Cultivo saved = cultivoRepository.save(existing);
        
        // Return DTO to avoid lazy-loading issues
        return new CultivoDTO(
            saved.getId(),
            saved.getNombre(),
            saved.getAreaHectareas(),
            saved.getEtapaActual(),
            saved.getEstado(),
            saved.getRendimientoKg(),
            saved.getFechaCreacion(),
            saved.getFechaActualizacion(),
            saved.getEspecie() != null ? saved.getEspecie().getNombre() : null,
            saved.getEspecie() != null ? saved.getEspecie().getImagenUrl() : null,
            saved.getEspecie() != null ? saved.getEspecie().getDescripcion() : null,
            saved.getUsuario() != null ? saved.getUsuario().getId() : null
        );
    }

    public CultivoDTO toggleEstado(Integer id) {
        Cultivo existing = getCultivoById(id);
        
        // Toggle between ACTIVO and COSECHADO
        if (existing.getEstado() == Estado.ACTIVO) {
            existing.setEstado(Estado.COSECHADO);
        } else {
            existing.setEstado(Estado.ACTIVO);
        }
        
        Cultivo saved = cultivoRepository.save(existing);
        
        // Return DTO to avoid lazy-loading issues
        return new CultivoDTO(
            saved.getId(),
            saved.getNombre(),
            saved.getAreaHectareas(),
            saved.getEtapaActual(),
            saved.getEstado(),
            saved.getRendimientoKg(),
            saved.getFechaCreacion(),
            saved.getFechaActualizacion(),
            saved.getEspecie() != null ? saved.getEspecie().getNombre() : null,
            saved.getEspecie() != null ? saved.getEspecie().getImagenUrl() : null,
            saved.getEspecie() != null ? saved.getEspecie().getDescripcion() : null,
            saved.getUsuario() != null ? saved.getUsuario().getId() : null
        );
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
