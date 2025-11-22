package com.alberto.portfolio.services;

import com.alberto.portfolio.models.ContactaConmigo;

public interface EmailService {
    void sendContactNotification(ContactaConmigo message);
}
