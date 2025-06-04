package com.quentin.eazyschool.controller;

import com.quentin.eazyschool.model.Holiday;
import com.quentin.eazyschool.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/holidays")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class HolidayController {

    private final HolidayRepository holidayRepository;

    @GetMapping
    public String displayHolidaysPage(Model model) {
        List<Holiday> holidays = holidayRepository.findAll();
        Holiday.Type[] types = Holiday.Type.values();
        for (Holiday.Type type : types) {
            model.addAttribute(type.name(),
                    holidays.stream().filter(h -> h.getType().equals(type)).toList()
            );
        }
        return "holidays";
    }
}
