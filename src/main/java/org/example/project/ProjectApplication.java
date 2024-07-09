package org.example.project;

import org.example.project.util.AESUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.SecretKey;

@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) {

//        try {
//            SecretKey secretKey = AESUtil.generateKey();
//			String key = AESUtil.encodeKey(secretKey);
//			System.out.println(key);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        SpringApplication.run(ProjectApplication.class, args);
	}

}
