package com.quentin.eazyschool.service;

import com.quentin.eazyschool.constants.EazySchoolConstants;
import com.quentin.eazyschool.model.Contact;
import com.quentin.eazyschool.payload.ContactDTO;
import com.quentin.eazyschool.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContactService {
    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public ContactDTO mapToDTO(Contact contact) {
        ContactDTO contactDTO = new ContactDTO();
        if (null != contact.getContactId()) {
            contactDTO.setContactId(contact.getContactId());
        }
        contactDTO.setName(contact.getName());
        contactDTO.setEmail(contact.getEmail());
        contactDTO.setSubject(contact.getSubject());
        contactDTO.setMessage(contact.getMessage());
        contactDTO.setMobileNum(contact.getMobileNum());

        return contactDTO;
    }

    public boolean saveMessage(ContactDTO contactDTO) {
        Contact contact = new Contact();
        contact.setName(contactDTO.getName());
        contact.setEmail(contactDTO.getEmail());
        contact.setMobileNum(contactDTO.getMobileNum());
        contact.setMessage(contactDTO.getMessage());
        contact.setSubject(contactDTO.getSubject());

        contact.setStatus(Contact.Status.OPEN.name());
        contact.setCreatedAt(LocalDateTime.now());
        contact.setCreatedBy(EazySchoolConstants.ANONYMOUS);
        return contactRepository.save(contact) > 0;
    }

    public List<ContactDTO> findAllWithStatusOpen() {
        List<Contact> messages = contactRepository.findAllWithStatusOpen();
        List<ContactDTO> contactDTOS = messages.stream().map(this::mapToDTO).toList();
        return contactDTOS;
    }

    public boolean closeMessage(Integer contactId, String name) {
        int result = contactRepository.updateStatus(contactId, name, Contact.Status.CLOSE.name());
        return result > 0;
    }
}
