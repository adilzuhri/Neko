package nus.iss.Neko.Server.utils;

public interface Queries {
    
    public static final String SQL_GET_USER = 
        "select * from users where email = ?";

    public static final String SQL_NEW_USER = 
        "insert into users(username, email, password) values (?, ?, ?)";

    public static final String SQL_NEW_POST = 
        "insert into posts(email, username, title, caption, cat_id, cat_label, likes, date, imageUUID) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String SQL_GET_ALL_POSTS = 
        "select posts.email, posts.post_id, posts.username, posts.cat_label, posts.post_id, posts.title, posts.caption, posts.cat_id, posts.likes, posts.imageUUID, posts.date, likedPosts.email as likedPostsEmail from posts left join (select * from likedPosts where email = ?) as likedPosts on posts.post_id = likedPosts.post_id where posts.date > NOW() - INTERVAL 60 DAY ORDER BY post_id DESC;";

    public static final String SQL_ALTER_LIKES_BY_POST =
        "update posts set likes = ? where post_id = ?";

    public static final String SQL_GET_LIKES_BY_POST = 
        "select likes from posts where post_id = ?";

    public static final String SQL_DELETE_LIKED_POST = 
        "delete from likedPosts where post_id = ? and email = ?";

        public static final String SQL_ADD_LIKED_POST = 
        "insert into likedPosts(email, post_id) values (?, ?)";

    public static final String SQL_DELETE_SAVED_CAT = 
        "delete from savedCats where cat_id = ? and email = ?";
    
    public static final String SQL_ADD_SAVED_CAT = 
        "insert into savedCats(email, cat_id, cat_label) values (?, ?, ?)";

    public static final String SQL_IS_CAT_SAVED = 
        "select * from savedCats where email = ? and cat_id = ?";

    public static final String SQL_GET_ALL_LIKED_POSTS = 
        "select posts.email, posts.username, posts.cat_label, posts.post_id, posts.title, posts.caption, posts.cat_id, posts.likes, posts.imageUUID, posts.date, likedPosts.email as likedPostsEmail from posts right join (select * from likedPosts where email = ?) as likedPosts on posts.post_id = likedPosts.post_id where posts.date > NOW() - INTERVAL 60 DAY ORDER BY post_id DESC;";

    public static final String SQL_GET_POPULAR_POSTS = 
        "select posts.email, posts.username, posts.cat_label, posts.post_id, posts.title, posts.caption, posts.cat_id, posts.likes, posts.imageUUID, posts.date, likedPosts.email as likedPostsEmail from posts left join (select * from likedPosts where email = ?) as likedPosts on posts.post_id = likedPosts.post_id order by posts.likes DESC limit 100";

    public static final String SQL_GET_MY_POSTS = 
        "select posts.email, posts.username, posts.cat_label, posts.post_id, posts.title, posts.caption, posts.cat_id, posts.likes, posts.imageUUID, posts.date, likedPosts.email as likedPostsEmail from posts left join (select * from likedPosts where email = ?) as likedPosts on posts.post_id = likedPosts.post_id where posts.email = ? ORDER BY post_id DESC;";

    public static final String SQL_GET_SAVED_CATS =
        "select * from savedCats where email = ?";

    public static final String SQL_GET_POSTS_BY_CAT_ID = 
        "select posts.email, posts.username, posts.cat_label, posts.post_id, posts.title, posts.caption, posts.cat_id, posts.likes, posts.imageUUID, posts.date, likedPosts.email as likedPostsEmail from posts left join (select * from likedPosts where email = ?) as likedPosts on posts.post_id = likedPosts.post_id where cat_id = ?";

    public static final String SQL_DELETE_POST = 
        "delete from posts where email = ? and post_id = ?";

    public static final String SQL_DELETE_LIKED_POSTS_BY_POSTS_ID_STRING = 
        "delete from likedPosts where post_id = ?";
    
}
