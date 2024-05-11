package org.eventhub.main.service;

import com.sendgrid.Response;
import org.eventhub.main.dto.EmailRequest;
import org.eventhub.main.dto.UserResponse;
import org.eventhub.main.dto.UserResponseBriefInfo;
import org.eventhub.main.model.User;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface EmailService {
    Response sendVerificationEmail(UUID tokenId, EmailRequest emailRequest) throws IOException;
    Response sendEmailAboutUpdate(List<UserResponseBriefInfo> users, UUID eventId, String eventTitle) throws IOException;
    Response sendEventCancellationEmail(List<UserResponseBriefInfo> users, String eventTitle)throws IOException;
}
