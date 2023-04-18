package nus.iss.Neko.repository;

import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import nus.iss.Neko.models.Post;

@Repository
public class PostRepository {

	@Autowired
	private MongoTemplate template;

	public ObjectId insertPost(Post post) {
		Document insertedDoc = template.insert(post.toDocument(), "post");
		return insertedDoc.getObjectId("_id");
	}

	public Optional<Post> getPost(String postId) {
		Criteria criteria = Criteria.where("postId").is(postId);
		Query query = Query.query(criteria);
		Document result = template.findOne(query, Document.class, "post");
		if (null == result)
			return Optional.empty();

		return Optional.of(Post.create(result));
	}

}
