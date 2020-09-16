package controller;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller()
@RequestMapping("/file")
public class FileController {
    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/file/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Authentication authentication, RedirectAttributes redirectAttributes){
        if (file.isEmpty()){
            redirectAttributes.addFlashAttribute("message","Please upload a file");
            return "redirect:/home";
        }
        else if (!fileService.isFileNameAvailable(file.getOriginalFilename())){
            redirectAttributes.addFlashAttribute("message","File name with this name already exists");
            return "redirect:/home";
        }
        int row  = fileService.uploadFile(file);
        if (row<0){
            redirectAttributes.addFlashAttribute("message","Try again.");
            return "redirect:/home";
        }
        redirectAttributes.addFlashAttribute("message","Successufly upload "+file.getOriginalFilename());
        return "redirect:/home";
    }

    @GetMapping()
    public String deleteFile(@PathVariable String filename,Authentication authentication,RedirectAttributes redirectAttributes){
        fileService.deleteFile(filename);
        redirectAttributes.addFlashAttribute("deleteSuccess","Successfully deleted: "+filename);
        return "reterect:/home";
    }

}
