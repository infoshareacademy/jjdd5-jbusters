package com.infoshareacademy.jbusters.dao;

import com.infoshareacademy.jbusters.model.AppProperty;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Stateless
public class AppPropertyDao {

    @PersistenceContext
    private EntityManager entityManager;

    public AppProperty update(AppProperty ap) {
        return entityManager.merge(ap);
    }


    public AppProperty findById(int id) {
        return entityManager.find(AppProperty.class, id);
    }

    public AppProperty findByName(String name) {
        final Query query = entityManager.createQuery("SELECT ap FROM AppProperty ap WHERE ap.apName = name");
        query.setParameter("name", name);

        return (AppProperty) query.getSingleResult();
    }

    public List<AppProperty> findAll() {
        final Query query = entityManager.createQuery("SELECT ap FROM AppProperty ap");

        return query.getResultList();
    }

}
