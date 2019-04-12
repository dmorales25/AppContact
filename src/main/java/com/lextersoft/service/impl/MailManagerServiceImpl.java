package com.lextersoft.service.impl;

import com.lextersoft.domain.Contact;
import com.lextersoft.domain.User;
import com.lextersoft.repository.ContactRepository;
import com.lextersoft.security.SecurityUtils;
import com.lextersoft.service.MailManagerService;
import com.lextersoft.service.UserService;
import com.lextersoft.web.rest.vm.Message;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailManagerServiceImpl implements MailManagerService {

    private final MailSender mailSender;
    private final UserService userService;
    private final ContactRepository contactRepository;

    public MailManagerServiceImpl(MailSender mailSender, UserService userService, ContactRepository contactRepository) {
        this.mailSender = mailSender;
        this.userService = userService;
        this.contactRepository = contactRepository;
    }

    @Override
    public void sendMail(Message message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        String userLogin = SecurityUtils.getCurrentUserLogin().get();
        User user = userService.getUserWithAuthoritiesByLogin(userLogin).get();

        Contact contact = contactRepository.findById(message.getTo()).get();

        mailMessage.setFrom(user.getEmail());
        mailMessage.setTo(contact.getEmail());
        mailMessage.setSubject(message.getSubject());
        mailMessage.setText(message.getMesssage());

        mailSender.send(mailMessage);
    }
}
