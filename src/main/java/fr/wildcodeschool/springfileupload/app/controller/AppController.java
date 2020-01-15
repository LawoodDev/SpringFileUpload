package fr.wildcodeschool.springfileupload.app.controller;

import fr.wildcodeschool.springfileupload.app.entity.User;
import fr.wildcodeschool.springfileupload.app.repository.UserRepository;
import fr.wildcodeschool.springfileupload.app.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AppController {

    @Autowired
    private UserRepository userRepository;

    private StorageService storageService;

    @Autowired
    public void FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }


    @GetMapping
    public String index(){
        return "home";
    }

    @GetMapping("/user/create")
    public String createUser(Model model){
        model.addAttribute("newUser", new User());
        return "form";
    }

    @PostMapping("/user/create")
    public String createUserPost(@ModelAttribute User user, Model model){
        //verification !!!
        storageService.store(user.getAvatar());
        user.setAvatar_url("/files/"+ user.getAvatar().getOriginalFilename());
        user = userRepository.save(user);

        model.addAttribute("currentUser", user);
        return "profile";
    }


    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

}
