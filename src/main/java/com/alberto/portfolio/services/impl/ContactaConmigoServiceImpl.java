package com.alberto.portfolio.services.impl;

import com.alberto.portfolio.models.ContactaConmigo;
import com.alberto.portfolio.repositories.ContactaConmigoRepository;
import com.alberto.portfolio.services.ContactaConmigoService;
import com.alberto.portfolio.services.EmailService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactaConmigoServiceImpl implements ContactaConmigoService {

    private final ContactaConmigoRepository contactaConmigoRepository;
    private final EmailService emailService;

    public ContactaConmigoServiceImpl(ContactaConmigoRepository contactaConmigoRepository,
                                      EmailService emailService) {
        this.contactaConmigoRepository = contactaConmigoRepository;
        this.emailService = emailService;
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
        if (contactaConmigo.getCreatedAt() == null) {
            contactaConmigo.setCreatedAt(java.time.LocalDateTime.now());
        }

        ContactaConmigo saved = contactaConmigoRepository.save(contactaConmigo);
        emailService.sendContactNotification(saved);
        return saved;
    }

    @Override
    public void deleteById(String id) {
        contactaConmigoRepository.deleteById(id);
    }
}
