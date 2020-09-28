package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller()
@RequestMapping("/note")
public class NoteController {
    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    private Logger logger = LoggerFactory.getLogger(NoteController.class);

    @PostMapping
    public String createOrUpdateNote(NoteForm noteModalForm, Authentication authentication, RedirectAttributes redirectAttributes) {
        String username = authentication.getName();

        if (!noteModalForm.getNoteid().equalsIgnoreCase("")) {
            try {
                this.noteService.editNote(noteModalForm, username);
                redirectAttributes.addFlashAttribute("successMessage", "Your note was updated successfully.");
                return "redirect:/result";
            } catch (Exception e) {
                logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong with the note update. Please try again!");
                return "redirect:/result";
            }
        } else {
            try {
                this.noteService.addNote(noteModalForm, username);
                redirectAttributes.addFlashAttribute("successMessage", "Your note was successfully created.");
                noteModalForm.setNotetitle("");
                noteModalForm.setNotedescription("");
                return "redirect:/result";
            } catch (Exception e) {
                logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong with the note creation... Please try again.");
                return "redirect:/result";
            }
        }

    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable int noteId, RedirectAttributes redirectAttributes) {
        try {
            noteService.deleteNote(noteId);
            redirectAttributes.addFlashAttribute("successMessage", "Your note was successfully deleted");
            return "redirect:/result";
        } catch (Exception e) {
            logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong with the note deletion... Please try again.");
            return "redirect:/result";
        }
    }
}
