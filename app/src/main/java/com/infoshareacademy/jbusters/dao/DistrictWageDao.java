package com.infoshareacademy.jbusters.dao;

import com.infoshareacademy.jbusters.model.DistrictWage;
import com.infoshareacademy.jbusters.model.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class DistrictWageDao {

    @PersistenceContext
    private EntityManager entityManager;

    public int save(DistrictWage dw) {
        entityManager.persist(dw);
        return dw.getDistrictId();
    }

    public DistrictWage update(DistrictWage dw) {
        return entityManager.merge(dw);
    }

    public void delete(Long id) {
        final DistrictWage dw = entityManager.find(DistrictWage.class, id);
        if (dw != null) {
            entityManager.remove(dw);
        }
    }

    public DistrictWage findById(int id) {
        return entityManager.find(DistrictWage.class, id);
    }



    public List<DistrictWage> findAll() {
        final Query query = entityManager.createQuery("SELECT dw FROM DistrictWage dw");

        return query.getResultList();
    }
}
