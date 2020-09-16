package mapper;

import model.Note;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface NoteMapper {
    @Results({
            @Result(property = "noteId", column = "noteid"),
            @Result(property = "noteTitle", column = "notetitle"),
            @Result(property = "noteDescription", column = "notedescription"),
            @Result(property = "userId", column = "userid"),
    })
    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    public List<Note> getNotes(Integer userId);

    @Results({
            @Result(property = "noteId", column = "noteid"),
            @Result(property = "noteTitle", column = "notetitle"),
            @Result(property = "noteDescription", column = "notedescription"),
            @Result(property = "userId", column = "userid"),
    })
    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId}")
    public Note getNote(Integer noteId);

    @Insert("INSERT INTO NOTES(notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    public int addNote(Note note);

    @Update("UPDATE NOTES SET notetitle=#{noteTitle}, notedescription=#{noteDescription}, userid=#{userId} WHERE noteid=#{noteId}")
    public void editNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid=#{noteId}")
    public void deleteNote(Integer noteId);
}
