package com.iykescode.demo.controller;

import com.iykescode.demo.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping(value = "/student")
public class StudentController {

    @RequestMapping(value = "/displayCourses")
    public ModelAndView displayCourses(HttpSession session) {
        Person person = (Person) session.getAttribute("loggedInPerson");
        ModelAndView modelAndView = new ModelAndView("user/courses_enrolled");
        modelAndView.addObject("person", person);
        return modelAndView;
    }
}
