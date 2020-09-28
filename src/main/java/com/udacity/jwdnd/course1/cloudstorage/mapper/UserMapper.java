package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {

    @Results({
            @Result(property = "userid", column = "userid"),
            @Result(property = "username", column = "username"),
            @Result(property = "salt", column = "salt"),
            @Result(property = "password", column = "password"),
            @Result(property = "firstname", column = "firstname"),
            @Result(property = "lastName", column = "lastname")
    })
    @Select("SELECT * FROM USERS WHERE username = #{username}")
    public User getUser(String username);

    @Insert("INSERT INTO USERS(username,salt,password,firstname,lastname) VALUES(#{username},#{salt},#{password},#{firstname},#{lastname})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    public int addUser(User user);

    @Select("SELECT userid FROM USERS WHERE username = #{username}")
    int getUserIdByUsername(String username);

    @Update("UPDATE USERS SET username=#{username}, salt=#{salt}, password=#{password}, firstname=#{firstname}, lastname=#{lastname} WHERE userid=#{userid}")
    public void updateUser(User user);

    @Delete("DELETE FROM USERS WHERE userid = #{userid}")
    public void deleteUser(User user);
}