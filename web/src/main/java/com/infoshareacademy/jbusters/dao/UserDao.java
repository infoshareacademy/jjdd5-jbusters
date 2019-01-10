package com.infoshareacademy.jbusters.dao;

import com.infoshareacademy.jbusters.model.User;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public int save(User u) {
        entityManager.persist(u);
        return u.getUserId();
    }

    public User update(User u) {
        return entityManager.merge(u);
    }

    public void delete(int id) {
        final User u = entityManager.find(User.class, id);
        if (u != null) {
            entityManager.remove(u);
        }
    }

    public User findEmail(String userEmail){
        return entityManager.find(User.class, userEmail);
    }

    public User findById(int id) {
        return entityManager.find(User.class, id);
    }


    public List<User> findAll() {
        final Query query = entityManager.createQuery("SELECT u FROM User u");

        return (List<User>) query.getResultList();
    }

    public User findByEmail(String sessionEmail) {
        final Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.userEmail = :email");
        query.setParameter("email", sessionEmail);

       return (User) query.getSingleResult();
    }
}
