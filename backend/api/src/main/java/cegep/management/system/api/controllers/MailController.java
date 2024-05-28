package cegep.management.system.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cegep.management.system.api.model.Mail;
import cegep.management.system.api.service.MailService;

@RestController
@RequestMapping("/api/v1/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @GetMapping
    public ResponseEntity<List<Mail>> getAllMails() {
        List<Mail> mails = mailService.getAllMails();
        return ResponseEntity.ok(mails);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mail> getMailById(@PathVariable Long id) {
        Mail mail = mailService.getMailById(id)
                .orElseThrow(() -> new RuntimeException("Mail not found with id: " + id));
        return ResponseEntity.ok(mail);
    }

    @GetMapping("/sender/{senderId}")
    public ResponseEntity<List<Mail>> getMailsBySenderId(@PathVariable Long senderId) {
        List<Mail> mails = mailService.getMailsBySenderId(senderId);
        return ResponseEntity.ok(mails);
    }

    @GetMapping("/receiver/{receiverId}")
    public ResponseEntity<List<Mail>> getMailsByReceiverId(@PathVariable Long receiverId) {
        List<Mail> mails = mailService.getMailsByReceiverId(receiverId);
        return ResponseEntity.ok(mails);
    }

    @PostMapping
    public ResponseEntity<Mail> createMail(@RequestBody Mail mail) {
        Mail createdMail = mailService.createMail(mail);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMail);
    }

}
