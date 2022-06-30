package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServices {

    private NotesMapper notesMapper;
    private AuthenticatedUserService authenticatedUser;

    public NoteServices(NotesMapper notesMapper, AuthenticatedUserService authenticatedUser) {
        this.notesMapper = notesMapper;
        this.authenticatedUser = authenticatedUser;
    }

    public List<Notes> getNotes(){
        return notesMapper.getNotes(authenticatedUser.getLoggedInUserId());
    }

    public Notes getNote(Integer noteId){
        return notesMapper.getNote(noteId);
    }

    public int addNote(NoteForm noteForm){
        return notesMapper.addNote(new Notes(0,noteForm.getNoteTitle(),noteForm.getNoteDescription(),authenticatedUser.getLoggedInUserId()));
    }

    public void editNote(NoteForm noteForm){
        Notes note = notesMapper.getNote(noteForm.getNoteId());
        note.setNoteTitle(noteForm.getNoteTitle());
        note.setNoteDescription(noteForm.getNoteDescription());
        notesMapper.editNote(note);
    }

    public void deleteNote(Integer noteId){
        notesMapper.deleteNote(noteId);
    }
}
