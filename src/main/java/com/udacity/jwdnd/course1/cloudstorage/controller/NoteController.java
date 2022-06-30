package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteServices;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/note")
public class NoteController {

    private NoteServices noteServices;


    public NoteController(NoteServices noteServices) {
        this.noteServices = noteServices;
    }

    @PostMapping("/add")
    public String addNote(@ModelAttribute("noteForm") NoteForm noteForm, Authentication authentication, RedirectAttributes redirectAttributes){

        if(noteForm.getNoteId() == null){
            noteServices.addNote(noteForm);
        }else{
            noteServices.editNote(noteForm);
            redirectAttributes.addFlashAttribute("editNoteSuccess","Note edited.");
            return "redirect:/result";
        }
        return "redirect:/result";


    }

    @GetMapping("/delete/{noteId:.+}")
    public String deleteNote(@PathVariable Integer noteId, Authentication authentication, RedirectAttributes redirectAttributes){
        noteServices.deleteNote(noteId);
        redirectAttributes.addFlashAttribute("deleteNoteSuccess","Note deleted successfully.");
        return "redirect:/result";
    }
}
