package com.infoshareacademy.jbusters.dao;

import com.infoshareacademy.jbusters.model.NewTransaction;
import com.infoshareacademy.jbusters.model.Tranzaction;

import javax.ejb.Stateless;
import javax.persistence.Cacheable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
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

    public List<Tranzaction> basicFilter(LocalDate date, String city, String marketType, int yearConstructCategory){

        final Query query = entityManager.createQuery("SELECT t FROM Tranzaction t WHERE t.transactionDataTransaction >= :date AND t.transactionTypeOfMarket = :marketType AND t.transactionCity = :city AND t.transactionConstructionYearCategory = :constructYearCat");
        query.setParameter("date", date);
        query.setParameter("marketType", marketType);
        query.setParameter("constructYearCat", yearConstructCategory);
        query.setParameter("city", city);
    return query.getResultList();
    }

    public List<String> getCitiesList(){
        final Query query = entityManager.createQuery("SELECT DISTINCT t.transactionCity FROM Tranzaction t ORDER BY t.transactionCity ASC");
        return query.getResultList();

    }
    public List<String> getDistrictsList(String city){
        final Query query = entityManager.createQuery("SELECT DISTINCT t.transactionDistrict FROM Tranzaction t WHERE t.transactionCity = :city ORDER BY t.transactionDistrict ASC");
        query.setParameter("city", city);
        return query.getResultList();

    }

}
