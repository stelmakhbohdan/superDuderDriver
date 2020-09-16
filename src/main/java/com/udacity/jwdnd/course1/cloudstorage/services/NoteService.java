package com.udacity.jwdnd.course1.cloudstorage.services;

import mapper.NoteMapper;
import model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;
    private AuthenticatedUserService authenticatedUserService;

    public NoteService(NoteMapper noteMapper, AuthenticatedUserService authenticatedUserService) {
        this.noteMapper = noteMapper;
        this.authenticatedUserService = authenticatedUserService;
    }

    public Note getNote(Integer noteId){
        return noteMapper.getNote(noteId);
    }
    public List<Note> getNotes(){
        return noteMapper.getNotes(authenticatedUserService.getLoggedInUserId());
    }

    public void editNotes(Note notes){
        Note note = noteMapper.getNote(notes.getNoteid());
        note.setNotetittle(notes.getNotetittle());
        note.setNotedescription(notes.getNotedescription());
        noteMapper.editNote(note);
    }

    public int addNote(Note note){
        return noteMapper.addNote(new Note(0,note.getNotetittle(),note.getNotedescription()
        ,authenticatedUserService.getLoggedInUserId()));
    }

    public void deleteNote(Integer noteId){
        noteMapper.deleteNote(noteId);
    }
}
