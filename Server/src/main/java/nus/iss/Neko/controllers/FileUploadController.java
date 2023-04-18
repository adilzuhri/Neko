package nus.iss.Neko.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import nus.iss.Neko.services.S3Service;

@Controller
public class FileUploadController {
    
    @Autowired
    private S3Service s3svc;

    @PostMapping(path="/upload-ng", 
            consumes=MediaType.MULTIPART_FORM_DATA_VALUE, 
            produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin()
    public ResponseEntity<String> uploadForAngular(
        @RequestPart MultipartFile imageFile,
        @RequestPart String title,
        @RequestPart String complain
    ){
        String key = "";
        System.out.printf("title: %s", title);
        System.out.printf("complain: %s", complain);
        
        try {
            key = s3svc.upload(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonObject payload = Json.createObjectBuilder()
            .add("imagekey", key)
            .build();

        return ResponseEntity.ok(payload.toString());
    }

    @PostMapping(path="/upload-tf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadTF(@RequestPart MultipartFile myfile, @RequestPart String name,
        Model model){
        String key = "";
        System.out.printf("name: %s\n", name);
        try {
            key = s3svc.upload(myfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("name", name);
        model.addAttribute("file", myfile);
        model.addAttribute("key", key);
        
        return "upload";
    }
}
