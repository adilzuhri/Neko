package nus.iss.Neko.Server.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import nus.iss.Neko.Server.models.Post;
import static nus.iss.Neko.Server.utils.Queries.*;

@Repository
public class PostRepository {

    @Autowired
    private JdbcTemplate template;

    public boolean deletePost(int post_id, String email) {
        int deleted = template.update(SQL_DELETE_POST, email, post_id);
        return deleted == 1;
    }

    public void deleteLikedPostsById(int post_id) {
        try {
            template.update(SQL_DELETE_LIKED_POSTS_BY_POSTS_ID_STRING, post_id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public Optional<SqlRowSet> getAllPostsDateAsc(String email) {
        SqlRowSet result = template.queryForRowSet(SQL_GET_ALL_POSTS, email);
        return getResultFromDatabase(result);
    }

    public boolean updateLikesOnPost(int post_id, String alteration) {
        
        SqlRowSet result = template.queryForRowSet(SQL_GET_LIKES_BY_POST, post_id);
        result.next();
        int likes = result.getInt("likes");
        if (alteration.contains("add")) {
            likes++;
        } else {
            likes--;
        }

        try {
            template.update(SQL_ALTER_LIKES_BY_POST, likes, post_id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        // int added = template.update(SQL_ALTER_LIKES_BY_POST, likes, post_id);
        // return added == 1;

        return true;

    }

    public boolean alterPostInLikedPost(int post_id, String email, String alteration) {
        int added = 0;
        if (alteration.contains("add")) {
            added = template.update(SQL_ADD_LIKED_POST, email, post_id);
        } else {
            added = template.update(SQL_DELETE_LIKED_POST, post_id, email);
        }
        return added == 1;
    }

    public Optional<SqlRowSet> getPopularPost(String email) {
        SqlRowSet result = template.queryForRowSet(SQL_GET_POPULAR_POSTS, email);
        return getResultFromDatabase(result);
    }

    public boolean uploadPost(Post post) {
        int added = template.update(SQL_NEW_POST,
                post.getEmail(), post.getUsername(),
                post.getTitle(), post.getCaption(), post.getCat_id(),
                post.getCat_label(), 0,
                post.getDate(), post.getImageUUID());
        // email, title, caption, cat_id, ratings, likes, date, imageUUID

        return added == 1;
    }

    public Optional<SqlRowSet> getAllLikedPosts(String email) {
        SqlRowSet result = template.queryForRowSet(SQL_GET_ALL_LIKED_POSTS, email);
        return getResultFromDatabase(result);
    }

    public Optional<SqlRowSet> getMyPosts(String email) {
        SqlRowSet result = template.queryForRowSet(SQL_GET_MY_POSTS, email, email);
        return getResultFromDatabase(result);
    }

    public Optional<SqlRowSet> getPostByCatId(String email, String cat_id) {
        SqlRowSet result = template.queryForRowSet(SQL_GET_POSTS_BY_CAT_ID, email, cat_id);
        return getResultFromDatabase(result);
    }

    private Optional<SqlRowSet> getResultFromDatabase(SqlRowSet result) {
        if (!result.next()) {
            return Optional.empty();
        }
        result.previous();
        return Optional.of(result);
    }
}
