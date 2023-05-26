package org.example.model;

import java.util.List;
import java.util.Set;

public class Recipe extends BaseModel {

    Set<IngredientInRecipe> ingredients;
    //unique attribute in database
    String name;
    String description;

    public Recipe(String name, String description, Set<IngredientInRecipe> ingredients) {
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

    public Set<IngredientInRecipe> getIngredients() {
        return ingredients;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIngredients(Set<IngredientInRecipe> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return name;
    }

}
