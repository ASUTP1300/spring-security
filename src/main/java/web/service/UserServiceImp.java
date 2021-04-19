package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.JpaUserImplDAO;
import web.dao.UserDAO;
import web.model.Role;
import web.model.User;
import web.repository.UserRepository;

import java.util.Collections;
import java.util.List;
@Service
public class UserServiceImp implements UserService, UserDetailsService {

    private PasswordEncoder passwordEncoder;

    private UserDAO userDAO;

    @Autowired
    private UserRepository userRepository;

     @Autowired
     public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
         this.passwordEncoder = passwordEncoder;
     }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public UserServiceImp(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Transactional
    @Override
    public void add(User user) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            userRepository.save(user);
    }

    @Transactional
    @Override
    public void update(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
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

    @Override
    public User getByFirstName(String firstname) {
       return userDAO.getByFirstName(firstname);
    }

    @Transactional
    @Override
    public List<User> listUsers() {
        return userDAO.listUsers();
    }

    @Override
    public UserDetails loadUserByUsername(String firstName) throws UsernameNotFoundException {
        User user = userDAO.getByFirstName(firstName);

        if (user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
