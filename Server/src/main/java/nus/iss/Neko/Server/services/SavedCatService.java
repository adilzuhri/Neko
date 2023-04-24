package nus.iss.Neko.Server.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import nus.iss.Neko.Server.models.SavedCat;
import nus.iss.Neko.Server.repository.SavedCatRepository;

@Service
public class SavedCatService {

    @Autowired
    private SavedCatRepository catRepo;

    public boolean updateSavedCat(String email, String cat_label, 
            String alteration, String cat_id) {
        return catRepo.alterSavedCat(cat_id, cat_label, email, alteration);
    }

    public boolean isCatSaved(String email, String cat_id) {
        return catRepo.isCatSaved(cat_id, email);
    }

    public Optional<List<SavedCat>> getSavedCats(String email) {
        SqlRowSet result = catRepo.getSavedCats(email);
        if (!result.next()) {
            return Optional.empty();
        }
        result.previous();
        List<SavedCat> savedCats = new LinkedList<>();

        while(result.next()) {
            savedCats.add(SavedCat.createSavedCats(result));
        }

        return Optional.of(savedCats);
    }
    
}
