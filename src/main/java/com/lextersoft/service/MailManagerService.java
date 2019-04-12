package com.lextersoft.service;

import com.lextersoft.web.rest.vm.Message;

public interface MailManagerService {
    void sendMail(Message message);
}
