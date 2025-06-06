package com.quentin.eazyschool.service;

import com.quentin.eazyschool.constants.EazySchoolConstants;
import com.quentin.eazyschool.model.Address;
import com.quentin.eazyschool.model.Person;
import com.quentin.eazyschool.model.Role;
import com.quentin.eazyschool.payload.PersonDTO;
import com.quentin.eazyschool.payload.RegisterRequest;
import com.quentin.eazyschool.repository.AddressRepository;
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
    private final AddressRepository addressRepository;

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

    public PersonDTO fetchUserByUsername(String username) {
        Person person = personRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("Person not found")
        );
        return modelMapper.map(person, PersonDTO.class);
    }

    public PersonDTO updateDetails(PersonDTO personDTO) {
        Person person = personRepository.findById(personDTO.getId()).orElseThrow(
                () -> new RuntimeException("Person not found")
        );
        Address address = new Address();
        if (person.getAddress() != null && person.getAddress().getId() > 0) {
            address = addressRepository.findById(personDTO.getAddress().getId()).orElseThrow(
                    () -> new RuntimeException("Address not found")
            );
        }

        modelMapper.map(personDTO.getAddress(), address);
        person.setAddress(address);

        String hashedPassword = person.getPassword();
        Role role = person.getRole();
        modelMapper.map(personDTO, person);
        person.setPassword(hashedPassword);
        person.setRole(role);
        Person savedPerson = personRepository.save(person);
        return modelMapper.map(savedPerson, PersonDTO.class);
    }
}
