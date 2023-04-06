package org.example.model;

import java.util.List;

public class Recipe {

    List<IngredientInRecipe> ingredients;
    String name;
    String description;

    public Recipe(String name, String description, List<IngredientInRecipe> ingredients) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<IngredientInRecipe> getIngredients() {
        return ingredients;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIngredients(List<IngredientInRecipe> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return name;
    }

}
