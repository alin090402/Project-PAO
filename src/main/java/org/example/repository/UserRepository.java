package org.example.repository;

import org.example.database.Database;
import org.example.model.Inventory;
import org.example.model.PremiumUser;
import org.example.model.User;
import org.example.model.enumeration.Role;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class UserRepository {

    private Set<User> users = new TreeSet<>(List.of(
            new PremiumUser(1, Role.ADMIN, "admin", new ArrayList<>(), new Inventory(new ArrayList<>()), new Date(new Date().getTime() + 30 * 24 * 60 * 60 * 1000)),
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
    public Set<User> getUsers() {
        return users;
    }
    public User getUserById(long id) {
        return users.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }
    public User getUserByUsername(String username) {
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }
    public void addUser(User user) {

        try{
            String insertUsers = "INSERT INTO users (role, username) VALUES (?, ?)";
            PreparedStatement preparedStatement = Database.getInstance().getPreparedStatement(insertUsers);
            preparedStatement.setString(1, user.getRole().toString());
            preparedStatement.setString(2, user.getUsername());
            Database.getInstance().executePrepared(preparedStatement);

            if (user instanceof PremiumUser){
                String insertPremiumUsers = "INSERT INTO users_premium (user_id, expirationDate) VALUES (?, ?)";
                preparedStatement = Database.getInstance().getPreparedStatement(insertPremiumUsers);
                preparedStatement.setLong(1, user.getId());
                preparedStatement.setDate(2, new java.sql.Date(((PremiumUser) user).getExpirationDate().getTime()));
                Database.getInstance().executePrepared(preparedStatement);
            }

            String insertInventory = "INSERT INTO inventories (user_id, ingredient_id) VALUES (?, ?)";
            preparedStatement = Database.getInstance().getPreparedStatement(insertInventory);
            for (var ingredient: user.getInventory().getIngredients()){
                preparedStatement.setLong(1, user.getId());
                preparedStatement.setLong(2, ingredient.getId());
                Database.getInstance().executePrepared(preparedStatement);
            }

            String insertFavoriteRecipes = "INSERT INTO favorite_recipes (user_id, recipe_id) VALUES (?, ?)";
            preparedStatement = Database.getInstance().getPreparedStatement(insertFavoriteRecipes);
            for (var recipe: user.getFavoriteRecipes()){
                preparedStatement.setLong(1, user.getId());
                preparedStatement.setLong(2, recipe.getId());
                Database.getInstance().executePrepared(preparedStatement);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

//        user.setId(NextId++);
//        users.add(user);
    }
    public void updateUser(User user) {
        User userToUpdate = getUserById(user.getId());
        userToUpdate.setRole(user.getRole());
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setFavoriteRecipes(user.getFavoriteRecipes());
        userToUpdate.setInventory(user.getInventory());
    }
    public void deleteUser(String username) {
        users.remove(getUserByUsername(username));
    }

    public void givePremium(String username) {
        User user = getUserByUsername(username);
        if (user == null)
            return;
        else if(!user.isPremium())
        {
            user = new PremiumUser(user.getId(), user.getRole(), user.getUsername(), user.getFavoriteRecipes(), user.getInventory(), new Date(new Date().getTime() + 30 * 24 * 60 * 60 * 1000));
        }
        else {
            ((PremiumUser) user).extendMembershipByDays(30);
        }

        //we will add these in the same unit of work when we will switch to database
        users.remove(getUserByUsername(username));
        users.add(user);

    }

}
