package org.com.service;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.com.client.PostExternalClient;
import org.com.entity.Post;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@ApplicationScoped
public class StartupService {
    
    @Inject
    Logger logger;
    @RestClient
    PostExternalClient postExternalClient;
    
    @Transactional
    public void importPosts() {
        logger.info("StartupService - importPosts");
        Post.persist(this.postExternalClient.getAll());
    }
    
    public void onStart(@Observes StartupEvent ev) {
        logger.info("StartupService - onStart - start");
        this.importPosts();
        logger.info("StartupService - onStart - end");
    }
}