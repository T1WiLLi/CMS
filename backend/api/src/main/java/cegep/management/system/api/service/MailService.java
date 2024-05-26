package cegep.management.system.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cegep.management.system.api.error.ResourceNotFoundException;
import cegep.management.system.api.model.Mail;
import cegep.management.system.api.repo.MailRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MailService {

    private final MailRepository mailRepository;

    public MailService(MailRepository mailRepository) {
        this.mailRepository = mailRepository;
    }

    public List<Mail> getAllMails() {
        return mailRepository.findAll();
    }

    public Optional<Mail> getMailById(Long mailId) {
        return mailRepository.findById(mailId);
    }

    public Mail createMail(Mail mail) {
        return mailRepository.save(mail);
    }

    @Transactional
    public Mail updateMail(Long mailId, Mail updatedMail) {
        return mailRepository.findById(mailId).map(mail -> {
            mail.setReceiver(updatedMail.getReceiver());
            mail.setSender(updatedMail.getSender());
            mail.setSubject(updatedMail.getSubject());
            mail.setContent(updatedMail.getContent());
            mail.setDate(updatedMail.getDate());
            mail.setRead(updatedMail.isRead());
            return mailRepository.save(mail);
        }).orElseThrow(() -> new ResourceNotFoundException("Mail not found"));
    }

    public void deleteMail(Long mailId) {
        mailRepository.deleteById(mailId);
    }
}
