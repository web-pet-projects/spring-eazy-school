package com.quentin.eazyschool.repository;

import com.quentin.eazyschool.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findAllByStatus(Contact.Status status);
    Page<Contact> findAllByStatus(Contact.Status status, Pageable pageable);
}
