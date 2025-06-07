package com.quentin.eazyschool.repository;

import com.quentin.eazyschool.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
