package com.infoshareacademy.jbusters.dao;

import com.infoshareacademy.jbusters.model.Configuration;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class ConfigurationDao {

    @PersistenceContext
    private EntityManager entityManager;

    public int save(Configuration c) {
        entityManager.persist(c);
        return c.getConfigId();
    }

    public Configuration update(Configuration c) {
        return entityManager.merge(c);
    }

    public void delete(Long id) {
        final Configuration c = entityManager.find(Configuration.class, id);
        if (c != null) {
            entityManager.remove(c);
        }
    }

    public Configuration findById(int id) {
        return entityManager.find(Configuration.class, id);
    }


    public List<Configuration> findAll() {
        final Query query = entityManager.createQuery("SELECT c FROM Configuration c");

        return query.getResultList();
    }
}
