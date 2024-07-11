package org.example.project.util;

import lombok.Getter;
import lombok.Setter;
import org.example.project.vo.EnumResponseCode;

//@NoArgsConstructor
@Getter
@Setter
public class ApiResponse {
    private int code;
    private String message;
    private String data;

    public ApiResponse(EnumResponseCode responseCode, String data) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.data = data;
    }

    public ApiResponse(EnumResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.data = null;
    }

}
