package com.lextersoft.web.rest.vm;

public class Message {
    private Long  to;
    private String subject;
    private String messsage;

    public Message(Long to, String subject, String messsage) {
        this.to = to;
        this.subject = subject;
        this.messsage = messsage;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }
}
