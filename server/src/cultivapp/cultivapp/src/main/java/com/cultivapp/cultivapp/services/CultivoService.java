package com.cultivapp.cultivapp.services;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cultivapp.cultivapp.dto.CultivoDTO;
import com.cultivapp.cultivapp.dto.CultivoDetailDTO;
import com.cultivapp.cultivapp.dto.CultivoRequest;
import com.cultivapp.cultivapp.models.Cultivo;
import com.cultivapp.cultivapp.models.Especie;
import com.cultivapp.cultivapp.models.Etapa;
import com.cultivapp.cultivapp.models.Regla;
import com.cultivapp.cultivapp.models.Tarea;
import com.cultivapp.cultivapp.models.Usuario;
import com.cultivapp.cultivapp.models.enums.Estado;
import com.cultivapp.cultivapp.repositories.CultivoRepository;
import com.cultivapp.cultivapp.repositories.EspecieRepository;
import com.cultivapp.cultivapp.repositories.EtapaRepository;
import com.cultivapp.cultivapp.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CultivoService {

    private final EtapaRepository etapaRepository;
    private final CultivoRepository cultivoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EspecieRepository especieRepository; 
    private final NotificacionService notificacionService;


   
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
            c.getEspecie() != null ? c.getEspecie().getEtapas().size() : null,
            c.getUsuario() != null ? c.getUsuario().getId() : null,
            c.getUsuario() != null ? c.getUsuario().getCiudad() : null
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
        c.getEspecie() != null ? c.getEspecie().getEtapas().size() : null,
        c.getUsuario() != null ? c.getUsuario().getId() : null,
        c.getUsuario() != null ? c.getUsuario().getCiudad() : null

        
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

    @Transactional(readOnly = true)
    public CultivoDetailDTO getCultivoDetailById(Integer id) {
    // Buscar cultivo
    Cultivo cultivo = cultivoRepository.findById(id)
            .orElseThrow(() -> new CultivoNotFoundException(id));

    //  Especie 
    CultivoDetailDTO.EspecieInfo especieInfo = null;
    if (cultivo.getEspecie() != null) {
        var e = cultivo.getEspecie();
        especieInfo = CultivoDetailDTO.EspecieInfo.builder()
                .id(e.getId())
                .nombre(e.getNombre())
                .nombreCientifico(e.getNombreCientifico())
                .imagenUrl(e.getImagenUrl())
                .totalEtapas(e.getEtapas().size())
                .build();
    }
    List<CultivoDetailDTO.TareaInfo> tareasInfo = cultivo.getTareas().stream()
        .map(t -> CultivoDetailDTO.TareaInfo.builder()
            .id(t.getId())
            .descripcionRegla(t.getRegla() != null ? t.getRegla().getDescripcion() : "Sin descripción")
            .activa(t.isActiva())
            .realizada(t.isRealizada())
            .vencida(t.isVencida())
            .fechaCreacion(t.getFechaCreacion())
            .fechaProgramada(t.getFechaProgramada())
            .fechaVencimiento(t.getFechaVencimiento())
            .build()
        )
    .toList();


    // --- Usuario ---
    CultivoDetailDTO.UsuarioInfo usuarioInfo = null;
    if (cultivo.getUsuario() != null) {
        var u = cultivo.getUsuario();
        usuarioInfo = CultivoDetailDTO.UsuarioInfo.builder()
                .id(u.getId())
                .email(u.getEmail())
                .build();
    }

    // --- Etapa actual ---
    CultivoDetailDTO.EtapaInfo etapaActualInfo = null;
    if (cultivo.getEtapaActual() != null && cultivo.getEspecie() != null) {

        // Buscar la etapa actual con sus reglas
        Optional<Etapa> etapaOpt = etapaRepository.findEtapaActualConReglas(
                cultivo.getEspecie().getId(),
                cultivo.getEtapaActual()
        );

       if (etapaOpt.isPresent()) {
    Etapa etapa = etapaOpt.get();

    etapaActualInfo = CultivoDetailDTO.EtapaInfo.builder()
        .id(etapa.getId())
        .nombre(etapa.getNombre().name()) // o getNombre()
        .duracionDias(etapa.getDuracionDias())
        .orden(etapa.getOrden())
        .reglas(
            etapa.getReglas().stream()
                .map(r -> CultivoDetailDTO.ReglaInfo.builder()
                    .id(r.getId())
                    .tipo(r.getTipo().name()) // si es enum
                    .descripcion(r.getDescripcion())
                    .intervaloDias(r.getIntervaloDias())
                    .build()
                ).toList()
        )
        .build();
        }
    }

    // --- Armar DTO final ---
    return CultivoDetailDTO.builder()
            .id(cultivo.getId())
            .nombre(cultivo.getNombre())
            .fechaSiembra(cultivo.getFechaCreacion() != null
                    ? cultivo.getFechaCreacion().toLocalDate()
                    : null)
            .areaHectareas(cultivo.getAreaHectareas())
            .etapaActual(cultivo.getEtapaActual())
            .estado(cultivo.getEstado())
            .saludRiego(cultivo.getSaludRiego())
            .saludMantenimiento(cultivo.getSaludMantenimiento())
            .saludFertilizacion(cultivo.getSaludFertilizacion())
            .rendimientoKg(cultivo.getRendimientoKg())
            .fechaCreacion(cultivo.getFechaCreacion())
            .fechaActualizacion(cultivo.getFechaActualizacion())
            .especie(especieInfo)
            .usuario(usuarioInfo)
            .etapaActualInfo(etapaActualInfo)
            .tareas(tareasInfo)
            .build();
}



 public CultivoDTO createCultivo(CultivoRequest request) {
    Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    Especie especie = especieRepository.findById(request.getEspecieId())
        .orElseThrow(() -> new RuntimeException("Especie no encontrada"));

    // Crear cultivo con etapa inicial
    Cultivo cultivo = Cultivo.builder()
        .nombre(request.getNombre())
        .areaHectareas(request.getAreaHectareas())
        .estado(Estado.ACTIVO)
        .usuario(usuario)
        .especie(especie)
        .etapaActual((short) 1) 
        .build();

    // Crear tareas iniciales
    List<Tarea> tareasIniciales = new ArrayList<>();

    especie.getEtapas().stream()
        .filter(etapa -> etapa.getOrden().equals(cultivo.getEtapaActual()))
        .findFirst()
        .ifPresent(etapa -> {
            for (Regla regla : etapa.getReglas()) {
                Tarea tarea = Tarea.builder()
                    .cultivo(cultivo)
                    .regla(regla)
                    .fechaProgramada(LocalDateTime.now())
                    .fechaVencimiento(LocalDateTime.now().plusDays(regla.getIntervaloDias()))
                    .activa(true)
                    .realizada(false)
                    .vencida(false)
                    .build();
                tareasIniciales.add(tarea);
            }
        });

    cultivo.setTareas(tareasIniciales);

    Cultivo saved = cultivoRepository.save(cultivo);

    notificacionService.createNotificacion(
        "Nuevo cultivo creado: " + saved.getNombre(),
        usuario.getId()
    );

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
        saved.getEspecie() != null ? saved.getEspecie().getEtapas().size() : null,
        saved.getUsuario() != null ? saved.getUsuario().getId() : null,
        saved.getUsuario() != null ? saved.getUsuario().getCiudad() : null

    );
}



    public CultivoDTO updateCultivo(Integer id, CultivoRequest request) {
        Cultivo existing = getCultivoById(id);

        // Prevent editing archived crops
        if (existing.getEstado() == Estado.COSECHADO ||
            existing.getEstado() == Estado.PERDIDO) {
            throw new ArchivedCropException("No editable mientras esté archivado o perdido");
        }

        // Fetch full entities from database
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        Especie especie = especieRepository.findById(request.getEspecieId())
            .orElseThrow(() -> new RuntimeException("Especie no encontrada"));

        // Update fields
        existing.setNombre(request.getNombre());
        existing.setAreaHectareas(request.getAreaHectareas());
        existing.setRendimientoKg(request.getRendimientoKg());
        
        // Update etapaActual if provided
        if (request.getEtapaActual() != null) {
            existing.setEtapaActual(request.getEtapaActual());
        }
        
        // Update estado if provided
        if (request.getEstado() != null && !request.getEstado().isEmpty()) {
            try {
                existing.setEstado(Estado.valueOf(request.getEstado()));
            } catch (IllegalArgumentException e) {
                // If invalid estado, ignore it
            }
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
            saved.getEspecie() != null ? saved.getEspecie().getEtapas().size() : null,
            saved.getUsuario() != null ? saved.getUsuario().getId() : null,
            saved.getUsuario() != null ? saved.getUsuario().getCiudad() : null

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
            saved.getEspecie() != null ? saved.getEspecie().getEtapas().size() : null,
            saved.getUsuario() != null ? saved.getUsuario().getId() : null,
            saved.getUsuario() != null ? saved.getUsuario().getCiudad() : null

        );
    }


    public void deleteCultivo(Integer id) {
        Cultivo existing = getCultivoById(id);
        cultivoRepository.delete(existing);
    }


 public void verificarCambiosDeEtapa() {
        List<Cultivo> cultivos = cultivoRepository.findAll();

        for (Cultivo cultivo : cultivos) {
            // Obtiene la etapa actual del cultivo
            Short numeroEtapa = cultivo.getEtapaActual();
            if (numeroEtapa == null) continue;

            // Busca la Etapa correspondiente a la especie y al orden actual
            Etapa etapaActual = etapaRepository.findByEspecieIdAndOrden(
                cultivo.getEspecie().getId(), numeroEtapa
            );

            if (etapaActual == null || etapaActual.getDuracionDias() == null) continue;

            // Fecha de inicio y duración
            LocalDateTime inicio = cultivo.getFechaInicioEtapa();
            if (inicio == null) continue;

            LocalDateTime finEsperado = inicio.plusDays(etapaActual.getDuracionDias());

            // Si ya pasó el tiempo de la etapa actual
            if (LocalDateTime.now().isAfter(finEsperado)) {
                avanzarEtapa(cultivo, etapaActual);
            }
        }
    }

    private void avanzarEtapa(Cultivo cultivo, Etapa etapaActual) {
        // Buscar la siguiente etapa de la misma especie
        Etapa siguiente = etapaRepository.findNextEtapa(
            cultivo.getEspecie().getId(),
            etapaActual.getOrden()
        );

        if (siguiente != null) {
            cultivo.setEtapaActual(siguiente.getOrden());
            cultivo.setFechaInicioEtapa(LocalDateTime.now());
            cultivoRepository.save(cultivo);
            Usuario usuario = cultivo.getUsuario();
            notificacionService.createNotificacion(
                "cultivo "+cultivo.getNombre() +" cambio a etapa: " + siguiente.getNombre() ,
                usuario.getId()
            );
  
            System.out.println("🌿 Cultivo " + cultivo.getId() + 
                " avanzó a etapa: " + siguiente.getNombre());
        } else {
            System.out.println("🌾 Cultivo " + cultivo.getId() + 
                " ya está en la última etapa.");
        }
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