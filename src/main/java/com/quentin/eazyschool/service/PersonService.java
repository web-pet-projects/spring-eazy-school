package com.quentin.eazyschool.service;

import com.quentin.eazyschool.constants.EazySchoolConstants;
import com.quentin.eazyschool.model.Person;
import com.quentin.eazyschool.model.Role;
import com.quentin.eazyschool.payload.RegisterRequest;
import com.quentin.eazyschool.repository.PersonRepository;
import com.quentin.eazyschool.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PersonService {
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean createUser(RegisterRequest registerRequest) {
        Person person = modelMapper.map(registerRequest, Person.class);
        Role role = roleRepository.findByName(EazySchoolConstants.AppRole.STUDENT.name()).orElseThrow(
                () -> new RuntimeException("Role not found")
        );
        person.setRole(role);
        person.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        Person savedPerson = personRepository.save(person);
        return savedPerson.getId() > 0;
    }
}
