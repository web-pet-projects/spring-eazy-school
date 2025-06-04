package com.quentin.eazyschool.config;

import com.quentin.eazyschool.model.Holiday;
import com.quentin.eazyschool.repository.HolidayRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
       registry.addViewController("/courses").setViewName("courses");
       registry.addViewController("/about").setViewName("about");
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CommandLineRunner initData(HolidayRepository holidayRepository) {
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
        };
    }
}
