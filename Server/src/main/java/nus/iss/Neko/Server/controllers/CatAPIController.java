package nus.iss.Neko.Server.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nus.iss.Neko.Server.models.Cat;
import nus.iss.Neko.Server.models.CatDetailsResponse;
import nus.iss.Neko.Server.services.CatService;
import nus.iss.Neko.Server.services.SavedCatService;



@RestController
@RequestMapping("/search")
public class CatAPIController {

    @Autowired
    private CatService catSvc;

    @Autowired
    private SavedCatService savedCatSvc;

    @GetMapping("/Cats/{numPage}")
    public ResponseEntity<?> getCats(@RequestParam("query") String query,
            @PathVariable("numPage") int numPage, 
            @RequestParam(required = false) String _contValue) {

        Optional<?> getCatsOtp = catSvc.getCatId(query, numPage, _contValue);

        if (getCatsOtp.isEmpty()) {
            return ResponseEntity.badRequest().body("No results found");
        } else if (getCatsOtp.get() instanceof String == true) {
            return ResponseEntity.internalServerError().body("Internal error!");
        } else {
            return ResponseEntity.ok(getCatsOtp.get());
        }
    }

    @GetMapping("/Cat/{id}")
    public ResponseEntity<?> getCatDetails(@PathVariable("id") String id,
            @RequestParam String email) {

        Optional<?> getCatDetailOpt = catSvc.getCatDetails(id);
        if (getCatDetailOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("No results found");
        } else if (getCatDetailOpt.get() instanceof String == true) {
            return ResponseEntity.internalServerError().body("Internal error!");
        } else {
            CatDetailsResponse CatDetResp = new CatDetailsResponse();
            CatDetResp.setCat((Cat)getCatDetailOpt.get());
            Boolean isSaved = savedCatSvc.isCatSaved(email, id);
            CatDetResp.setSaved(isSaved);
            return ResponseEntity.ok(CatDetResp); 
        }
    }

    @GetMapping("/Cat/label/{id}")
    public ResponseEntity<String> getCatLabel(@PathVariable("id") String id) {
        Optional<?> getCatLabelOpt = catSvc.getCatLabelById(id);
        if (getCatLabelOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("No results found");
        } else if (getCatLabelOpt.get().toString().contains("error")) {
            return ResponseEntity.internalServerError().body("Internal error!");
        } else {
            String label = getCatLabelOpt.get().toString();
            return ResponseEntity.ok(label); 
        }
    } 
    @PutMapping("/{id}")
    public void updateCat(@PathVariable Long id, @RequestBody Cat cat) {
        Cat existingCat = CatService.getCatLabelById(id);
        if (existingCat != null) {
            existingCat.setName(cat.getName());
            existingCat.setBreed(cat.getBreed());
            existingCat.setAge(cat.getAge());
            CatService.saveCat(existingCat);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCat(@PathVariable Long id) {
        CatService.deleteCat(id);
    }
}
