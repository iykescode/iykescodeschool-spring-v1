package com.iykescode.demo.controller;

import com.iykescode.demo.model.Person;
import com.iykescode.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/public")
public class PublicController {

    private final PersonService personService;

    @Autowired
    public PublicController(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(value = "/register", method = { RequestMethod.GET })
    public String displayRegisterPage(Model model) {
        model.addAttribute("person", new Person());
        return "auth/register";
    }

    @RequestMapping(value ="/createUser",method = { RequestMethod.POST })
    public String createUser(@Valid @ModelAttribute("person") Person person, Errors errors) {
        if(errors.hasErrors()){
            return "auth/register";
        }

        boolean isSaved = personService.createNewPerson(person);

        if(isSaved){
            return "redirect:/login?register=true";
        }else {
            return "auth/register";
        }
    }
}
