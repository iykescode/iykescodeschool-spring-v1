package com.iykescode.demo.controller;

import com.iykescode.demo.model.Contact;
import com.iykescode.demo.model.Person;
import com.iykescode.demo.repository.PersonRepository;
import com.iykescode.demo.service.ContactService;
import com.iykescode.demo.service.GetModelAndViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private final ContactService contactService;
    private final PersonRepository personRepository;

    private final GetModelAndViewService getModelAndViewService;

    @Autowired
    public UserController(ContactService contactService, PersonRepository personRepository, GetModelAndViewService getModelAndViewService) {
        this.contactService = contactService;
        this.personRepository = personRepository;
        this.getModelAndViewService = getModelAndViewService;
    }

    @RequestMapping(value = "/dashboard", method = { RequestMethod.GET, RequestMethod.POST })
    public String displayDashboard(Model model, Authentication authentication, HttpSession session) {
        Person person = personRepository.readByEmail(authentication.getName());
        model.addAttribute("username", person.getName());

        if (Objects.equals(authentication.getAuthorities().toString(), "[ROLE_STUDENT]")) {
            model.addAttribute("roles", "STUDENT");
        } else if (Objects.equals(authentication.getAuthorities().toString(), "[ROLE_ADMIN]")) {
            model.addAttribute("roles", "ADMIN");
        }

        if(null != person.getAClass() && null != person.getAClass().getName()){
            model.addAttribute("enrolledClass", person.getAClass().getName());
        }

        session.setAttribute("loggedInPerson", person);
//        throw new RuntimeException("It's been a bad day!!");
        return "user/dashboard";
    }

    @RequestMapping(value = "/displayMessages/page/{pageNum}", method = { RequestMethod.GET })
    public ModelAndView displayMessages(Model model,
                                        @PathVariable(name = "pageNum") int pageNum,
                                        @RequestParam("sortField") String sortField,
                                        @RequestParam("sortDir") String sortDir,
                                        @RequestParam(value = "closeSuccess", required = false) String closeSuccess,
                                        @RequestParam(value = "error", required = false) String error) {
        Page<Contact> msgPage = contactService.findMsgsWithOpenStatus(pageNum, sortField, sortDir);
        List<Contact> contactMsgs = msgPage.getContent();
        ModelAndView modelAndView = new ModelAndView("user/messages");
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", msgPage.getTotalPages());
        model.addAttribute("totalMsgs", msgPage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        modelAndView.addObject("contactMsgs",contactMsgs);
        return getModelAndViewService.contactController(error, closeSuccess, modelAndView);
    }
}
