package com.infoshareacademy.jbusters.dao;

import com.infoshareacademy.jbusters.data.Config;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class ConfigDao {
    @PersistenceContext
    private EntityManager entityManager;

    public Config update(Config c) {
        return entityManager.merge(c);
    }

    public Config findByName(String propertyName){
        return entityManager.find(Config.class, propertyName);
    }

    public Config findById(Long id) {
        return entityManager.find(Config.class, id);
    }

    public List<Config> findAll() {
        final Query query = entityManager.createQuery("SELECT c FROM Config c");

        return query.getResultList();
    }

}
