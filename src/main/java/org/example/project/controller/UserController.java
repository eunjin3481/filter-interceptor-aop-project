package org.example.project.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.project.service.UserService;
import org.example.project.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<?> singup(@RequestBody User user) {
        return new ResponseEntity<>(userService.insert(user), HttpStatus.CREATED);
    }

    @GetMapping
    public void read() {


    }
}
