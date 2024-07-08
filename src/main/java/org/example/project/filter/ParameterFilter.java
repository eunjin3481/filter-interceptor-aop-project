package org.example.project.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

//@Slf4j
//@Component
//public class ParameterFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        log.info("-----doFilter start-----");
//
//    }
//
//    private String getRequestBody(ContentCachingRequestWrapper requestWrapper) {
//        byte[] buf = requestWrapper.getContentAsByteArray();
//        if (buf.length > 0) {
//            return new String(buf, StandardCharsets.UTF_8);
//        }
//        return "";
//    }
//
//    private String decrypt(String encryptedData) {
//        // 여기에 복호화 로직을 구현
//        // 예시로 Base64 디코딩만 수행하는 예제
//        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
//        return new String(decodedBytes, StandardCharsets.UTF_8);
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        // 초기화 코드
//    }
//
//    @Override
//    public void destroy() {
//        // 소멸 코드
//    }
//
//}