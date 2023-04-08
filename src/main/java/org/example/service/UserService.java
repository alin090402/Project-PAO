package org.example.service;

import org.example.model.Ingredient;
import org.example.model.Inventory;
import org.example.model.Recipe;
import org.example.model.User;
import org.example.model.enumeration.Role;
import org.example.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static UserService instance;

    private UserRepository userRepository;

    private UserService() {
        //maybe I'll switch to dependency injection
        this.userRepository = UserRepository.getInstance();
    }


    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public void register(String username) {
        if (userRepository.getUserByUsername(username) == null) {
            userRepository.addUser(new User(0, Role.USER, username, new ArrayList<>(), new Inventory(new ArrayList<>())));

        }
    }

    public List<User> getAllUsers() {
        return userRepository.getUsers();
    }
    public User FindUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public void addRecipeToFavorite(User user, Recipe recipe) {
        user.getFavoriteRecipes().add(recipe);
        //it does nothing, but it will be useful when I'll switch to database
        userRepository.updateUser(user);
    }

    public void removeRecipeFromFavorite(User user, Recipe recipe) {
        user.getFavoriteRecipes().remove(recipe);
        //it does nothing, but it will be useful when I'll switch to database
        userRepository.updateUser(user);
    }

    public void AddIngredientToInventory(User user, Ingredient ingredient) {
        user.getInventory().getIngredients().add(ingredient);
        //it does nothing, but it will be useful when I'll switch to database
        userRepository.updateUser(user);
    }

    public void RemoveIngredientFromInventory(User user, Ingredient ingredient) {
        user.getInventory().getIngredients().remove(ingredient);
        //it does nothing, but it will be useful when I'll switch to database
        userRepository.updateUser(user);
    }

    public void deleteUser(String username) {
        userRepository.deleteUser(username);
    }

    public void givePremium(String username) {
        userRepository.givePremium(username);
    }


}
