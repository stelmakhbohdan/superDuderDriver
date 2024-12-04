package com.example.demo.service;

import com.example.demo.model.Notes;

import java.util.List;

public interface NoteService {

    public int addNote(Notes note);
    public void deleteNote(int id);
    public void updateNote(Notes note);
    public List<Notes> getAllNotes(int userId);
    public Notes findNote(int id);
}
