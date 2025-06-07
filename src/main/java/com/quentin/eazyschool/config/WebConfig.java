package com.quentin.eazyschool.config;

import com.quentin.eazyschool.model.Holiday;
import com.quentin.eazyschool.model.Person;
import com.quentin.eazyschool.model.Role;
import com.quentin.eazyschool.repository.HolidayRepository;
import com.quentin.eazyschool.repository.PersonRepository;
import com.quentin.eazyschool.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
       registry.addViewController("/about").setViewName("about");
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    @Bean
    public CommandLineRunner initData(HolidayRepository holidayRepository, RoleRepository roleRepository, PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            List<Holiday> holidays = Arrays.asList(
                    new Holiday(1, " Jan 1 ", "New Year's Day", Holiday.Type.FESTIVAL),
                    new Holiday(2, " Oct 31 ", "Halloween", Holiday.Type.FESTIVAL),
                    new Holiday(3, " Nov 24 ", "Thanksgiving Day", Holiday.Type.FESTIVAL),
                    new Holiday(4, " Dec 25 ", "Christmas", Holiday.Type.FESTIVAL),
                    new Holiday(5, " Jan 17 ", "Martin Luther King Jr. Day", Holiday.Type.FEDERAL),
                    new Holiday(6, " July 4 ", "Independence Day", Holiday.Type.FEDERAL),
                    new Holiday(7, " Sep 5 ", "Labor Day", Holiday.Type.FEDERAL),
                    new Holiday(8, " Nov 11 ", "Veterans Day", Holiday.Type.FEDERAL)
            );
            for (Holiday holiday : holidays) {
                holiday.setCreatedBy("DBA");
            }
            holidayRepository.saveAll(holidays);

            List<Role> roles = Arrays.asList(
                    new Role(1, "STUDENT"),
                    new Role(999, "ADMIN")
            );
            for (Role role : roles) {
                role.setCreatedBy("DBA");
            }
            roleRepository.saveAll(roles);

            Person admin = Person.builder()
                    .username("admin")
                    .email("admin@quentin.eazyschool.com")
                    .password(passwordEncoder.encode("admin"))
                    .role(roles.stream().filter(r -> r.getName().equals("ADMIN")).findFirst().orElseThrow(
                            () -> new RuntimeException("No admin role found")
                    ))
                    .build();
            Person user1 = Person.builder()
                    .username("user1")
                    .email("user1@example.com")
                    .password(passwordEncoder.encode("user1"))
                    .role(roles.stream().filter(r -> r.getName().equals("STUDENT")).findFirst().orElseThrow(
                            () -> new RuntimeException("No STUDENT role found")
                    ))
                    .dateOfBirth(LocalDate.of(2000,2,20))
                    .gender(Person.Gender.FEMALE)
                    .firstName("John").lastName("Doe")
                    .mobileNum("1234567890")
                    .build();
            admin.setCreatedBy("DBA");
            user1.setCreatedBy("DBA");
            personRepository.save(admin);
            personRepository.save(user1);

        };
    }
}
