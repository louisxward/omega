package org.com.service;

import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.RequestScoped;
import org.com.entity.Post;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class PostService {
    
    public long count() {
        return Post.count();
    }
    
    public Optional<Post> get(Long id) {
        return Post.findByIdOptional(id);
    }
    
    public List<Post> getByUserId(Long id) {
        return Post.list("userId", id);
    }
    
    public List<Post> getAll(Long id, Long userId, String title) {
        return Post.listAll();
    }
    
    public List<Post> getSearch(Long id, Long userId, String title) {
        return Post.list("id = :id or userId = :userId or title = :title",
            Parameters.with("id", id).and("userId", userId).and("title", title));
    }
    
}