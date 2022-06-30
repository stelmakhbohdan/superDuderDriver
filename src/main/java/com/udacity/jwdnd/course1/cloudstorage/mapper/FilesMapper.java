package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface FilesMapper {

    @Results({
            @Result(property = "fileId", column = "fileid"),
            @Result(property = "fileName", column = "filename"),
            @Result(property = "contentType", column = "contenttype"),
            @Result(property = "fileSize", column = "filesize"),
            @Result(property = "userId", column = "userid"),
            @Result(property = "fileData", column = "filedata")
    })
    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    public List<Files> getFiles(Integer userId);

    @Results({
            @Result(property = "fileId", column = "fileid"),
            @Result(property = "fileName", column = "filename"),
            @Result(property = "contentType", column = "contenttype"),
            @Result(property = "fileSize", column = "filesize"),
            @Result(property = "userId", column = "userid"),
            @Result(property = "fileData", column = "filedata")
    })
    @Select("SELECT * FROM FILES WHERE userid = #{userId} AND filename = #{fileName}")
    public Files getFile(Integer userId,String fileName);

    @Insert("INSERT INTO FILES(filename,contenttype,filesize,userid,filedata) VALUES(#{fileName},#{contentType},#{fileSize},#{userId},#{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    public int addFile(Files file);

    @Update("UPDATE FILES SET filename=#{fileName}, contenttype=#{contentType}, filesize=#{fileSize}, userid=#{userId}, filedata=#{fileData} WHERE fileid=#{fileId}")
    public void updateFile(Files file);

    @Delete("DELETE FROM FILES WHERE filename = #{fileName}")
    public void deleteFile(String fileName);
}
