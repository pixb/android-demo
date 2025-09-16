package com.example.demo.controller.service.impl;

import com.example.demo.controller.mapper.UserMapper;
import com.example.demo.controller.model.User;
import com.example.demo.controller.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User selectUserById(Integer id) {
        return userMapper.selectUserById(id);
    }
}
