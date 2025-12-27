package com.kkreal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Schema(description = "用户实体")
@TableName("user")
public class User {

    @Schema(description = "用户ID", example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户名", required = true, example = "zhangsan")
    @TableField("username")
    private String username;

    @Schema(description = "邮箱地址", required = true, example = "zhangsan@example.com")
    @TableField("email")
    private String email;

    @Schema(description = "手机号码", example = "13800138000")
    @TableField("phone")
    private String phone;

    @Schema(description = "年龄", example = "25")
    @TableField("age")
    private Integer age;

    @Schema(description = "状态：0-禁用，1-正常", example = "1")
    @TableField("status")
    private Integer status;

    @Schema(description = "创建时间")
    @TableField("created_at")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    @TableField("updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public User() {
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.status = 1;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
