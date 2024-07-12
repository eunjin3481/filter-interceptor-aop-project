package org.example.project.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 사용자 정보를 담는 클래스
 */
@Getter
@Setter
@ToString
public class User {
    String id;
    String pw;
    String name;
}
