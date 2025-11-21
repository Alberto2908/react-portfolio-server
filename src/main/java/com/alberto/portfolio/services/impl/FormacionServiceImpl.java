package com.alberto.portfolio.services.impl;

import com.alberto.portfolio.models.Formacion;
import com.alberto.portfolio.repositories.FormacionRepository;
import com.alberto.portfolio.services.FormacionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormacionServiceImpl implements FormacionService {

    private final FormacionRepository formacionRepository;

    public FormacionServiceImpl(FormacionRepository formacionRepository) {
        this.formacionRepository = formacionRepository;
    }

    @Override
    public List<Formacion> findAll() {
        return formacionRepository.findAll();
    }

    @Override
    public Optional<Formacion> findById(String id) {
        return formacionRepository.findById(id);
    }

    @Override
    public Formacion create(Formacion formacion) {
        return formacionRepository.save(formacion);
    }

    @Override
    public Formacion update(String id, Formacion formacion) {
        formacion.setId(id);
        return formacionRepository.save(formacion);
    }

    @Override
    public void deleteById(String id) {
        formacionRepository.deleteById(id);
    }
}
