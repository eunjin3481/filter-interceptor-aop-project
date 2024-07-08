package org.example.project.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.project.util.AESUtil;
import org.example.project.util.RereadableRequestWrapper;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class DecryptionFilter implements Filter {

//    @Value("${secret.key}")
    private String secretKey = "aaaaaaaaaaaaaaaaaaaaaaaaassaaaaa";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("-----doFilter start-----");

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // HttpServletRequest 감싸기
        RereadableRequestWrapper rereadableRequestWrapper = new RereadableRequestWrapper(httpRequest);

        // GET 요청의 경우
        String pathInfo = httpRequest.getPathInfo();
        System.out.println(pathInfo);
        if (pathInfo != null) {
            log.info("변수화된 경로: " + pathInfo);

            // 복호화 로직 추가
            try {
                SecretKey key = AESUtil.decodeKey(secretKey);
                String decryptedPath = AESUtil.decrypt(pathInfo.substring(1), key);

                log.info("복호화된 경로: " + decryptedPath);

                // 복호화된 경로로 새 경로 구성
                String newUrl = httpRequest.getContextPath() + "/" + decryptedPath;

                return;
            } catch (Exception e) {
                throw new RuntimeException("Failed to decrypt path", e);
            }
        }

        // POST 요청의 경우
        if (request instanceof HttpServletRequest && "POST".equalsIgnoreCase(httpRequest.getMethod())) {
            // 암호화된 RequestBody 읽기
            String encryptedRequestBody = new String(rereadableRequestWrapper.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            log.info("암호화된 RequestBody: " + encryptedRequestBody);

            try {
                SecretKey key = AESUtil.decodeKey(secretKey);
                String decryptedRequestBody = AESUtil.decrypt(encryptedRequestBody, key);
                log.info("복호화된 RequestBody: " + decryptedRequestBody);

                // 새로 복호화된 RequestBody 설정
                rereadableRequestWrapper.updateRawData(decryptedRequestBody.getBytes(StandardCharsets.UTF_8));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        // 필터 체인에 새로 만든 요청 전달
        chain.doFilter(rereadableRequestWrapper, response);

        log.info("-----doFilter end-----");
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