package org.example.project.vo;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * API 응답 코드를 정의한 Enum 클래스
 */
@Getter
public enum EnumResponseCode {
    SUCCESS(0, HttpStatus.OK, "성공"),
    CREATED(1, HttpStatus.CREATED, "생성"),
    MISSING_REQUIRED_FIELDS(2, HttpStatus.BAD_REQUEST, "필수 필드 누락"),
    DUPLICATE_ID(3, HttpStatus.BAD_REQUEST, "ID 중복"),
    VALIDATION_FAILURE(4, HttpStatus.BAD_REQUEST, "유효성 검사 실패"),
    SERVER_ERROR(5, HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류"),
    USER_NOT_FOUND(6, HttpStatus.NOT_FOUND, "사용자 없음"),
    BAD_REQUEST(7, HttpStatus.BAD_REQUEST, "잘못된 요청"),
    AUTHENTICATION_FAILURE(8, HttpStatus.UNAUTHORIZED, "인증 실패");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;

    /**
     * EnumResponseCode 객체를 생성
     *
     * @param code        응답 코드
     * @param httpStatus  HTTP 상태 코드
     * @param message     응답 메시지
     */
    EnumResponseCode(int code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

}
