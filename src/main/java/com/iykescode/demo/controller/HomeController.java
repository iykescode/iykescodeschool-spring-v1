package com.iykescode.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = { RequestMethod.GET })
    public String displayHomePage() {
        return "home";
    }

    @RequestMapping(value = {"", "/home", "/index"}, method = { RequestMethod.GET })
    public String homePageRedirect() {
        return "redirect:/";
    }
}
