package com.kkreal.auth.controller;

import com.kkreal.auth.dto.LoginRequest;
import com.kkreal.auth.dto.LoginResponse;
import com.kkreal.auth.util.JwtUtil;
import com.kkreal.common.Result;
import com.kkreal.entity.User;
import com.kkreal.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 */
@Tag(name = "认证管理", description = "用户登录、注册、token刷新等接口")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${jwt.expiration:3600}")
    private Long expiration;

    /**
     * 用户登录
     */
    @Operation(summary = "用户登录", description = "用户使用用户名和密码登录")
    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        // 查询用户
        User user = userService.getUserByUsername(loginRequest.getUsername());
        if (user == null) {
            return Result.error("用户不存在");
        }

        // 这里应该验证密码，为了演示先跳过密码验证
        // 实际应用中需要比较加密后的密码
        if (!isValidPassword(user, loginRequest.getPassword())) {
            return Result.error("密码错误");
        }

        // 生成JWT token
        String token = jwtUtil.generateToken(user.getUsername());
        String refreshToken = jwtUtil.generateToken(user.getUsername() + "_refresh");

        LoginResponse response = new LoginResponse(
                token,
                refreshToken,
                user.getId(),
                user.getUsername(),
                System.currentTimeMillis() + expiration * 1000
        );

        return Result.success("登录成功", response);
    }

    /**
     * 用户注册
     */
    @Operation(summary = "用户注册", description = "新用户注册")
    @PostMapping("/register")
    public Result<User> register(@RequestBody User user) {
        // 检查用户是否已存在
        User existingUser = userService.getUserByUsername(user.getUsername());
        if (existingUser != null) {
            return Result.error("用户名已存在");
        }

        // 保存用户（实际应用中需要加密密码）
        User savedUser = userService.createUser(user);
        return Result.success("注册成功", savedUser);
    }

    /**
     * 刷新Token
     */
    @Operation(summary = "刷新Token", description = "使用刷新Token获取新的访问Token")
    @PostMapping("/refresh")
    public Result<String> refresh(@RequestHeader("Refresh-Token") String refreshToken) {
        if (jwtUtil.validateToken(refreshToken, refreshToken)) {
            // 从刷新Token中提取用户名（实际应用中可能需要不同的逻辑）
            String username = jwtUtil.getUsernameFromToken(refreshToken);
            String newToken = jwtUtil.generateToken(username);
            return Result.success("Token刷新成功", newToken);
        } else {
            return Result.error("无效的刷新Token");
        }
    }

    /**
     * 模拟密码验证
     */
    private boolean isValidPassword(User user, String inputPassword) {
        // 实际应用中应该比较加密后的密码
        // 这里为了演示返回true，实际应该使用BCrypt等进行密码比较
        return true;
    }
}
