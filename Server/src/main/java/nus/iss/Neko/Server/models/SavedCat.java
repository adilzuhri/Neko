package nus.iss.Neko.Server.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class SavedCat {
    private String cat_label;
    private String cat_id;

    public String getCat_label() {
        return cat_label;
    }
    public void setCat_label(String cat_label) {
        this.cat_label = cat_label;
    }
    public String getCat_id() {
        return cat_id;
    }
    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public static SavedCat createSavedCats(SqlRowSet result) {
        SavedCat savedCat = new SavedCat();
        savedCat.setCat_id(result.getString("cat_id"));
        savedCat.setCat_label(result.getString("cat_label"));

        return savedCat;
    }
}
