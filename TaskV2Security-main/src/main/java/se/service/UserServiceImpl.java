package se.service;

import se.domain.User;
import se.repository.UserRepository;
import se.repository.UserRepositoryImpl;

import java.util.List;

public class UserServiceImpl implements UserService{

    private UserRepository userRepository= new UserRepositoryImpl();

    @Override
    public void save(User user) {
        if(user!=null) {
            userRepository.save(user);
        }
    }

    @Override
    public void delete(Integer id) {
        userRepository.delete(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public User getByLogPass(String login, String password) {
        return userRepository.getByLogPass(login,password);
    }

    @Override
    public User getByFileUser(Integer id) {
        return userRepository.getByFileUser(id);
    }

    @Override
    public Integer getId(User user) {
        return userRepository.getId(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Override
    public String findByName(String name) {
        return userRepository.findByName(name);
    }
}
