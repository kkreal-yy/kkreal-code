package com.kkreal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kkreal.common.Result;
import com.kkreal.entity.User;
import com.kkreal.exception.BusinessException;
import com.kkreal.service.UserService;
import com.kkreal.util.LogUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制器
 */
@Tag(name = "用户管理", description = "用户相关的增删改查接口")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LogUtil.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 创建用户
     */
    @Operation(summary = "创建用户", description = "创建一个新用户")
    @PostMapping
    public Result<User> createUser(@RequestBody User user) {
        try {
            LogUtil.logBusinessInfo(logger, "开始创建用户，用户名: {}", user.getUsername());
            
            if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
                LogUtil.logBusinessError(logger, "创建用户失败，用户名为空");
                throw new BusinessException("用户名不能为空");
            }
            if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                LogUtil.logBusinessError(logger, "创建用户失败，邮箱为空");
                throw new BusinessException("邮箱不能为空");
            }
            User createdUser = userService.createUser(user);
            LogUtil.logBusinessInfo(logger, "用户创建成功，ID: {}", createdUser.getId());
            return Result.success("用户创建成功", createdUser);
        } catch (Exception e) {
            LogUtil.logBusinessError(logger, "创建用户异常，用户名: {}，错误: {}", user.getUsername(), e.getMessage());
            throw e;
        }
    }

    /**
     * 查询所有用户
     */
    @Operation(summary = "查询所有用户", description = "获取系统中所有用户列表")
    @GetMapping
    public Result<List<User>> getAllUsers() {
        try {
            LogUtil.logBusinessInfo(logger, "开始查询所有用户");
            List<User> users = userService.getAllUsers();
            LogUtil.logBusinessInfo(logger, "查询所有用户完成，总数: {}", users.size());
            return Result.success(users);
        } catch (Exception e) {
            LogUtil.logBusinessError(logger, "查询所有用户异常，错误: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * 根据ID查询用户
     */
    @Operation(summary = "根据ID查询用户", description = "通过用户ID获取用户详细信息")
    @GetMapping("/{id}")
    public Result<User> getUserById(
            @Parameter(description = "用户ID", required = true) @PathVariable Long id) {
        try {
            LogUtil.logBusinessInfo(logger, "开始查询用户，ID: {}", id);
            User user = userService.getUserById(id);
            if (user == null) {
                LogUtil.logBusinessInfo(logger, "用户不存在，ID: {}", id);
                throw new BusinessException("用户不存在");
            }
            LogUtil.logBusinessInfo(logger, "查询用户成功，用户名: {}", user.getUsername());
            return Result.success(user);
        } catch (Exception e) {
            LogUtil.logBusinessError(logger, "查询用户异常，ID: {}，错误: {}", id, e.getMessage());
            throw e;
        }
    }

    /**
     * 根据用户名查询用户
     */
    @Operation(summary = "根据用户名查询用户", description = "通过用户名获取用户详细信息")
    @GetMapping("/username/{username}")
    public Result<User> getUserByUsername(
            @Parameter(description = "用户名", required = true) @PathVariable String username) {
        try {
            LogUtil.logBusinessInfo(logger, "开始根据用户名查询，用户名: {}", username);
            User user = userService.getUserByUsername(username);
            if (user == null) {
                LogUtil.logBusinessInfo(logger, "用户不存在，用户名: {}", username);
                throw new BusinessException("用户不存在");
            }
            LogUtil.logBusinessInfo(logger, "根据用户名查询成功，ID: {}", user.getId());
            return Result.success(user);
        } catch (Exception e) {
            LogUtil.logBusinessError(logger, "根据用户名查询异常，用户名: {}，错误: {}", username, e.getMessage());
            throw e;
        }
    }

    /**
     * 分页查询用户
     */
    @Operation(summary = "分页查询用户", description = "分页获取用户列表")
    @GetMapping("/page")
    public Result<Page<User>> getUsersByPage(
            @Parameter(description = "页码", example = "1") @RequestParam(defaultValue = "1") int pageNum,
            @Parameter(description = "每页数量", example = "10") @RequestParam(defaultValue = "10") int pageSize) {
        try {
            LogUtil.logBusinessInfo(logger, "开始分页查询用户，页码: {}，每页: {}", pageNum, pageSize);
            Page<User> page = userService.getUsersByPage(pageNum, pageSize);
            LogUtil.logBusinessInfo(logger, "分页查询完成，总记录数: {}，总页数: {}", page.getTotal(), page.getPages());
            return Result.success(page);
        } catch (Exception e) {
            LogUtil.logBusinessError(logger, "分页查询异常，页码: {}，每页: {}，错误: {}", pageNum, pageSize, e.getMessage());
            throw e;
        }
    }

    /**
     * 条件查询用户
     */
    @Operation(summary = "条件查询用户", description = "根据用户名、邮箱、状态等条件查询用户")
    @GetMapping("/search")
    public Result<List<User>> searchUsers(
            @Parameter(description = "用户名（模糊查询）") @RequestParam(required = false) String username,
            @Parameter(description = "邮箱（模糊查询）") @RequestParam(required = false) String email,
            @Parameter(description = "状态：0-禁用，1-正常") @RequestParam(required = false) Integer status) {
        try {
            LogUtil.logBusinessInfo(logger, "开始条件查询用户，用户名: {}，邮箱: {}，状态: {}", username, email, status);
            List<User> users = userService.getUsersByCondition(username, email, status);
            LogUtil.logBusinessInfo(logger, "条件查询完成，总数: {}", users.size());
            return Result.success(users);
        } catch (Exception e) {
            LogUtil.logBusinessError(logger, "条件查询异常，用户名: {}，邮箱: {}，状态: {}，错误: {}", 
                    username, email, status, e.getMessage());
            throw e;
        }
    }

    /**
     * 更新用户
     */
    @Operation(summary = "更新用户", description = "更新用户信息")
    @PutMapping("/{id}")
    public Result<User> updateUser(
            @Parameter(description = "用户ID", required = true) @PathVariable Long id,
            @RequestBody User user) {
        try {
            LogUtil.logBusinessInfo(logger, "开始更新用户，ID: {}", id);
            user.setId(id);
            boolean success = userService.updateUser(user);
            if (!success) {
                LogUtil.logBusinessInfo(logger, "用户更新失败，用户不存在，ID: {}", id);
                throw new BusinessException("用户更新失败或用户不存在");
            }
            User updatedUser = userService.getUserById(id);
            LogUtil.logBusinessInfo(logger, "用户更新成功，ID: {}", id);
            return Result.success("用户更新成功", updatedUser);
        } catch (Exception e) {
            LogUtil.logBusinessError(logger, "更新用户异常，ID: {}，错误: {}", id, e.getMessage());
            throw e;
        }
    }

    /**
     * 删除用户
     */
    @Operation(summary = "删除用户", description = "根据ID删除用户")
    @DeleteMapping("/{id}")
    public Result<Void> deleteUser(
            @Parameter(description = "用户ID", required = true) @PathVariable Long id) {
        try {
            LogUtil.logBusinessInfo(logger, "开始删除用户，ID: {}", id);
            boolean success = userService.deleteUserById(id);
            if (!success) {
                LogUtil.logBusinessInfo(logger, "用户删除失败，用户不存在，ID: {}", id);
                throw new BusinessException("用户删除失败或用户不存在");
            }
            LogUtil.logBusinessInfo(logger, "用户删除成功，ID: {}", id);
            return Result.success("用户删除成功", null);
        } catch (Exception e) {
            LogUtil.logBusinessError(logger, "删除用户异常，ID: {}，错误: {}", id, e.getMessage());
            throw e;
        }
    }
}
