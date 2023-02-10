package ru.gb.zaripov.api.exceptions;

public class AppError {
    private String code;
    private String message;

    public AppError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
