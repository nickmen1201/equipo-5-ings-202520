package com.cultivapp.cultivapp.services;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.cultivapp.cultivapp.dto.ReglaRequest;
import com.cultivapp.cultivapp.models.Regla;
import com.cultivapp.cultivapp.models.enums.TipoRegla;
import com.cultivapp.cultivapp.repositories.ReglaRepository;

import jakarta.transaction.Transactional;

@Service
public class ReglaService {

    private final ReglaRepository reglaRepository;

    public ReglaService(ReglaRepository reglaRepository) {
        this.reglaRepository = reglaRepository;
    }

    /** Create a new Regla from the request. */
    @Transactional
    public Regla createRegla(ReglaRequest request) {
        Regla regla = Regla.builder()
        .descripcion(request.getDescripcion())
        .tipo(request.getTipo())
        .intervaloDias(request.getIntervaloDias())
        .build();

        Regla saved = reglaRepository.save(regla);

        return saved;
    }

    /** List all reglas. */
    public List<Regla> listAll() {
        return reglaRepository.findAll();
    }

    /**
     * Filter reglas dynamically. Any parameter may be null to skip that filter.
     * @param tipo filter by TipoRegla
     * @param minIntervalo minimum intervaloDias (inclusive)
     * @param maxIntervalo maximum intervaloDias (inclusive)
     * @param descripcion substring match (case-insensitive)
     * @return matching reglas
     */
    public List<Regla> filterReglas(List<TipoRegla> tipos) {
        // If no types provided, return all reglas
        if (tipos == null || tipos.isEmpty()) {
            return reglaRepository.findAll();
        }

        Specification<Regla> spec = (root, query, cb) -> {
            return root.get("tipo").in(tipos);
        };

        return reglaRepository.findAll(spec);
    }

    /** Get regla by id. */
    public Optional<Regla> getById(Integer id) {
        return reglaRepository.findById(id);
    }

    /** Update an existing regla with values from the request. */
    @Transactional
    public Regla updateRegla(Integer id, ReglaRequest request) {
        Regla regla = reglaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Regla no encontrada: " + id));
        if (request.getDescripcion() != null)
            regla.setDescripcion(request.getDescripcion());
        if (request.getTipo() != null)
            regla.setTipo(request.getTipo());
        if (request.getIntervaloDias() != null)
            regla.setIntervaloDias(request.getIntervaloDias());
        return reglaRepository.save(regla);
    }

    /** Delete regla by id. */
    @Transactional
    public void deleteRegla(Integer id) {
        if (!reglaRepository.existsById(id))
            throw new IllegalArgumentException("Regla no encontrada: " + id);
        reglaRepository.deleteById(id);
    }

}
 
