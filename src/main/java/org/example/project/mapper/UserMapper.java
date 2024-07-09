package org.example.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.project.vo.User;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Mapper
public interface UserMapper {
    void insert(User newUser) throws SQLException;
    User read(String userId) throws SQLException;
}
