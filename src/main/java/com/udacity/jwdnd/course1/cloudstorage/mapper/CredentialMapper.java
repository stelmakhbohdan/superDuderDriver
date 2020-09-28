package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CredentialMapper {

    @Results({
            @Result(property = "credentialid",column = "credentialid"),
            @Result(property = "url",column = "url"),
            @Result(property = "username",column = "username"),
            @Result(property = "key",column = "key"),
            @Result(property = "password",column = "password"),
            @Result(property = "userid",column = "userid")
    })
    @Select("SELECT * FROM CREDENTIALS WHERE userid=#{userid}")
    public List<Credential> getCredentials(Integer userId);

    @Results({
            @Result(property = "credentialid",column = "credentialid"),
            @Result(property = "url",column = "url"),
            @Result(property = "username",column = "username"),
            @Result(property = "key",column = "key"),
            @Result(property = "password",column = "password"),
            @Result(property = "userid",column = "userid")
    })

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid=#{credentialid}")
    public Credential getCredential(Integer credentialid);

    @Update("UPDATE CREDENTIALS SET credentialid=#{credentialid},url=#{url},username=#{username},key=#{key},password=3{password},userid=#{userid}")
    public void updateCredentials(Credential credential);

    @Insert("INSERT INTO CREDENTIALS(credentialid,url,username,key,password,userid) VALUES(#{credentiald},#{url},#{username},#{key},#{password},#{userid})")
    @Options(useGeneratedKeys = true,keyProperty = "credentialid")
    public int addCredentials(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid=#{credentialid}")
    public void deleteCredential(Integer credentialid);

}
