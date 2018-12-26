package com.infoshareacademy.jbusters.dao;

import com.infoshareacademy.jbusters.model.Users;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class UsersDao {

    @PersistenceContext
    private EntityManager entityManager;

    public int save(Users u) {
        entityManager.persist(u);
        return u.getUsersId();
    }

    public Users update(Users u) {
        return entityManager.merge(u);
    }

    public void delete(Long id) {
        final Users u = entityManager.find(Users.class, id);
        if (u != null) {
            entityManager.remove(u);
        }
    }

    public Users findEmail(String usersEmail){
        return entityManager.find(Users.class, usersEmail);
    }

    public Users findById(Long id) {
        return entityManager.find(Users.class, id);
    }


    public List<Users> findAll() {
        final Query query = entityManager.createQuery("SELECT u FROM Users u");

        return query.getResultList();
    }

}
