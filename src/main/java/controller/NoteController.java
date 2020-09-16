package controller;

import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import model.Note;
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

    @PostMapping("/add")
    public String addNote(@ModelAttribute Note note, Model model, RedirectAttributes redirectAttributes){
        if (note.getNoteid()==null){
            noteService.addNote(note);
        }else {
            noteService.editNotes(note);

            redirectAttributes.addFlashAttribute("editNoteSuccess","Note edited.");
            return "redirect:/result";
        }
        return "redirect:/home";
    }

    @GetMapping("/delete/{noteid}")
    public String deleteNote(@PathVariable Integer noteId, Authentication authentication, RedirectAttributes redirectAttributes){
        noteService.deleteNote(noteId);
        redirectAttributes.addFlashAttribute("deleteNoteSuccess","Delete note successfully");
        return "redirect:/result";
    }
}
