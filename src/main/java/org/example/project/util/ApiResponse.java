package org.example.project.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.project.vo.EnumResponseCode;
import org.springframework.http.HttpStatus;

/**
 * API 응답을 나타내는 클래스
 */
@Getter
@Setter
@ToString
public class ApiResponse {
    private int code;               // 응답 코드
    private HttpStatus httpStatus;  // 응답 HTTP Status
    private String message;         // 응답 메시지
    private Object data;            // 응답 데이터

    /**
     * EnumResponseCode와 데이터를 받아서 ApiResponse 객체를 생성
     *
     * @param responseCode 응답 코드와 메시지를 정의하는 EnumResponseCode 객체
     * @param data 응답에 포함될 데이터
     */
    public ApiResponse(EnumResponseCode responseCode, Object data) {
        this.code = responseCode.getCode();             // EnumResponseCode에서 코드를 가져옴
        this.httpStatus = responseCode.getHttpStatus(); // EnumResponseCode에서 httpstatus를 가져옴
        this.message = responseCode.getMessage();       // EnumResponseCode에서 메시지를 가져옴
        this.data = data;                               // 주어진 데이터를 설정
    }

    /**
     * EnumResponseCode를 받아서 ApiResponse 객체를 생성
     *
     * @param responseCode 응답 코드와 메시지를 정의하는 EnumResponseCode 객체
     */
    public ApiResponse(EnumResponseCode responseCode) {
        this.code = responseCode.getCode();             // EnumResponseCode에서 코드를 가져옴
        this.httpStatus = responseCode.getHttpStatus(); // EnumResponseCode에서 httpstatus를 가져옴
        this.message = responseCode.getMessage();       // EnumResponseCode에서 메시지를 가져옴
        this.data = null;                               // 데이터는 null로 설정
    }

}
