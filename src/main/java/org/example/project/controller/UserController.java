package org.example.project.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.project.service.UserService;
import org.example.project.util.ApiResponse;
import org.example.project.util.ResponseCode;
import org.example.project.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<?> signup(@RequestBody User newUser) {
        log.info("-----signup controller-----");
        HttpStatus code = null;
        ApiResponse response = new ApiResponse();

        if (newUser == null) {
            code = HttpStatus.BAD_REQUEST;
            response.setResponseCode(ResponseCode.MISSING_REQUIRED_FIELDS);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(ResponseCode.MISSING_REQUIRED_FIELDS));
        }
        log.info("추가할 user: " + newUser.toString());

        try {
            User user = userService.insert(newUser);
            code = HttpStatus.CREATED;
            response = new ApiResponse(ResponseCode.SUCCESS, user.toString());
//            return ResponseEntity.status(HttpStatus.CREATED)
//                    .body(new ApiResponse(ResponseCode.SUCCESS, user.toString()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse(ResponseCode.DUPLICATE_ID));
        }
        return ResponseEntity
                .status(code)
                .body(response);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> readUser(@PathVariable String userId) throws Exception {
        log.info("-----readUser controller-----");
        Object user = userService.read(userId);
        if (user != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(ResponseCode.USER_NOT_FOUND));
        }
    }
}
