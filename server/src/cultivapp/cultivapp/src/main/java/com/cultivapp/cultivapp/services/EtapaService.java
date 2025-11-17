package com.cultivapp.cultivapp.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cultivapp.cultivapp.dto.EtapaDTO;
import com.cultivapp.cultivapp.dto.EtapaRequest;
import com.cultivapp.cultivapp.dto.ReglaRequest;
import com.cultivapp.cultivapp.models.Etapa;
import com.cultivapp.cultivapp.models.Regla;
import com.cultivapp.cultivapp.repositories.EspecieRepository;
import com.cultivapp.cultivapp.repositories.EtapaRepository;
import com.cultivapp.cultivapp.repositories.ReglaRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EtapaService {

    private final EtapaRepository etapaRepository;
    private final EspecieRepository especieRepository;
    private final ReglaRepository reglaRepository;

    @Transactional
    public EtapaDTO createEtapa(EtapaRequest request) {
        var especie = especieRepository.findById(request.getEspecieId())
                .orElseThrow(() -> new EntityNotFoundException("Especie no encontrada"));

        var etapa = new Etapa();
        etapa.setNombre(request.getNombre());
        etapa.setEspecie(especie);
        etapa.setDuracionDias(request.getDuracionDias());
        etapa.setOrden(request.getOrden());

        // Validar y añadir reglas
        if (request.getReglaIds() == null || request.getReglaIds().size() < 1) {
            throw new IllegalArgumentException("Debe especificar al menos 1 regla para la etapa");
        }

        var reglas = reglaRepository.findAllById(request.getReglaIds());
        if (reglas.size() != request.getReglaIds().size()) {
            throw new EntityNotFoundException("Una o más reglas no fueron encontradas");
        }
        etapa.getReglas().addAll(reglas);

        return convertToDTO(etapaRepository.save(etapa));
    }

    @Transactional(readOnly = true)
    public EtapaDTO getEtapaById(Integer id) {
        return convertToDTO(etapaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Etapa no encontrada")));
    }

    @Transactional(readOnly = true)
    public List<EtapaDTO> getAllEtapas() {
        return etapaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EtapaDTO> getEtapasByEspecie(Integer especieId) {
        var especie = especieRepository.findById(especieId)
                .orElseThrow(() -> new EntityNotFoundException("Especie no encontrada"));

        return etapaRepository.findByEspecieOrderByOrden(especie).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public EtapaDTO updateEtapa(Integer id, EtapaRequest request) {
        var etapa = etapaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Etapa no encontrada"));

        var especie = especieRepository.findById(request.getEspecieId())
                .orElseThrow(() -> new EntityNotFoundException("Especie no encontrada"));

        etapa.setNombre(request.getNombre());
        etapa.setEspecie(especie);
        etapa.setDuracionDias(request.getDuracionDias());
        etapa.setOrden(request.getOrden());

        // Validar y actualizar reglas
        if (request.getReglaIds() == null || request.getReglaIds().size() < 3) {
            throw new IllegalArgumentException("Debe especificar al menos 3 reglas para la etapa");
        }

        etapa.getReglas().clear();
        var reglas = reglaRepository.findAllById(request.getReglaIds());
        if (reglas.size() != request.getReglaIds().size()) {
            throw new EntityNotFoundException("Una o más reglas no fueron encontradas");
        }
        etapa.getReglas().addAll(reglas);

        return convertToDTO(etapaRepository.save(etapa));
    }

    @Transactional
    public void deleteEtapa(Integer id) {
        if (!etapaRepository.existsById(id)) {
            throw new EntityNotFoundException("Etapa no encontrada");
        }
        etapaRepository.deleteById(id);
    }

    @Transactional
    public EtapaDTO addReglaToEtapa(Integer etapaId, Integer reglaId) {
        var etapa = etapaRepository.findById(etapaId)
                .orElseThrow(() -> new EntityNotFoundException("Etapa no encontrada"));

        var regla = reglaRepository.findById(reglaId)
                .orElseThrow(() -> new EntityNotFoundException("Regla no encontrada"));

        etapa.getReglas().add(regla);
        return convertToDTO(etapaRepository.save(etapa));
    }

    @Transactional
    public EtapaDTO removeReglaFromEtapa(Integer etapaId, Integer reglaId) {
        var etapa = etapaRepository.findById(etapaId)
                .orElseThrow(() -> new EntityNotFoundException("Etapa no encontrada"));

        var regla = reglaRepository.findById(reglaId)
                .orElseThrow(() -> new EntityNotFoundException("Regla no encontrada"));

        etapa.getReglas().remove(regla);
        return convertToDTO(etapaRepository.save(etapa));
    }

    private EtapaDTO convertToDTO(Etapa etapa) {
        var dto = new EtapaDTO();
        dto.setId(etapa.getId());
        dto.setNombre(etapa.getNombre());
        dto.setEspecie(null); // TODO: Convert especie to EspecieDTO
        dto.setDuracionDias(etapa.getDuracionDias());
        dto.setOrden(etapa.getOrden());
        dto.setReglas(etapa.getReglas().stream()
                .map(this::convertReglaToDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    private ReglaRequest convertReglaToDTO(Regla regla) {
        var dto = new ReglaRequest();
        dto.setDescripcion(regla.getDescripcion());
        dto.setTipo(regla.getTipo());
        dto.setIntervaloDias(regla.getIntervaloDias());
        return dto;
    }

    @Transactional
    public List<EtapaDTO> createEtapas(List<EtapaRequest> requests) {
        return requests.stream()
                .map(this::createEtapa)
                .collect(Collectors.toList());
    }
}
