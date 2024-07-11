package org.example.project.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.example.project.util.AESUtil;
import org.example.project.util.RereadableRequestWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 요청을 복호화하는 필터
 */
@Slf4j
@WebFilter(urlPatterns = "/user/*")
public class DecryptionFilter implements Filter {

    // todo - @Configuration을 통해 filter 등록시 privateKey(=null)에 값이 주입이 안됨
    @Value("${secret.key}")
    private String privateKey;

    /**
     * 요청을 필터링하여 복호화 작업을 수행
     *
     * @param request  필터링할 요청
     * @param response 응답
     * @param chain    필터 체인
     * @throws IOException      입출력 예외 발생 시
     * @throws ServletException 서블릿 예외 발생 시
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("-----doFilter start-----");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        System.out.println(httpRequest.getRequestURI());
        RereadableRequestWrapper rereadableRequestWrapper = new RereadableRequestWrapper(httpRequest);
        String httpMethodStr = httpRequest.getMethod();
        // GET 요청일 경우 사용자 ID 복호화
        if (HttpMethod.GET.name().equalsIgnoreCase(httpMethodStr) || HttpMethod.DELETE.name().equalsIgnoreCase(httpMethodStr)) {
            String requestURI = httpRequest.getRequestURI();
            String[] parts = requestURI.split("user/");
            // URI 형식이 잘못된 경우 BadRequestException 발생
            //todo 예외처리 확인해보기
            if (parts.length < 2) {
                throw new BadRequestException("Bad Request Exception");

            }
            String userId = parts[1];
            log.info("[Filter] 복호화 전 사용자 ID: " + userId);
            // 사용자 ID 복호화
            String decryptedUserId = null;
            SecretKey secretKey = AESUtil.decodeKey(privateKey);
            try {
                decryptedUserId = AESUtil.decrypt(userId, secretKey);

            } catch (Exception e) {
                throw new RuntimeException(e);

            }
            log.info("[Filter] 복호화 후 사용자 ID: " + decryptedUserId);
            // 복호화된 사용자 ID로 수정된 URI 생성
            String modifiedRequestURI = requestURI.replace(userId, decryptedUserId);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(modifiedRequestURI);
            requestDispatcher.forward(request, response);

        } else if (HttpMethod.POST.name().equalsIgnoreCase(httpMethodStr) || HttpMethod.PUT.name().equalsIgnoreCase(httpMethodStr)) {
            // POST 요청일 경우 RequestBody 복호화
            byte[] encryptedBytes = rereadableRequestWrapper.getInputStream().readAllBytes();

            // 요청 body가 비어 있는 경우 BadRequestException 발생
            if (encryptedBytes.length == 0) {
                throw new BadRequestException("Bad Request Exception");
            }

            String encryptedRequestBody = new String(encryptedBytes, StandardCharsets.UTF_8);
            log.info("[Filter] 복호화 전 사용자 정보: " + encryptedRequestBody);

            // RequestBody 복호화
            String decryptedRequestBody = null;
            SecretKey secretKey = AESUtil.decodeKey(privateKey);
            try {
                decryptedRequestBody = AESUtil.decrypt(encryptedRequestBody, secretKey);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            log.info("[Filter] 복호화 후 사용자 정보: " + decryptedRequestBody);

            // 복호화된 RequestBody 설정
            rereadableRequestWrapper.updateRawData(decryptedRequestBody.getBytes(StandardCharsets.UTF_8));
            // 필터 체인에 새로 만든 요청 전달
            chain.doFilter(rereadableRequestWrapper, response);

        }
        log.info("-----doFilter end-----");
    }

    @Override
    public void init(FilterConfig filterConfig) {
        // 필터 초기화 코드
        log.info("-----init-start-----");
    }

    @Override
    public void destroy() {
        // 필터 소멸 코드
    }
}
