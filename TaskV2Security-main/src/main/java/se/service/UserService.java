package se.service;

import se.domain.User;

import java.util.List;

public interface UserService {
    void save(User user);

    void delete(Integer id);

    List<User> getAll();

    User getByLogPass(String login, String password);

    User getByFileUser(Integer id);

    Integer getId(User user);

    User getUserByUsername(String username);

    String findByName(String name);
}
