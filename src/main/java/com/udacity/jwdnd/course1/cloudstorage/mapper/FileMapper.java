package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface FileMapper {
    @Results({
            @Result(property = "fileId", column = "fileId"),
            @Result(property = "filename", column = "filename"),
            @Result(property = "contenttype", column = "colunmtype"),
            @Result(property = "filesize", column = "filesize"),
            @Result(property = "userid", column = "userid"),
            @Result(property = "filedate", column = "filedate")
    })
    @Select("SELECT * FROM FILES WHERE userid=#{userid}")
    public List<File> getFiles(Integer userId);

    @Results({
            @Result(property = "fileId", column = "fileId"),
            @Result(property = "filename", column = "filename"),
            @Result(property = "contenttype", column = "colunmtype"),
            @Result(property = "filesize", column = "filesize"),
            @Result(property = "userid", column = "userid"),
            @Result(property = "filedate", column = "filedate")
    })
    @Select("SELECT * FROM FILES WHERE fileId=#{fileId}")
    public File getFile(Integer fileid);

    @Insert("INSERT INTO FILES (filename,contenttype,filesize,userid,filedata) VALUES(#{filename},#{contenttype},#{filesize},#{userid},#filedata)")
    @Options(useGeneratedKeys = true , keyProperty = "fileId")
    public int addFile(File file);

    @Update("UPDATE FILES SET filename=#{filename}, contenttype=#{contenttype},filesize=#{filesize},userid=#{userid},filedata=#{filedata} WHERE fileId=#{fileId}")
    public int updateFile(File file);

    @Delete("DELETE FROM FILES WHERE fileId=#{fileId}")
    public void deleteFiles(int fileid);

    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    File getFilebyFilename(String fileName);
}

