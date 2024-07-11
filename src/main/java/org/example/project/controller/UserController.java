package org.example.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.example.project.service.UserService;
import org.example.project.vo.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * FIXME 주석
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    // todo - 생성자 주입 애노테이션 찾아보기
    private final UserService userService;

    /**
     * 사용자 추가 요청을 처리하는 메소드
     * @param newUser 등록할 사용자 정보
     * @return 등록된 사용자 정보
     * @throws Exception 예외 발생 시
     */
    @PostMapping
    public User signup(@RequestBody User newUser) throws Exception {
        log.info("-----controller signup()-----");
        User user = userService.insert(newUser);
        return user;

    }

    /**
     * 사용자 조회 요청을 처리하는 메소드
     * @param userId 조회할 사용자의 아이디
     * @return 조회된 사용자 정보
     * @throws Exception 예외 발생 시
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> readUser(@PathVariable String userId) throws Exception {
        log.info("-----controller readUser()-----");
        User user = userService.read(userId);

        // 사용자가 존재하지 않을 경우 NotFoundException을 발생
        if (user == null) {
            throw new NotFoundException("User Not Found Exception");

        }
        return ResponseEntity.status(HttpStatus.OK).body(user);

    }
}
