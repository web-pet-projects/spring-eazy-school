package com.quentin.eazyschool.service;

import com.quentin.eazyschool.model.Contact;
import com.quentin.eazyschool.payload.ContactDTO;
import com.quentin.eazyschool.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ContactService {
    private final ContactRepository contactRepository;
    private final ModelMapper modelMapper;

    public boolean saveMessage(ContactDTO contactDTO) {
        Contact contact = modelMapper.map(contactDTO, Contact.class);
        contact.setStatus(Contact.Status.OPEN);
        Contact savedContact = contactRepository.save(contact);
        return savedContact != null && savedContact.getId() > 0;
    }

    public List<ContactDTO> fetchAllByStatus(Contact.Status status) {
        List<Contact> messages = contactRepository.findAllByStatus(status);
        return messages.stream().map(c -> modelMapper.map(c, ContactDTO.class)).toList();
    }

    public boolean closeMessage(Long contactId) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found"));
        contact.setStatus(Contact.Status.CLOSE);
        Contact savedContact = contactRepository.save(contact);
        return null != savedContact && savedContact.getId() > 0;
    }
}
