package com.infoshareacademy.jbusters.dao;

import com.infoshareacademy.jbusters.model.Suggestions;
import com.infoshareacademy.jbusters.model.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class SuggestionsDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Suggestions s) {
        entityManager.persist(s);
    }

    public Suggestions update(Suggestions s) {
        return entityManager.merge(s);
    }

    public void delete(int id) {
        final Suggestions s = entityManager.find(Suggestions.class, id);
        if (s != null) {
            entityManager.remove(s);
        }
    }

    public Suggestions findById(int id) {
        return entityManager.find(Suggestions.class, id);
    }

    public List<Suggestions> findAll() {
        final Query query = entityManager.createQuery("SELECT u FROM Suggestions u");

        return (List<Suggestions>) query.getResultList();
    }

}

