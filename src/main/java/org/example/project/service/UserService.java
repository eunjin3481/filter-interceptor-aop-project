package org.example.project.service;

import org.example.project.vo.User;

/**
 * 사용자(User) 서비스 인터페이스
 */
public interface UserService {

    /**
     * 새로운 사용자를 추가
     *
     * @param newUser 추가할 사용자 정보를 담고 있는 User 객체
     * @return 추가된 사용자 정보를 포함한 User 객체를 반환
     * @throws Exception 추가 과정에서 오류가 발생한 경우 예외가 던짐
     */
    User insert(User newUser) throws Exception;

    /**
     * 주어진 userId에 해당하는 사용자 정보를 조회
     *
     * @param userId 조회할 사용자의 고유 식별자
     * @return userId에 해당하는 사용자 정보를 담고 있는 User 객체를 반환
     * @throws Exception 조회 과정에서 오류가 발생한 경우 예외가 던짐
     */
    User read(String userId) throws Exception;
}
