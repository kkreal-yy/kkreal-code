package com.kkreal.auth.dto;

import lombok.Data;

/**
 * 登录响应DTO
 */
@Data
public class LoginResponse {
    private String token;
    private String refreshToken;
    private Long userId;
    private String username;
    private Long expireTime;

    public LoginResponse(String token, String refreshToken, Long userId, String username, Long expireTime) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.username = username;
        this.expireTime = expireTime;
    }
}
