package com.accountant.service.accountant.controller.errorcontroller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;

@Controller
@EnableWebMvc
@ControllerAdvice
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    @ResponseBody
    /**
     * Handle errors*/
    public String error(final HttpServletRequest request) {
        return "<h1>Error occurred</h1>";
    }
}
