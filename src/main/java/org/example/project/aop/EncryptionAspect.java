package org.example.project.aop;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.example.project.util.AESUtil;
import org.example.project.vo.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;


import static org.example.project.util.AESUtil.decodeKey;

@Slf4j
@Aspect
@Component
public class EncryptionAspect {

    // todo-servie말고 controller 다음에서 advice 적용하기, 응답전에 adivce 적용하는법 찾아보기
    @AfterReturning(value = "execution(* org.example.project.service.UserServiceImpl.read(..))", returning = "user")
    public User encryptAllUsers(Object user) throws Throwable {
        log.info("-----Advice start-----");

        Gson gson = new Gson();
        String json = gson.toJson(user);

        log.info("json: {}", json);

        if (user != null) {

            SecretKey key = decodeKey("aaaaaaaaaaaaaaaaaaaaaaaaassaaaaa");
            String encryptedUser = AESUtil.encrypt(json, key);
            log.info("암호화된 사용자 정보:" + encryptedUser);

//            log.info("-----Advice end-----");
        }
        log.info("-----Advice end-----");

        return null;
    }
}

