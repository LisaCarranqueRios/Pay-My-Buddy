package com.payment.application.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class MainController {

    /**
     * This method is responsible for getting main page
     *
     * @param model
     * @return
     */
    @GetMapping("/main/welcome")
    public String welcome(Model model) {
        return "main/welcome";
    }
}
