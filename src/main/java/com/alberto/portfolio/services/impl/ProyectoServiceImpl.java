package com.alberto.portfolio.services.impl;

import com.alberto.portfolio.models.Proyecto;
import com.alberto.portfolio.repositories.ProyectoRepository;
import com.alberto.portfolio.services.ProyectoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProyectoServiceImpl implements ProyectoService {

    private final ProyectoRepository proyectoRepository;

    public ProyectoServiceImpl(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
    }

    @Override
    public List<Proyecto> findAll() {
        return proyectoRepository.findAll();
    }

    @Override
    public Optional<Proyecto> findById(String id) {
        return proyectoRepository.findById(id);
    }

    @Override
    public Proyecto create(Proyecto proyecto) {
        // If requested position is taken, clear the other so this one can take it
        if (proyecto.getPosicion() != null) {
            proyectoRepository.findByPosicion(proyecto.getPosicion())
                    .ifPresent(other -> {
                        // free the position from the other project
                        other.setPosicion(null);
                        proyectoRepository.save(other);
                    });
        }
        return proyectoRepository.save(proyecto);
    }

    @Override
    public Proyecto update(String id, Proyecto proyecto) {
        proyecto.setId(id);
        // Swap positions if target position is already used by another
        if (proyecto.getPosicion() != null) {
            proyectoRepository.findByPosicion(proyecto.getPosicion())
                    .ifPresent(other -> {
                        if (!other.getId().equals(id)) {
                            Integer currentPos = null;
                            // Fetch current persisted entity to read its previous position
                            proyectoRepository.findById(id).ifPresent(existing -> {
                                // capture existing position in array hack since effectively final requirement
                            });
                            // We can't capture local from lambda easily; perform simple swap using repository again
                            Proyecto existing = proyectoRepository.findById(id).orElse(null);
                            Integer prev = existing != null ? existing.getPosicion() : null;
                            other.setPosicion(prev);
                            proyectoRepository.save(other);
                        }
                    });
        }
        return proyectoRepository.save(proyecto);
    }

    @Override
    public void deleteById(String id) {
        proyectoRepository.deleteById(id);
    }
}
