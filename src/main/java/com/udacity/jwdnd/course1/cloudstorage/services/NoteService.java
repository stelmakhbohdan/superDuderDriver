package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NotesMapper noteMapper;
    private AuthenticatedUserService authenticatedUserService;

    public NoteService(NotesMapper noteMapper, AuthenticatedUserService authenticatedUserService) {
        this.noteMapper = noteMapper;
        this.authenticatedUserService = authenticatedUserService;
    }

    public Notes getNote() {
        return noteMapper.getNote(authenticatedUserService.getLoggedInUserId());
    }

    public Notes getNote(Integer noteId) {
        return noteMapper.getNote(noteId);
    }

    public int addNote(Notes note) {
        return noteMapper.addNote(new Notes(0, note.getNoteTitle(),
                note.getNoteDescription(),
                authenticatedUserService.getLoggedInUserId()));
    }

    public void editNote(Notes note) {
        Notes notes = noteMapper.getNote(note.getNoteId());
    }
}

