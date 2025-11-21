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
        return proyectoRepository.save(proyecto);
    }

    @Override
    public Proyecto update(String id, Proyecto proyecto) {
        proyecto.setId(id);
        return proyectoRepository.save(proyecto);
    }

    @Override
    public void deleteById(String id) {
        proyectoRepository.deleteById(id);
    }
}
