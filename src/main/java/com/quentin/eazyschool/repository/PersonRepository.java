package com.quentin.eazyschool.repository;

import com.quentin.eazyschool.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
