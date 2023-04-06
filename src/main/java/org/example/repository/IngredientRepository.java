package org.example.repository;

import org.example.model.Ingredient;
import org.example.model.Nutrients;

import java.util.List;

public class IngredientRepository {
    private List<Ingredient> ingredients = List.of(
            new Ingredient("Flour",new Nutrients(50, 20, 30)),
            new Ingredient("Egg",new Nutrients(10, 20, 30)),
            new Ingredient("Milk",new Nutrients(20, 20, 30)),
            new Ingredient("Sugar",new Nutrients(30, 20, 30)),
            new Ingredient("Salt",new Nutrients(40, 20, 30)),
            new Ingredient("Butter",new Nutrients(50, 20, 30)),
            new Ingredient("Tomato",new Nutrients(60, 20, 30)),
            new Ingredient("Cheese",new Nutrients(70, 20, 30))
    );

    private static IngredientRepository instance;

    private IngredientRepository() {

    }

    public static IngredientRepository getInstance() {
        if (instance == null) {
            instance = new IngredientRepository();
        }
        return instance;
    }

    public Ingredient getIngredientByName(String name) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().equalsIgnoreCase(name)) {
                return ingredient;
            }
        }
        return null;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void addIngredient(String name, Nutrients Nutrients) {
        ingredients.add(new Ingredient(name, Nutrients));
    }

}
