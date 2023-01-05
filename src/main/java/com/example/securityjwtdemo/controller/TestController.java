package com.example.securityjwtdemo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/test")
public class TestController {
    @GetMapping("/all")
    public String allAccess(){
        return "Public Content.";
    }
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('PM') or hasRole('ADMIN')")
    public  String userAccess(){
        return "User Content";
    }
    @GetMapping("/pm")
    @PreAuthorize("hasRole('PM')")
    public String pmAccess() {
        return "PM Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }
}
