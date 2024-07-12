package org.example.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.example.project.service.UserService;
import org.example.project.util.ApiResponse;
import org.example.project.vo.EnumResponseCode;
import org.example.project.vo.User;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자 관련 API 엔드포인트를 제공하는 컨트롤러
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService; // UserService 주입

    /**
     * 사용자 추가 요청을 처리하는 메서드
     *
     * @param newUser 등록할 사용자 정보를 담은 User 객체
     * @return ApiResponse 객체로 감싼 등록된 사용자 정보
     * @throws Exception 등록 과정에서 예외가 발생할 경우
     */
    @PostMapping
    public ApiResponse signup(@RequestBody User newUser) throws Exception {
        log.info("-----controller signup()-----");
        User user = userService.insert(newUser); // UserService를 통해 사용자 등록
        return new ApiResponse(EnumResponseCode.SUCCESS, user); // ApiResponse를 생성하여 반환

    }

    /**
     * 사용자 조회 요청을 처리하는 메서드
     *
     * @param userId 조회할 사용자의 아이디
     * @return ApiResponse 객체로 감싼 조회된 사용자 정보
     * @throws Exception 조회 과정에서 예외가 발생할 경우
     */
    @GetMapping("/{userId}")
    public ApiResponse readUser(@PathVariable String userId) throws Exception {
        log.info("-----controller readUser()-----");
        User user = userService.read(userId); // UserService를 통해 사용자 조회
        // 사용자가 존재하지 않을 경우 NotFoundException 발생
        if (user == null) {
            throw new NotFoundException("User Not Found Exception");

        }
        return new ApiResponse(EnumResponseCode.SUCCESS, user); // ApiResponse를 생성하여 반환

    }
}
