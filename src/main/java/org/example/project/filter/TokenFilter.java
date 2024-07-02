package org.example.project.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TokenFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 헤더에서 토큰 확인
        String authToken = httpRequest.getHeader("Authorization");
        if (authToken != null && !authToken.isEmpty()) {
            // 복호화
            String decryptedToken = decryptToken(authToken);
            // 요청에 복호화된 토큰 추가
            httpRequest.setAttribute("decryptedToken", decryptedToken);
        }

        chain.doFilter(request, response);
    }

    private String decryptToken(String token) {
        // 토큰 복호화 로직
        return token;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 초기화 코드
    }

    @Override
    public void destroy() {
        // 소멸 코드
    }

}