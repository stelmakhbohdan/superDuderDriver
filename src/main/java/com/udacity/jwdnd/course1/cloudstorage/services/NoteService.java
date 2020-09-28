package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;
    private UserMapper userMapper;

    public NoteService(NoteMapper noteMapper, UserMapper userMapper) {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
    }

    public Note getNote(Integer noteId){
        return noteMapper.getNote(noteId);
    }

    public List<Note> getNotes(int userid){
        return noteMapper.getNotes(userid);
    }

    public void editNote(NoteForm noteModalForm, String username) {
        Note selectedNote = new Note(Integer.parseInt(noteModalForm.getNoteid()),
                noteModalForm.getNotetitle(), noteModalForm.getNotedescription(),
                userMapper.getUserIdByUsername(username));
        noteMapper.editNote(selectedNote);
    }

    public void addNote(NoteForm noteForm,String username){
        Note newNote = new Note(null, noteForm.getNotetitle(),
                noteForm.getNotedescription(), userMapper.getUserIdByUsername(username));
        noteMapper.addNote(newNote);
    }

    public void deleteNote(Integer noteId){
        noteMapper.deleteNote(noteId);
    }
}
