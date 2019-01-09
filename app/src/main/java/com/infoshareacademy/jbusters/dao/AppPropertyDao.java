package com.infoshareacademy.jbusters.dao;

import com.infoshareacademy.jbusters.model.AppProperty;
import com.infoshareacademy.jbusters.model.User;

import javax.ejb.Stateless;
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


    public AppProperty findByName(String apName){
        return entityManager.find(AppProperty.class, apName);
    }

    public AppProperty findById(int id) {
        return entityManager.find(AppProperty.class, id);
    }


    public List<AppProperty> findAll() {
        final Query query = entityManager.createQuery("SELECT ap FROM AppProperty ap");

        return query.getResultList();
    }

}
