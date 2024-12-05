package com.example.demo.service.implimentation;

import com.example.demo.controller.FileController;
import com.example.demo.mapper.NoteMapper;
import com.example.demo.model.Notes;
import com.example.demo.service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    private Logger logger =  LoggerFactory.getLogger(NoteServiceImpl.class);
    @Autowired
    private NoteMapper noteMapper;


    @Override
    public int addNote(Notes note) {
        logger.info("Note to add: "+note);
        return noteMapper.insertNote(note);
    }

    @Override
    public void deleteNote(int id) {
        noteMapper.deleteNote(id);
    }

    @Override
    public void updateNote(Notes note) {
         noteMapper.updateNote(note);

    }

    @Override
    public List<Notes> getAllNotes(int UserId) {
        return noteMapper.getAllNotes(UserId);
    }

    @Override
    public Notes findNote(int id) {
        return null;
    }
}
