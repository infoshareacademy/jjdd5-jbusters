package com.infoshareacademy.jbusters.dao;

import com.infoshareacademy.jbusters.model.NewTransaction;
import com.infoshareacademy.jbusters.model.Tranzaction;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class TranzactionDao {
    @PersistenceContext
    private EntityManager entityManager;

    public int save(Tranzaction t) {
        entityManager.persist(t);
        return t.getTranzactionId();
    }

    public Tranzaction update(Tranzaction t) {
        return entityManager.merge(t);
    }

    public void delete(int id) {
        final Tranzaction t = entityManager.find(Tranzaction.class, id);
        if (t != null) {
            entityManager.remove(t);
        }
    }

    public Tranzaction findById(Long id) {
        return entityManager.find(Tranzaction.class, id);
    }


    public List<Tranzaction> findAll() {
        final Query query = entityManager.createQuery("SELECT t FROM Tranzaction t");

        return query.getResultList();
    }

}
