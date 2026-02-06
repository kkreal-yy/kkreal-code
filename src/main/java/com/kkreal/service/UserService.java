package com.kkreal.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kkreal.entity.User;
import com.kkreal.mapper.UserMapper;
import com.kkreal.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;

    // Create - 创建用户
    public User createUser(User user) {
        String traceId = LogUtil.generateTraceId();
        MDC.put("traceId", traceId);
        
        try {
            log.info("开始创建用户，用户名: {}", user.getUsername());
            long startTime = System.currentTimeMillis();
            
            userMapper.insert(user);
            
            long duration = System.currentTimeMillis() - startTime;
            log.info("[PERFORMANCE] createUser took {} ms", duration);
            log.info("用户创建成功，用户ID: {}", user.getId());
            
            return user;
        } finally {
            MDC.remove("traceId");
        }
    }

    // Read - 根据ID查询用户
    public User getUserById(Long id) {
        String traceId = LogUtil.generateTraceId();
        MDC.put("traceId", traceId);
        
        try {
            log.info("开始查询用户，ID: {}", id);
            long startTime = System.currentTimeMillis();
            
            User user = userMapper.selectById(id);
            
            long duration = System.currentTimeMillis() - startTime;
            log.info("[PERFORMANCE] getUserById took {} ms", duration);
            
            if (user != null) {
                log.info("查询用户成功，用户名: {}", user.getUsername());
            } else {
                log.info("用户不存在，ID: {}", id);
            }
            
            return user;
        } finally {
            MDC.remove("traceId");
        }
    }

    // Read - 查询所有用户
    public List<User> getAllUsers() {
        String traceId = LogUtil.generateTraceId();
        MDC.put("traceId", traceId);
        
        try {
            log.info("开始查询所有用户");
            long startTime = System.currentTimeMillis();
            
            List<User> users = userMapper.selectList(null);
            
            long duration = System.currentTimeMillis() - startTime;
            log.info("[PERFORMANCE] getAllUsers took {} ms", duration);
            log.info("查询所有用户完成，总数: {}", users.size());
            
            return users;
        } finally {
            MDC.remove("traceId");
        }
    }

    // Read - 根据用户名查询
    public User getUserByUsername(String username) {
        String traceId = LogUtil.generateTraceId();
        MDC.put("traceId", traceId);
        
        try {
            log.info("开始根据用户名查询，用户名: {}", username);
            long startTime = System.currentTimeMillis();
            
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            User user = userMapper.selectOne(queryWrapper);
            
            long duration = System.currentTimeMillis() - startTime;
            log.info("[PERFORMANCE] getUserByUsername took {} ms", duration);
            
            if (user != null) {
                log.info("根据用户名查询成功，用户ID: {}", user.getId());
            } else {
                log.info("根据用户名未找到用户，用户名: {}", username);
            }
            
            return user;
        } finally {
            MDC.remove("traceId");
        }
    }

    // Read - 根据邮箱查询
    public User getUserByEmail(String email) {
        String traceId = LogUtil.generateTraceId();
        MDC.put("traceId", traceId);
        
        try {
            log.info("开始根据邮箱查询，邮箱: {}", email);
            long startTime = System.currentTimeMillis();
            
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("email", email);
            User user = userMapper.selectOne(queryWrapper);
            
            long duration = System.currentTimeMillis() - startTime;
            log.info("[PERFORMANCE] getUserByEmail took {} ms", duration);
            
            if (user != null) {
                log.info("根据邮箱查询成功，用户ID: {}", user.getId());
            } else {
                log.info("根据邮箱未找到用户，邮箱: {}", email);
            }
            
            return user;
        } finally {
            MDC.remove("traceId");
        }
    }

    // Read - 分页查询
    public Page<User> getUsersByPage(int pageNum, int pageSize) {
        String traceId = LogUtil.generateTraceId();
        MDC.put("traceId", traceId);
        
        try {
            log.info("开始分页查询用户，页码: {}，每页: {}", pageNum, pageSize);
            long startTime = System.currentTimeMillis();
            
            Page<User> page = new Page<>(pageNum, pageSize);
            Page<User> result = userMapper.selectPage(page, null);
            
            long duration = System.currentTimeMillis() - startTime;
            log.info("[PERFORMANCE] getUsersByPage took {} ms", duration);
            log.info("分页查询完成，总记录数: {}，总页数: {}", result.getTotal(), result.getPages());
            
            return result;
        } finally {
            MDC.remove("traceId");
        }
    }

    // Update - 更新用户
    public boolean updateUser(User user) {
        String traceId = LogUtil.generateTraceId();
        MDC.put("traceId", traceId);
        
        try {
            log.info("开始更新用户，ID: {}", user.getId());
            long startTime = System.currentTimeMillis();
            
            int result = userMapper.updateById(user);
            
            long duration = System.currentTimeMillis() - startTime;
            log.info("[PERFORMANCE] updateUser took {} ms", duration);
            
            if (result > 0) {
                log.info("用户更新成功，ID: {}", user.getId());
                return true;
            } else {
                log.info("用户更新失败，ID: {} 不存在", user.getId());
                return false;
            }
        } finally {
            MDC.remove("traceId");
        }
    }

    // Delete - 根据ID删除用户
    public boolean deleteUserById(Long id) {
        String traceId = LogUtil.generateTraceId();
        MDC.put("traceId", traceId);
        
        try {
            log.info("开始删除用户，ID: {}", id);
            long startTime = System.currentTimeMillis();
            
            int result = userMapper.deleteById(id);
            
            long duration = System.currentTimeMillis() - startTime;
            log.info("[PERFORMANCE] deleteUserById took {} ms", duration);
            
            if (result > 0) {
                log.info("用户删除成功，ID: {}", id);
                return true;
            } else {
                log.info("用户删除失败，ID: {} 不存在", id);
                return false;
            }
        } finally {
            MDC.remove("traceId");
        }
    }

    // Delete - 根据用户名删除用户
    public boolean deleteUserByUsername(String username) {
        String traceId = LogUtil.generateTraceId();
        MDC.put("traceId", traceId);
        
        try {
            log.info("开始根据用户名删除用户，用户名: {}", username);
            long startTime = System.currentTimeMillis();
            
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            int result = userMapper.delete(queryWrapper);
            
            long duration = System.currentTimeMillis() - startTime;
            log.info("[PERFORMANCE] deleteUserByUsername took {} ms", duration);
            
            if (result > 0) {
                log.info("根据用户名删除成功，用户名: {}，删除数量: {}", username, result);
                return true;
            } else {
                log.info("根据用户名删除失败，用户名: {} 不存在", username);
                return false;
            }
        } finally {
            MDC.remove("traceId");
        }
    }

    // 条件查询示例
    public List<User> getUsersByCondition(String username, String email, Integer status) {
        String traceId = LogUtil.generateTraceId();
        MDC.put("traceId", traceId);
        
        try {
            log.info("开始条件查询用户，用户名: {}，邮箱: {}，状态: {}", username, email, status);
            long startTime = System.currentTimeMillis();
            
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            if (username != null && !username.isEmpty()) {
                queryWrapper.like("username", username);
            }
            if (email != null && !email.isEmpty()) {
                queryWrapper.like("email", email);
            }
            if (status != null) {
                queryWrapper.eq("status", status);
            }
            List<User> users = userMapper.selectList(queryWrapper);
            
            long duration = System.currentTimeMillis() - startTime;
            log.info("[PERFORMANCE] getUsersByCondition took {} ms", duration);
            log.info("条件查询完成，总数: {}", users.size());
            
            return users;
        } finally {
            MDC.remove("traceId");
        }
    }
}
