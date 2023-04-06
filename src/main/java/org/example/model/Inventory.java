package org.example.model;

import java.util.List;

public class Inventory {
    List<Ingredient> ingredients;

    public Inventory(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
