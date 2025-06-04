package com.quentin.eazyschool.repository;

import com.quentin.eazyschool.model.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidayRepository extends JpaRepository<Holiday, Integer> {
    Iterable<Holiday> findAllByType(Holiday.Type type);
}
