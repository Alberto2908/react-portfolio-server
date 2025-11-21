package com.alberto.portfolio.services;

import com.alberto.portfolio.models.ContactaConmigo;

import java.util.List;
import java.util.Optional;

public interface ContactaConmigoService {

    List<ContactaConmigo> findAll();

    Optional<ContactaConmigo> findById(String id);

    ContactaConmigo create(ContactaConmigo contactaConmigo);

    void deleteById(String id);
}
