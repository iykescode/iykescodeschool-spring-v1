package com.iykescode.demo.controller;

import com.iykescode.demo.model.Contact;
import com.iykescode.demo.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
public class ContactController {

    @Value("${iykescodeschool.contact.successMsg}")
    private String successMsg;

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping(value = "/contact", method = { RequestMethod.GET })
    public String displayContactPage(Model model, @RequestParam(value = "successMsg", required = false) String success) {
        model.addAttribute("contact", new Contact());

        if (success != null) {
            model.addAttribute("message", successMsg);
            model.addAttribute("cssClass", "alert alert-success");
        }

        return "contact";
    }

    @RequestMapping(value = {"/contact-us", "/contact_us"}, method = { RequestMethod.GET })
    public String contactPageRedirect() {
        return "redirect:/contact";
    }

    @RequestMapping(value = "/saveMsg", method = { RequestMethod.POST })
    public String saveMessage(@Valid @ModelAttribute("contact") Contact contact, Errors errors) {
        if(errors.hasErrors()){
            log.error("Contact form validation failed due to : " + errors.toString());
            return "contact";
        }

        contactService.saveMessageDetails(contact);
        return "redirect:/contact?successMsg=true";
    }

    @RequestMapping(value = "/closeMsg/{id}",method = { RequestMethod.POST })
    public String closeMsg(@PathVariable int id) {
        contactService.updateMsgStatus(id);
        return "redirect:/user/displayMessages/page/1?sortField=name&sortDir=desc&closeSuccess=true";
    }
}
