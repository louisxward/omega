package org.com.service;


import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import org.com.entity.Field;
import org.com.model.FieldUpdateRequest;
import org.com.repository.FieldRepository;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class FieldService {

    private final ConcurrentLinkedQueue<FieldUpdateRequest> requestQueue = new ConcurrentLinkedQueue<>();

    @Inject
    FieldRepository repository;

    @Transactional
    public Field createField(Field field) {
        repository.persist(field);
        return field;
    }

    public void queueUpdateRequest(FieldUpdateRequest request) {
        requestQueue.offer(request);
    }

    @Scheduled(every = "1s", delay = 10, delayUnit = TimeUnit.SECONDS)
    void processQueuedUpdates() {
        FieldUpdateRequest request;
        while ((request = requestQueue.poll()) != null) {
            processUpdateRequest(request);
        }
    }

    @Transactional
    private void processUpdateRequest(FieldUpdateRequest request) {
        Long id = request.id(); // Assuming getId() method in FieldUpdateRequest
        Field field = request.field(); // Assuming getfield() method in FieldUpdateRequest

        if (!id.equals(field.id)) {
            // Handle ID mismatch (e.g., log an error)
            return;
        }
        try {
            repository.persist(field);
        } catch (OptimisticLockException e) {
            // Handle optimistic locking exception (e.g., retry or log)
        }
    }

    // ... other service methods
}