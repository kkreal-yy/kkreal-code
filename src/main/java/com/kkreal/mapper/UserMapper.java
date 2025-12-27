package com.kkreal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kkreal.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    // BaseMapper provides basic CRUD methods:
    // - insert(T entity)
    // - deleteById(Serializable id)
    // - updateById(T entity)
    // - selectById(Serializable id)
    // - selectList(Wrapper<T> queryWrapper)
    // - selectPage(Page<T> page, Wrapper<T> queryWrapper)
    // etc.
    
    // You can add custom methods here if needed
}
