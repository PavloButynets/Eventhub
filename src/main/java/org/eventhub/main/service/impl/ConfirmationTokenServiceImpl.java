package org.eventhub.main.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.eventhub.main.model.ConfirmationToken;
import org.eventhub.main.model.User;
import org.eventhub.main.repository.ConfirmationTokenRepository;
import org.eventhub.main.service.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    public ConfirmationTokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository){
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Override
    public ConfirmationToken create(User user){
        ConfirmationToken token = new ConfirmationToken();
        token.setCreatedAt(new Date());
        token.setUser(user);

        return this.confirmationTokenRepository.save(token);
    }

    @Override
    public ConfirmationToken read(UUID id){
        return this.confirmationTokenRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Token is not valid!"));
    }
}
