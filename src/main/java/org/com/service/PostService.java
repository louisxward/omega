package org.com.service;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.enterprise.context.RequestScoped;
import org.com.entity.Post;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@RequestScoped
public class PostService extends PanacheEntity {
    
    @RestClient
    PostExternalService postExternalService;
    
    public List<Post> getAll() {
        return postExternalService.getAll();
    }
    
    public void consumePosts() {
    
    }
    
}