package org.eventhub.main.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.eventhub.main.model.ConfirmationToken;
import org.eventhub.main.model.PasswordResetToken;
import org.eventhub.main.model.User;
import org.eventhub.main.repository.ConfirmationTokenRepository;
import org.eventhub.main.service.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    public ConfirmationTokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository){
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    private String generateToken(){
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        return encoder.encodeToString(bytes);
    }

    @Override
    public ConfirmationToken create(User user){
        ConfirmationToken token = new ConfirmationToken();
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 2);
        Date expiryDate = calendar.getTime();

        token.setToken(generateToken());
        token.setExpiryDate(expiryDate);
        token.setUser(user);

        return this.confirmationTokenRepository.save(token);
    }

    @Override
    public ConfirmationToken read(UUID id){
        return this.confirmationTokenRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Token is not valid!"));
    }

    @Override
    public ConfirmationToken findByToken(String token){
        return this.confirmationTokenRepository.findByToken(token).orElseThrow(()->new EntityNotFoundException("Token is not valid!"));
    }

    @Override
    public void delete(UUID id) {
        this.read(id);
        this.confirmationTokenRepository.deleteById(id);
    }


}
