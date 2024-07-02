package org.example.project.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.example.project.vo.User;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EncryptionAspect {

    @Pointcut("execution(* org.example.project.service.UserService.getUser*(..))")
    public void getUserMethod() {}

    @Before("getUserMethod()")
    public void beforeGetUser() {
        System.out.println("Before retrieving user...");
    }

    @AfterReturning(pointcut = "getUserMethod()", returning = "user")
    public void afterReturningGetUser(User user) {
        if (user != null) {
            // 사용자 정보 암호화
            encryptUserData(user);
        }
    }

    private void encryptUserData(User user) {
        // 사용자 정보 암호화 로직
//        user.setName(encrypt(user.getName()));
    }

    private String encrypt(String data) {
        // 실제 암호화 로직
        return data; // 예시로 단순 반환
    }
}

