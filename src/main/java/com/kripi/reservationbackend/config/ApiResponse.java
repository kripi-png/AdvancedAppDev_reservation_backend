package com.kripi.reservationbackend.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ApiResponse<T> {
    private final boolean success;
    private final String message;
    private final T data;

    public ApiResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
        this.message = null;
    }

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.data = null;
    }

}
