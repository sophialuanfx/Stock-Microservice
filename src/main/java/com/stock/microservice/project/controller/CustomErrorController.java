package com.stock.microservice.project.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
public class CustomErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(ERROR_PATH)
    public String handleError(HttpServletRequest request, Model model) {
        // Get error status code
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Integer statusCode = status != null ? Integer.valueOf(status.toString()) : HttpStatus.INTERNAL_SERVER_ERROR.value();

        // Get error message
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        String errorMessage = message != null ? message.toString() : "An unexpected error occurred";

        // Set error attributes for the view
        model.addAttribute("timestamp", LocalDateTime.now());
        model.addAttribute("statusCode", statusCode);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("path", request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));

        return "error";
    }

    public String getErrorPath() {
        return ERROR_PATH;
    }
}
