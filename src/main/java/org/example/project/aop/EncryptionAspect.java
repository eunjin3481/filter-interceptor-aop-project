package org.example.project.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.AfterReturning;
import org.example.project.util.AESUtil;
import org.example.project.vo.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import static org.example.project.util.AESUtil.decodeKey;

@Aspect
@Component
public class EncryptionAspect {

    @AfterReturning(pointcut = "execution(* com.example.project.service.UserService.read())", returning = "a")
    public void encryptAllUsers(User user) throws Exception {
        if (user != null) {
            SecretKey key = decodeKey("aaaaaaaaaaaaaaaaaaaaaaaaassaaaaa");
            String a = AESUtil.encrypt(user.toString(), key);
        }
    }


}

