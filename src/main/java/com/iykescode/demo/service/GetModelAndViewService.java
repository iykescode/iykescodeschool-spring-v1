package com.iykescode.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Service
public class GetModelAndViewService {

    @Value("${iykescodeschool.admin.closeMsg}")
    private String closeMsg;

    public ModelAndView adminController(@RequestParam(value = "error", required = false) String error,
                                         @RequestParam(value = "deleteSuccess", required = false) String deleteSuccess,
                                         @RequestParam(value = "addSuccess", required = false) String addSuccess,
                                         ModelAndView modelAndView) {
        if(error != null) {
            modelAndView.addObject("message", "Cannot find student with that email!!");
            modelAndView.addObject("cssClass", "alert alert-danger");
        } else if (deleteSuccess != null) {
            modelAndView.addObject("message", "Student deleted successfully!!");
            modelAndView.addObject("cssClass", "alert alert-info");
        } else if (addSuccess != null) {
            modelAndView.addObject("message", "Student added successfully!!");
            modelAndView.addObject("cssClass", "alert alert-success");
        }

        return modelAndView;
    }

    public ModelAndView contactController(@RequestParam(value = "error", required = false) String error,
                                          @RequestParam(value = "closeSuccess", required = false) String closeSuccess,
                                          ModelAndView modelAndView) {
        if(error != null) {
            modelAndView.addObject("message", "Cannot find student with that email!!");
            modelAndView.addObject("cssClass", "alert alert-danger");
        } else if (closeSuccess != null) {
            modelAndView.addObject("message", closeMsg);
            modelAndView.addObject("cssClass", "alert alert-success");
        }

        return modelAndView;
    }
}
