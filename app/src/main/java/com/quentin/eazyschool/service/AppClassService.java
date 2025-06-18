package com.quentin.eazyschool.service;

import com.quentin.eazyschool.model.AppClass;
import com.quentin.eazyschool.model.Person;
import com.quentin.eazyschool.payload.AppClassDTO;
import com.quentin.eazyschool.payload.PersonDTO;
import com.quentin.eazyschool.repository.AppClassRepository;
import com.quentin.eazyschool.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AppClassService {
    private final AppClassRepository appClassRepository;
    private final ModelMapper modelMapper;
    private final PersonRepository personRepository;


    public List<AppClassDTO> fetchAll() {
        List<AppClass> appClasses = appClassRepository.findAll();
        return appClasses.stream().map(c -> modelMapper.map(c, AppClassDTO.class)).toList();
    }

    public void createClass(AppClassDTO appClass) {
        AppClass classEntity = modelMapper.map(appClass, AppClass.class);
        appClassRepository.save(classEntity);
    }

    public AppClassDTO fetchById(Long classId) {
        AppClass classEntity = appClassRepository.findById(classId).orElseThrow(
                () -> new RuntimeException("Class not found")
        );
        AppClassDTO appClassDTO = modelMapper.map(classEntity, AppClassDTO.class);
        List<PersonDTO> students = classEntity.getStudents().stream().map(
                s -> modelMapper.map(s, PersonDTO.class)
        ).collect(Collectors.toList());
        appClassDTO.setStudents(students);
        return appClassDTO;
    }

    public void removeClass(Long classId) {
        AppClass classEntity = appClassRepository.findById(classId).orElseThrow(
                () -> new RuntimeException("Class not found")
        );
        appClassRepository.delete(classEntity);
    }

    public void addStudentByEmail(Long classId, String email) {
        AppClass classEntity = appClassRepository.findById(classId).orElseThrow(
                () -> new RuntimeException("Class not found")
        );
        Person student = personRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("Student not found")
        );
        Person existingStudent = personRepository.findByIdAndAppClassId(student.getId(), classId).orElse(null);
        if (existingStudent != null) {
            throw new RuntimeException("Student already exists");
        }

        student.setAppClass(classEntity);
//        personRepository.save(student);
        classEntity.getStudents().add(student);
        appClassRepository.save(classEntity);
    }

    public void removeStudentById(Long classId, Long studentId) {
        AppClass classEntity = appClassRepository.findById(classId).orElseThrow(
                () -> new RuntimeException("Class not found")
        );
        Person student = personRepository.findByIdAndAppClassId(studentId, classId).orElseThrow(
                () -> new RuntimeException("Student not found in class: " + classEntity.getName())
        );

        student.setAppClass(null);
//        personRepository.save(student);
        classEntity.getStudents().remove(student);
        appClassRepository.save(classEntity);
    }


}
