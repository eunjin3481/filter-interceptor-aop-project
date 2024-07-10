package org.example.project.aop;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.example.project.util.AESUtil;
import org.example.project.util.ApiResponse;
import org.example.project.util.ResponseCode;
import org.example.project.vo.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.crypto.SecretKey;


import static org.example.project.util.AESUtil.decodeKey;

@Slf4j
@RestControllerAdvice
public class EncryptionAspect implements ResponseBodyAdvice<Object> {
    @Value("${secret.key}")
    private String privateKey;

    @Override
    public boolean supports(org.springframework.core.MethodParameter returnType,
                            Class converterType) {
        // 모든 클래스에 대해 처리하도록 설정
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        System.out.println(body);
        Gson gson = new Gson();
        String json = gson.toJson(body);

        log.info("json: {}", json);

        SecretKey key = decodeKey(privateKey);
        String encryptedUser = null;
        try {
            encryptedUser = AESUtil.encrypt(json, key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info("암호화된 사용자 정보:" + encryptedUser);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseCode.SUCCESS, encryptedUser));
    }
}