package com.example.attornatus.attornatus.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {


        @RequestMapping("/error")
        public ResponseEntity<String> handleError(HttpServletRequest request) {
        String message = "A página solicitada não foi encontrada.";

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

}