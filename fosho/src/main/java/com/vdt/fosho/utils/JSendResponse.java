package com.vdt.fosho.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JSendResponse<T>  {

    private String status;
    private String message;
    private T data;

    public JSendResponse(String status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> JSendResponse<T> success(T data) {
        return new JSendResponse<>("success", data, null);
    }

    public static <T> JSendResponse<T> fail(T data) {
        return new JSendResponse<>("fail", data, null);
    }

    public static JSendResponse<Object> error(String message) {
        return new JSendResponse<>("error", null, message);
    }
}
