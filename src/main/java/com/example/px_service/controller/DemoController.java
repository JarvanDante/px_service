package com.example.px_service.controller;

import com.example.px_service.common.routes.ApiRoutes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping({ApiRoutes.API_V1 + "/demo/hello", "/hello"})
    public String Hello() {
        return "px";
    }

}
