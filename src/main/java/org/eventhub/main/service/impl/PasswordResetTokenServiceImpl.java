package org.eventhub.main.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.eventhub.main.model.ConfirmationToken;
import org.eventhub.main.model.PasswordResetToken;
import org.eventhub.main.model.User;
import org.eventhub.main.repository.PasswordResetTokenRepository;
import org.eventhub.main.service.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    public PasswordResetTokenServiceImpl(PasswordResetTokenRepository passwordResetTokenRepository){
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    private String generateToken(){
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        return encoder.encodeToString(bytes);
    }

    @Override
    public PasswordResetToken create(User user) {
        PasswordResetToken token = new PasswordResetToken();
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, 2);
        Date expiryDate = calendar.getTime();

        token.setToken(generateToken());
        token.setUser(user);
        token.setExpiryDate(expiryDate);

        return this.passwordResetTokenRepository.save(token);
    }

    @Override
    public PasswordResetToken read(UUID id) {
        return this.passwordResetTokenRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Token is not valid!"));

    }

    @Override
    public PasswordResetToken findByToken(String token){
        return this.passwordResetTokenRepository.findByToken(token).orElseThrow(()->new EntityNotFoundException("Token is not valid!"));
    }

    @Override
    public void delete(UUID id) {
        this.read(id);
        this.passwordResetTokenRepository.deleteById(id);
    }
}
