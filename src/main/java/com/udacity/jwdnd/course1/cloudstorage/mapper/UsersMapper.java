package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UsersMapper {

    @Results({
            @Result(property = "userId", column = "userid"),
            @Result(property = "userName", column = "username"),
            @Result(property = "salt", column = "salt"),
            @Result(property = "password", column = "password"),
            @Result(property = "firsName", column = "firsname"),
            @Result(property = "lastName", column = "lastname")
    })
    @Select("SELECT * FROM USERS WHERE username = #{userName}")
    public Users getUser(String userName);

    @Insert("INSERT INTO USERS(username,salt,password,firstname,lastname) VALUES(#{userName},#{salt},#{password},#{firstName},#{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    public int addUser(Users user);

    @Update("UPDATE USERS SET username=#{userName}, salt=#{salt}, password=#{password}, firstname=#{firstName}, lastname=#{lastName} WHERE userid=#{userId}")
    public void updateUser(Users user);

    @Delete("DELETE FROM USERS WHERE userid = #{userId}")
    public void deleteUser(Users user);
}