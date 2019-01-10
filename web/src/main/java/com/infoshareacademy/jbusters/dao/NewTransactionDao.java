package com.infoshareacademy.jbusters.dao;

import com.infoshareacademy.jbusters.model.NewTransaction;
import com.infoshareacademy.jbusters.model.User;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class NewTransactionDao {

    @PersistenceContext
    private EntityManager entityManager;

    public int save(NewTransaction n) {
        entityManager.persist(n);
        return n.getNewTransactionId();
    }

    public NewTransaction update(NewTransaction n) {
        return entityManager.merge(n);
    }

    public void delete(int id) {
        final NewTransaction n = entityManager.find(NewTransaction.class, id);
        if (n != null) {
            entityManager.remove(n);
        }
    }

    public NewTransaction findById(int id) {
        return entityManager.find(NewTransaction.class, id);
    }


    public List<NewTransaction> findAll() {
        final Query query = entityManager.createQuery("SELECT u FROM NewTransaction u");

        return query.getResultList();
    }

    public List<NewTransaction> findByUser(User user) {

        final Query query = entityManager.createQuery("SELECT u FROM NewTransaction u WHERE u.newTransactionUser = :user");
        query.setParameter("user", user);

        return (List<NewTransaction>) query.getResultList();
    }
}