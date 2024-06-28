package org.example.userproject.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.userproject.vo.User;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class Controller {

    @GetMapping
    public void read() {
        log.info("read()");
    }

    @PostMapping
    public void singup(@RequestBody User user) {




    }


}
