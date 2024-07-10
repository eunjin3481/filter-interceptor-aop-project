package org.example.project.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.project.util.AESUtil;
import org.example.project.util.RereadableRequestWrapper;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Component
public class DecryptionFilter implements Filter {

    @Value("${secret.key}")
    private String privateKey;

    /**
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("-----doFilter start-----");

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // HttpServletRequest 감싸기
        RereadableRequestWrapper rereadableRequestWrapper = new RereadableRequestWrapper(httpRequest);
        if (HttpMethod.GET.name().equalsIgnoreCase(httpRequest.getMethod())) { // GET 요청의 경우
            // 암호화된 사용자 ID 읽기
            String requestURI = httpRequest.getRequestURI();
            String userId = requestURI.split("user/")[1];
            log.info("암호화된 사용자 ID: " + userId);
            // 사용자 ID 복호화
            String decryptedUserId = null;
            try {
                SecretKey secretKey = AESUtil.decodeKey(privateKey);
                decryptedUserId = AESUtil.decrypt(userId, secretKey);
                log.info("복호화된 사용자 ID: " + decryptedUserId);

            } catch (Exception e) {
                throw new RuntimeException(e);

            }

            // todo-chain.doFilter 사용하기
            String modifiedRequestURI = requestURI.replace(userId, decryptedUserId);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(modifiedRequestURI);
            requestDispatcher.forward(request, response);
            return;

        }
        if (HttpMethod.POST.name().equalsIgnoreCase(httpRequest.getMethod())) { // POST 요청의 경우
            // 암호화된 RequestBody 읽기
            String encryptedRequestBody = new String(rereadableRequestWrapper.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            log.info("암호화된 RequestBody: " + encryptedRequestBody);
            try {
                SecretKey secretKey = AESUtil.decodeKey(privateKey);
                String decryptedRequestBody = AESUtil.decrypt(encryptedRequestBody, secretKey);
                log.info("복호화된 RequestBody: " + decryptedRequestBody);

                // 새로 복호화된 RequestBody 설정
                rereadableRequestWrapper.updateRawData(decryptedRequestBody.getBytes(StandardCharsets.UTF_8));

            } catch (Exception e) {
                throw new RuntimeException(e); // todo-예외처리

            }
            // 필터 체인에 새로 만든 요청 전달
            chain.doFilter(rereadableRequestWrapper, response);
        }

        log.info("-----doFilter end-----");
    }

    @Override
    public void init(FilterConfig filterConfig){
        // 초기화 코드
    }

    @Override
    public void destroy() {
        // 소멸 코드
    }

}