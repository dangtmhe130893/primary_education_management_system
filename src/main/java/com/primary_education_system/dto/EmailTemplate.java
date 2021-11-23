package com.primary_education_system.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailTemplate {
    private String receiver;
    private String subject;
    private String content;

    public EmailTemplate() {
    }

    public EmailTemplate(String receiver, String subject, String content) {
        this.receiver = receiver;
        this.subject = subject;
        this.content = content;
    }
}
