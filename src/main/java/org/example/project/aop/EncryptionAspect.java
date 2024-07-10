package org.example.project.aop;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.example.project.util.AESUtil;
import org.example.project.util.ApiResponse;
import org.example.project.util.ResponseCode;
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

/**
 * Controller에서 반환되는 응답 데이터를 암호화하는 Aspect
 */
@Slf4j
@RestControllerAdvice
public class EncryptionAspect implements ResponseBodyAdvice<Object> {

    @Value("${secret.key}")
    private String privateKey;  // application.properties에서 설정한 암호화 키

    /**
     * 특정 클래스의 반환값에 대해서만 이 Aspect가 동작하도록 설정
     * ResponseEntity를 반환하는 경우에는 처리하지 않음
     *
     * @param returnType    메소드의 반환 타입
     * @param converterType 메시지 컨버터의 타입
     * @return              처리 여부 (true: 처리, false: 미처리)
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.getParameterType().equals(ResponseEntity.class);
    }

    /**
     * Controller의 메소드가 반환하는 데이터를 암호화하는 메소드
     *
     * @param body                    Controller에서 반환된 객체
     * @param returnType              메소드의 반환 타입
     * @param selectedContentType     선택된 컨텐츠 타입
     * @param selectedConverterType   선택된 컨버터 타입
     * @param request                 HTTP 요청 객체
     * @param response                HTTP 응답 객체
     * @return                        암호화된 응답 데이터
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        log.info("-----Advice Encryption-----");

        // 객체를 JSON 문자열로 변환
        Gson gson = new Gson();
        String json = gson.toJson(body);

        // 설정된 암호화 키를 사용하여 데이터를 암호화
        SecretKey key = decodeKey(privateKey);
        String encryptedData = null;
        try {
            encryptedData = AESUtil.encrypt(json, key);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }

        // 암호화된 데이터를 ApiResponse 형태로 반환
        log.info("[Advice] Encrypted data: " + encryptedData);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseCode.SUCCESS, encryptedData));
    }
}
