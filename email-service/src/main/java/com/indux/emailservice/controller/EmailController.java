package com.indux.emailservice.controller;

import com.indux.emailservice.service.EmailService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public String sendEmail(@RequestBody Map<String, String> request) {

        String to = request.get("to");
        String subject = request.get("subject");
        String body = request.get("body");

        emailService.sendMail(to, subject, body);

        return "Email sent successfully";
    }
}