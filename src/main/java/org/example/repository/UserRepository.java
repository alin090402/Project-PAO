package org.example.repository;

import org.example.model.Inventory;
import org.example.model.User;
import org.example.model.enumeration.Role;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private List<User> users = new ArrayList<>(List.of(
            new User(1, Role.ADMIN, "admin", new ArrayList<>(), new Inventory(new ArrayList<>())),
            new User(2, Role.USER, "user", new ArrayList<>(), new Inventory(new ArrayList<>())),
            new User(3, Role.USER, "user2", new ArrayList<>(), new Inventory(new ArrayList<>())),
            new User(4, Role.USER, "user3", new ArrayList<>(), new Inventory(new ArrayList<>()))
    ));
    long NextId = 4;


    private static UserRepository instance;
    private UserRepository() {}
    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }
    public List<User> getUsers() {
        return users;
    }
    public User getUserById(long id) {
        return users.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }
    public User getUserByUsername(String username) {
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }
    public void addUser(User user) {
        user.setId(NextId++);
        users.add(user);
    }
    public void updateUser(User user) {
        User userToUpdate = getUserById(user.getId());
        userToUpdate.setRole(user.getRole());
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setFavoriteRecipes(user.getFavoriteRecipes());
        userToUpdate.setInventory(user.getInventory());
    }

}
