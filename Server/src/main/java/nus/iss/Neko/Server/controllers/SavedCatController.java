package nus.iss.Neko.Server.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nus.iss.Neko.Server.models.SavedCat;
import nus.iss.Neko.Server.services.SavedCatService;

@RestController
@RequestMapping("/saved")
public class SavedCatController {

    @Autowired
    private SavedCatService savedCatSvc;

    @GetMapping("/allCat")
    public ResponseEntity<?> getAllSavedCat(@RequestParam String email) {
        Optional<List<SavedCat>> savedCatOpt = savedCatSvc.getSavedCats(email);
        if (savedCatOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("No cats saved");
        }

        return ResponseEntity.ok(savedCatOpt.get());
    }

    @GetMapping("/alterSaved")
    public ResponseEntity<String> alterSavedCat(
            @RequestParam String email, @RequestParam String cat_label,
            @RequestParam String cat_id, @RequestParam String alteration) {

        boolean added = savedCatSvc.updateSavedCat(email, cat_label, alteration, cat_id);
        if (added) {
            return ResponseEntity.ok("\"Successful saved cats\"");
        } else {
            return ResponseEntity.internalServerError().body("\"Error! Unable to save cat!\"");
        }
    }
}
