package com.example.demo.mapper;

import com.example.demo.model.Credential;
import com.example.demo.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM notes WHERE noteid = #{noteId}")
    Notes getNote(int noteId);

    @Insert("INSERT INTO notes ( notetitle,notedescription,userid) VALUES(#{noteTitle}, #{noteDescription},#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNote(Notes note);

    @Select("SELECT * FROM notes WHERE userid = #{userId}")
    List<Notes> getAllNotes(int userId);

    @Update("UPDATE notes SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId}")
    void updateNote(Notes note);

    @Delete("DELETE FROM notes WHERE noteid = #{noteId}")
    Integer deleteNote(int noteId);
}
