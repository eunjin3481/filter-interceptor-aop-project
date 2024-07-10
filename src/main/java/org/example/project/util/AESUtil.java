package org.example.project.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * AESUtil 클래스는 AES 알고리즘을 사용하여 데이터를 암호화하고 복호화하는 유틸리티
 */
public class AESUtil {

    private static final String ALGORITHM = "AES"; //AES-256

    /**
     * AES 알고리즘을 사용하여 랜덤 키를 생
     *
     * @return 생성된 SecretKey 객체
     * @throws Exception 키 생성 시 발생할 수 있는 예외
     */
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(256); // 키 크기 설정 (256비트)
        return keyGenerator.generateKey();
    }

    /**
     * 주어진 SecretKey를 Base64 문자열로 인코딩
     *
     * @param key 인코딩할 SecretKey 객체
     * @return 인코딩된 SecretKey의 Base64 문자열
     */
    public static String encodeKey(SecretKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    /**
     * Base64로 인코딩된 문자열을 바이트 배열로 디코딩하여 SecretKey 객체로 복원
     *
     * @param encodedKey 인코딩된 SecretKey의 Base64 문자열
     * @return 복원된 SecretKey 객체
     */
    public static SecretKey decodeKey(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, ALGORITHM);
    }

    /**
     * 주어진 데이터를 주어진 SecretKey를 사용하여 AES 알고리즘으로 암호화
     *
     * @param data 암호화할 데이터 문자열
     * @param key  사용할 SecretKey 객체
     * @return 암호화된 데이터의 Base64 문자열
     * @throws Exception 암호화 실패 시 발생할 수 있는 예외
     */
    public static String encrypt(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);

    }

    /**
     * 주어진 암호화된 데이터를 주어진 SecretKey를 사용하여 AES 알고리즘으로 복호화
     *
     * @param encryptedData 암호화된 데이터의 Base64 문자열
     * @param key           사용할 SecretKey 객체
     * @return 복호화된 원본 데이터 문자열
     * @throws Exception 복호화 실패 시 발생할 수 있는 예외
     */
    public static String decrypt(String encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes);

    }
}