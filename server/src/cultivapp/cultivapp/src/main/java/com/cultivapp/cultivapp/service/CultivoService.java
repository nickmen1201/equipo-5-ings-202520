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
        c.getUsuario() != null ? c.getUsuario().getId() : null
    )).collect(Collectors.toList());
}
    

    public Cultivo getCultivoById(Integer id) {
        return cultivoRepository.findById(id)
                .orElseThrow(() -> new CultivoNotFoundException(id));
    }


    public Cultivo createCultivo(Cultivo cultivo) {
        return cultivoRepository.save(cultivo);
    }


    public Cultivo updateCultivo(Integer id, Cultivo updated) {
        Cultivo existing = getCultivoById(id);

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
}
