package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PrePersist;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class JpaUserImplDAO implements UserDAO {

    @PersistenceContext
    @Autowired
    private EntityManager entityManager;

    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    public void remove(long id) {
       User user = entityManager.find(User.class, id);
       if(user != null){
           entityManager.remove(user);
       }
    }

    @Override
    public User getById(long id) {
        TypedQuery<User> typedQuery = entityManager.createQuery("SELECT u FROM User u WHERE u.id = : id", User.class );
        typedQuery.setParameter("id", id);
        return typedQuery.getResultList().get(0);
    }

    @Override
    public List<User> listUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
}
