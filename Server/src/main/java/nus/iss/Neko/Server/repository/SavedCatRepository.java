package nus.iss.Neko.Server.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import static nus.iss.Neko.Server.utils.Queries.*;

@Repository
public class SavedCatRepository {
    
    @Autowired
    private JdbcTemplate template;

    public boolean alterSavedCat(String cat_id, String cat_label,
            String email, String alteration) {
        int added = 0;
        if (alteration.contains("add")) {
            added = template.update(SQL_ADD_SAVED_CAT, email, cat_id, cat_label);
        } else {
            added = template.update(SQL_DELETE_SAVED_CAT, cat_id, email);
        }

        return added == 1;
    }

    public boolean isCatSaved(String cat_id, String email) {
        SqlRowSet result = template
            .queryForRowSet(SQL_IS_CAT_SAVED, email, cat_id);

        if (!result.next()) {
            return false;
        } else {
            return true;
        }
    }

    public SqlRowSet getSavedCats(String email) {
        return template.queryForRowSet(SQL_GET_SAVED_CATS, email);
    }
}
