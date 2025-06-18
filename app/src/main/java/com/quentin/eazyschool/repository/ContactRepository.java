package com.quentin.eazyschool.repository;

import com.quentin.eazyschool.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

//    List<Contact> findAllByStatus(Contact.Status status);

//    @Query("select c from Contact c where c.status = :status")
//    @Query(value = "select * from contact_msg c where c.status = :status", nativeQuery = true)
    Page<Contact> findAllByStatus(Contact.Status status, Pageable pageable);
}
