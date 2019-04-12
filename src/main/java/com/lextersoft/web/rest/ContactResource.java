package com.lextersoft.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lextersoft.domain.Contact;
import com.lextersoft.repository.ContactRepository;
import com.lextersoft.service.MailManagerService;
import com.lextersoft.web.rest.errors.BadRequestAlertException;
import com.lextersoft.web.rest.util.HeaderUtil;
import com.lextersoft.web.rest.vm.Message;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Contact.
 */
@RestController
@RequestMapping("/api")
public class ContactResource {

    private final Logger log = LoggerFactory.getLogger(ContactResource.class);

    private static final String ENTITY_NAME = "contact";

    private final ContactRepository contactRepository;

    private final MailManagerService mailManagerService;


    public ContactResource(ContactRepository contactRepository, MailManagerService mailManagerService) {
        this.contactRepository = contactRepository;
        this.mailManagerService = mailManagerService;
    }

    /**
     * POST  /contacts : Create a new contact.
     *
     * @param contact the contact to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contact, or with status 400 (Bad Request) if the contact has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contacts")
    @Timed
    public ResponseEntity<Contact> createContact(@Valid @RequestBody Contact contact) throws URISyntaxException {
        log.debug("REST request to save Contact : {}", contact);
        if (contact.getId() != null) {
            throw new BadRequestAlertException("A new contact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Contact result = contactRepository.save(contact);
        return ResponseEntity.created(new URI("/api/contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contacts : Updates an existing contact.
     *
     * @param contact the contact to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contact,
     * or with status 400 (Bad Request) if the contact is not valid,
     * or with status 500 (Internal Server Error) if the contact couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contacts")
    @Timed
    public ResponseEntity<Contact> updateContact(@Valid @RequestBody Contact contact) throws URISyntaxException {
        log.debug("REST request to update Contact : {}", contact);
        if (contact.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Contact result = contactRepository.save(contact);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contact.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contacts : get all the contacts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contacts in body
     */
    @GetMapping("/contacts")
    @Timed
    public List<Contact> getAllContacts() {
        log.debug("REST request to get all Contacts");
        return contactRepository.findAll();
    }

    /**
     * GET  /contacts/:id : get the "id" contact.
     *
     * @param id the id of the contact to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contact, or with status 404 (Not Found)
     */
    @GetMapping("/contacts/{id}")
    @Timed
    public ResponseEntity<Contact> getContact(@PathVariable Long id) {
        log.debug("REST request to get Contact : {}", id);
        Optional<Contact> contact = contactRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(contact);
    }

    /**
     * DELETE  /contacts/:id : delete the "id" contact.
     *
     * @param id the id of the contact to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contacts/{id}")
    @Timed
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        log.debug("REST request to delete Contact : {}", id);

        contactRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/contacts/send-message")
    @Timed
    public ResponseEntity<Void> sendMessage(@RequestBody Message message) {
        log.debug("Rest entry to send email to contact : {}", message );

        mailManagerService.sendMail(message);

        return ResponseEntity.ok().headers(HeaderUtil.createAlert(ENTITY_NAME, "OK")).build();
    }
}
