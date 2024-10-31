package org.com.service;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;
import org.com.entity.Post;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class StartUpService {
    
    @RestClient
    PostExternalService postExternalService;
    
    @Transactional
    public void importPosts() {
        System.out.println("StartUpService - import posts from external api");
        Post.persist(this.postExternalService.getAll());
    }
    
    public void onStart(@Observes StartupEvent ev) {
        System.out.println("StartUpService - App Started - start");
        this.importPosts();
        System.out.println("StartUpService - App Started - end");
    }
}