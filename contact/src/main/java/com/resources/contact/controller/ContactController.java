package com.resources.contact.controller;

import com.resources.contact.exception.ResourceNotFoundException;
import com.resources.contact.model.Contact;
import com.resources.contact.model.Phone;
import com.resources.contact.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


@RestController
@Service
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    ContactRepository contactRepository;

    @GetMapping
    public List<Contact> listContacts() {
        return contactRepository.findAll();
    }

    @PostMapping
    public Contact saveContact(@RequestBody Contact contact) {
        return contactRepository.save(contact);
    }

    @GetMapping("/{id}")
    public Contact getContactById(@PathVariable(value = "id") Long contactId) throws ResourceNotFoundException {
        try {
            return contactRepository.findAll().stream().filter(contact -> contact.getId() == contactId).findFirst().get();
        } catch (Exception e) {
            throw new ResourceNotFoundException("Contact not found for this id :: " + contactId);
        }
    }

    @PutMapping("/{id}")
    public Contact updateContact(@PathVariable(value = "id") Long contactId,
                                 @RequestBody Contact contactDetails) throws ResourceNotFoundException {
        try {
            Contact contact =
                    contactRepository.findAll().stream().filter(contacts -> contacts.getId() == contactId).findFirst().get();

            contactDetails.getName().setNameId(contact.getId());
            contactDetails.getAddress().setAddressId(contact.getId());
            for (Phone phone : contactDetails.getPhone()) {
                phone.setPhoneId(contact.getId());
            }

            contact.setName(contactDetails.getName());
            contact.setEmail(contactDetails.getEmail());
            contact.setAddress(contactDetails.getAddress());
            contact.setPhone((ArrayList<Phone>) contactDetails.getPhone());

            Contact updatedContact = contactRepository.save(contact);
            return updatedContact;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Contact not found for this id :: " + contactId);
        }
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteContact(@PathVariable(value = "id") Long contactId) throws ResourceNotFoundException {
        try {
            Contact contact =
                    contactRepository.findAll().stream().filter(contacts -> contacts.getId() == contactId).findFirst().get();

            if (contact == null) {
                throw new ResourceNotFoundException("Contact not found for this id :: " + contactId);
            }
            contactRepository.delete(contact);

            Map<String, Boolean> response = new HashMap<>();
            response.put("Contact Deleted", Boolean.TRUE);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Contact not found for this id :: " + contactId);
        }
    }
}
