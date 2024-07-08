package org.example.project.service;

import org.example.project.mapper.UserMapper;
import org.example.project.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserMapper userMapper;

    @Override
    public User insert(User newUser) {
        userMapper.insert(newUser);
        return userMapper.read(newUser.getId());
    }

    @Override
    public User read(String userId) {
        return userMapper.read(userId);
    }
}
