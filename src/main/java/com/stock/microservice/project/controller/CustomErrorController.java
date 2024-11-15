package com.stock.microservice.project.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Get the error details
        Object status = request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");

        // Log error details (use a logger or System.out for simplicity)
        System.out.println("Error status: " + status);
        if (exception != null) {
            System.out.println("Exception: " + exception.getMessage());
        }

        // Add error message to the model for the view
        model.addAttribute("errorMessage", "Error: " + status + " - " + (exception != null ? exception.getMessage() : "Unknown error"));

        return "error";  // Return to a custom error page (make sure you have error.html in templates)
    }
}