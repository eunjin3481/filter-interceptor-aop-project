package org.example.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.project.vo.User;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserMapper {
    void insert(User newUser);
    User read(String userId);
}
