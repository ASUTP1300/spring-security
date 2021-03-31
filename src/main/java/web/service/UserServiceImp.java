package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.JpaUserImplDAO;
import web.dao.UserDAO;
import web.model.User;

import java.util.List;
@Service
public class UserServiceImp implements UserService {

    private UserDAO userDAO;

    @Autowired
    public UserServiceImp(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Transactional
    @Override
    public void add(User user) {
        userDAO.add(user);
    }

    @Transactional
    @Override
    public void update(User user) {
        userDAO.update(user);
    }

    @Transactional
    @Override
    public void remove(long id) {
        userDAO.remove(id);
    }

    @Override
    public User getById(long id) {
        return userDAO.getById(id);
    }

    @Transactional
    @Override
    public List<User> listUsers() {
        return userDAO.listUsers();
    }
}
