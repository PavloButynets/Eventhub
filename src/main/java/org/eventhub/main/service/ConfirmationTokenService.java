package org.eventhub.main.service;

import org.eventhub.main.model.ConfirmationToken;
import org.eventhub.main.model.User;

import java.util.UUID;

public interface ConfirmationTokenService {
    ConfirmationToken create(User user);
    ConfirmationToken read(UUID id);
}
