package com.example.library.repo;

import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

public class GenericRepository<T> {
    private final MongoTemplate template;
    private final Class<T> type;

    public GenericRepository(MongoTemplate template, Class<T> type) {
        this.template = template;
        this.type = type;
    }

    public T save(T obj) {
        template.save(obj);
        return obj;
    }

    public List<T> findAll() {
        return template.findAll(type);
    }

    public T findById(String id) {
        return template.findById(id, type);
    }

    public void deleteById(String id) {
        T found = template.findById(id, type);
        if (found != null) template.remove(found);
    }
}
