package com.example.px_service.controller.Frontend;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/frontend")
public class PublicController {

    @PostMapping("/register")
    public String register(){
        return "register";
    }

}
