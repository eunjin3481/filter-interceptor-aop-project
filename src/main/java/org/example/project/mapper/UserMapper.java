package org.example.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.project.vo.User;

@Mapper
public interface UserMapper {
    User insert(User newUser);
    User read(User user);
}
