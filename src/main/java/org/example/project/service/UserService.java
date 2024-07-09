package org.example.project.service;

import org.example.project.vo.User;


public interface UserService {
    User insert(User newUser) throws Exception;
    User read(String userId) throws Exception;
}
