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
    public ResponseEntity<?> signup(@RequestBody User user) {
        log.info("-----signup controller-----");
        log.info("-----추가할 user: -----" + user.toString());
        userService.insert(user);
//        return new ResponseEntity<>(userService.insert(user), HttpStatus.CREATED);
        return null;
    }


    @GetMapping("/{userId}")
    public ResponseEntity<?> read(@PathVariable String userId) {
        User user = userService.read(userId);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
