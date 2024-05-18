package org.eventhub.main.service;

import com.sendgrid.Response;
import org.eventhub.main.dto.EmailRequest;
import org.eventhub.main.dto.UserResponse;
import org.eventhub.main.dto.UserResponseBriefInfo;
import org.eventhub.main.model.Event;
import org.eventhub.main.model.User;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface EmailService {
    Response sendVerificationEmail(String token, EmailRequest emailRequest) throws IOException;
    Response sendResetPasswordEmail(String token, EmailRequest emailRequest)throws IOException;
    Response sendEmailAboutUpdate(List<User> user, UUID eventId, String title) throws IOException;
    Response sendEventCancellationEmail(List<User> users, String eventTitle) throws IOException;
    Response sendApprovalEmail(User user, UUID eventId, String title)throws IOException;
    Response sendExclusionEmail(User user, String title) throws  IOException;
}
