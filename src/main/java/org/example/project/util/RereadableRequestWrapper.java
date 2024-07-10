package org.example.project.util;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * HttpServletRequestWrapper를 확장하여 요청 본문을 여러 번 읽을 수 있도록 지원하는 클래스
 */
public class RereadableRequestWrapper extends HttpServletRequestWrapper {

    private final Charset encoding;
    private byte[] rawData;

    /**
     * HttpServletRequest를 감싸고 복호화된 요청 본문을 읽을 수 있도록 준비
     *
     * @param request 원본 HttpServletRequest
     * @throws IOException InputStream에서 데이터를 읽어오는 도중 발생할 수 있는 입출력 예외
     */
    public RereadableRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);

        // 문자 인코딩 설정
        String characterEncoding = request.getCharacterEncoding();
        if (StringUtils.isBlank(characterEncoding)) {
            characterEncoding = StandardCharsets.UTF_8.name();
        }
        this.encoding = Charset.forName(characterEncoding);

        // 원본 요청의 InputStream에서 데이터를 읽어 byte 배열로 저장
        try {
            InputStream inputStream = request.getInputStream();
            this.rawData = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 요청 본문의 데이터를 새로운 데이터로 업데이트
     *
     * @param newData 새로운 요청 본문 데이터
     */
    public void updateRawData(byte[] newData) {
        this.rawData = newData;
    }

    /**
     * ServletInputStream을 통해 요청 본문 데이터를 읽을 수 있도록 함
     *
     * @return ServletInputStream 객체
     * @throws IOException ServletInputStream에서 데이터를 읽어오는 도중 발생할 수 있는 입출력 예외
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.rawData);
        ServletInputStream servletInputStream = new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
        return servletInputStream;
    }

    /**
     * BufferedReader를 통해 요청 본문 데이터를 읽을 수 있도록 함
     *
     * @return BufferedReader 객체
     * @throws IOException BufferedReader에서 데이터를 읽어오는 도중 발생할 수 있는 입출력 예외
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream(), this.encoding));
    }

    /**
     * 원본 요청 객체를 반환합니다.
     *
     * @return 원본 HttpServletRequest 객체
     */
    @Override
    public ServletRequest getRequest() {
        return super.getRequest();
    }
}
