package org.example.project.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.apache.ibatis.javassist.NotFoundException;
import org.example.project.util.ApiResponse;
import org.example.project.vo.EnumResponseCode;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 예외처리 하는 Aspect
 * @Order(1): RestControllerAdvice의 순서를 첫 번째로 지정
 */
@Slf4j
//@Order(1)
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * NotFoundException 예외 처리 메소드
     * @param ex 발생한 NotFoundException 객체
     * @return HTTP 404 상태 코드와 함께 사용자를 찾을 수 없음을 나타내는 ApiResponse를 반환
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex) {
        log.info("Failed to find user.", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(EnumResponseCode.USER_NOT_FOUND));

    }

    /**
     * BadRequestException 예외 처리 메소드
     * @param ex 발생한 BadRequestException 객체
     * @return HTTP 400 상태 코드와 함께 잘못된 요청을 나타내는 ApiResponse를 반환
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex) {
        log.info("{}",ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(EnumResponseCode.BAD_REQUEST));

    }

    /**
     * DataIntegrityViolationException 예외 처리 메소드
     * @param ex 발생한 DataIntegrityViolationException 객체
     * @return HTTP 400 상태 코드와 함께 서버 오류를 나타내는 ApiResponse를 반환
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.error("User ID duplicated", ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(EnumResponseCode.DUPLICATE_ID));

    }

    /**
     * Exception 예외 처리 메소드
     * @param ex 발생한 Exception 객체
     * @return HTTP 500 상태 코드와 함께 서버 오류를 나타내는 ApiResponse를 반환
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleServerErrorException(Exception ex) {
        log.info("{}",ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(EnumResponseCode.SERVER_ERROR));

    }
}
