package com.bayu.springboot3.jwt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/management")
@RequiredArgsConstructor
public class ManagementController {

    @GetMapping
    public String get() {
        return "GET:: management controller";
    }

    @PostMapping
    public String post() {
        return "POST:: management controller";
    }

    @PutMapping
    public String put() {
        return "PUT:: management controller";
    }

    @DeleteMapping
    public String delete() {
        return "DELETE:: management controller";
    }

}
