package com.alberto.portfolio.services.impl;

import com.alberto.portfolio.models.ContactaConmigo;
import com.alberto.portfolio.repositories.ContactaConmigoRepository;
import com.alberto.portfolio.services.ContactaConmigoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactaConmigoServiceImpl implements ContactaConmigoService {

    private final ContactaConmigoRepository contactaConmigoRepository;

    public ContactaConmigoServiceImpl(ContactaConmigoRepository contactaConmigoRepository) {
        this.contactaConmigoRepository = contactaConmigoRepository;
    }

    @Override
    public List<ContactaConmigo> findAll() {
        return contactaConmigoRepository.findAll();
    }

    @Override
    public Optional<ContactaConmigo> findById(String id) {
        return contactaConmigoRepository.findById(id);
    }

    @Override
    public ContactaConmigo create(ContactaConmigo contactaConmigo) {
        return contactaConmigoRepository.save(contactaConmigo);
    }

    @Override
    public void deleteById(String id) {
        contactaConmigoRepository.deleteById(id);
    }
}
