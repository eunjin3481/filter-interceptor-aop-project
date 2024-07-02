package org.example.project.service;

import org.example.project.vo.User;

public interface UserService {
    User insert(User newUser);
    User read(User user);
}
