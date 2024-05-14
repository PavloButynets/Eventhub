package org.eventhub.main.service.impl;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.eventhub.main.dto.EmailRequest;
import org.eventhub.main.dto.UserResponse;
import org.eventhub.main.dto.UserResponseBriefInfo;
import org.eventhub.main.model.Event;
import org.eventhub.main.model.User;
import org.eventhub.main.service.EmailService;
import org.eventhub.main.service.EventService;
import org.eventhub.main.service.UserService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class EmailServiceImpl implements EmailService {
    private final SendGrid sendGrid;
    private final Email emailFrom;
    private final String url;

    public EmailServiceImpl(){
        this.sendGrid = new SendGrid(System.getenv("sendgrid_key"));
        this.emailFrom = new Email(System.getenv("email"));
        this.url = "http://localhost:3000/";
    }

    private Response sendEmail(Mail mail) throws IOException {
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        return this.sendGrid.api(request);
    }

    private Response sendEmailAboutEvent(List<User> users, String eventTitle, String url, String template) throws IOException {
        Mail mail = new Mail();
        mail.setFrom(this.emailFrom);
        mail.setTemplateId(template);

        for (User user : users) {
            this.sendEmailToParticipant(user, eventTitle, url, mail);
        }

        return this.sendEmail(mail);
    }

    private void sendEmailToParticipant(User user, String eventTitle, String url, Mail mail) throws IOException{
        Personalization personalization = new Personalization();
        personalization.addTo(new Email(user.getEmail()));

        personalization.addDynamicTemplateData("first_name", user.getFirstName());
        personalization.addDynamicTemplateData("event_title", eventTitle);
        personalization.addDynamicTemplateData("url", url);

        mail.addPersonalization(personalization);
    }

    @Override
    public Response sendVerificationEmail(UUID tokenId, EmailRequest emailRequest) throws IOException {
        String verificationEndPoint = this.url + "confirm/" + tokenId.toString();

        Mail mail = new Mail();
        mail.setFrom(this.emailFrom);

        Personalization personalization = new Personalization();
        personalization.addTo(new Email(emailRequest.getTo()));

        personalization.addDynamicTemplateData("first_name", emailRequest.getName());
        personalization.addDynamicTemplateData("eventhub", this.url);
        personalization.addDynamicTemplateData("url",verificationEndPoint);

        mail.addPersonalization(personalization);
        mail.setTemplateId(System.getenv("verification_template"));

        return this.sendEmail(mail);
    }

    @Override
    public Response sendEmailAboutUpdate(List<User>users, UUID eventId, String title) throws IOException {
        return this.sendEmailAboutEvent(users, title,this.url + "event/" + eventId,System.getenv("update_template"));
    }

    @Override
    public Response sendEventCancellationEmail(List<User> users, String eventTitle)throws IOException{
        return this.sendEmailAboutEvent(users,eventTitle, this.url,System.getenv("cancellation_template"));
    }

    @Override
    public Response sendApprovalEmail(User user, UUID eventId, String title) throws IOException {
        Mail mail = new Mail();
        mail.setFrom(this.emailFrom);
        mail.setTemplateId(System.getenv("approval_template"));

        this.sendEmailToParticipant(user, title, this.url + "event/" + eventId, mail);
        return this.sendEmail(mail);
    }

    @Override
    public Response sendExclusionEmail(User user, String title) throws IOException {
        Mail mail = new Mail();
        mail.setFrom(this.emailFrom);
        mail.setTemplateId(System.getenv("exclusion_template"));

        this.sendEmailToParticipant(user, title, this.url, mail);
        return this.sendEmail(mail);
    }


}
