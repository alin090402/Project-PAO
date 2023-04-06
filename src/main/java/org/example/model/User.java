package org.example.model;

import org.example.model.Recipe;
import org.example.model.enumeration.Role;

import java.util.List;

public class User extends BaseModel {
    private Role role;
    private String username;
    private List<Recipe> favoriteRecipes;

    private Inventory inventory;

    public User(long id, Role role, String username, List<Recipe> favoriteRecipes, Inventory inventory) {
        super(id);
        this.role = role;
        this.username = username;
        this.favoriteRecipes = favoriteRecipes;
        this.inventory = inventory;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Recipe> getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public void setFavoriteRecipes(List<Recipe> favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public String toString()
    {
        return "User: " + this.username + " Role: " + this.role;
    }
}
