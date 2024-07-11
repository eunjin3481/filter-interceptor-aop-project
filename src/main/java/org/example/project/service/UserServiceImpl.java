package org.example.project.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.project.mapper.UserMapper;
import org.example.project.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * FIXME 주석
 */
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserMapper userMapper;

    @Override
    public User insert(User newUser) throws Exception {
        userMapper.insert(newUser);
        return userMapper.read(newUser.getId());
    }

    @Override
    public User read(String userId) throws Exception {
        return userMapper.read(userId);
    }
}
