package com.quentin.eazyschool.repository;

import com.quentin.eazyschool.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByUsername(String username);

    Optional<Person> findByEmail(String email);

    Optional<Person> findByIdAndAppClassId(Long studentId, Long classId);
}
