package org.example.project.util;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS(0, "성공"),
    MISSING_REQUIRED_FIELDS(1, "필수 필드 누락"),
    DUPLICATE_ID(2, "ID 중복"),
    VALIDATION_FAILURE(3, "유효성 검사 실패"),
    SERVER_ERROR(4, "서버 오류"),
    USER_NOT_FOUND(5, "사용자 없음"),
    INVALID_REQUEST(6, "잘못된 요청"),
    AUTHENTICATION_FAILURE(7, "인증 실패");

    private final int code;
    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
