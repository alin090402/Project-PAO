package org.example.repository;

import org.example.model.IngredientInRecipe;
import org.example.model.Recipe;
import org.example.model.enumeration.Importance;
import org.example.model.enumeration.UnitOfMeasure;
import org.example.service.IngredientService;

import java.util.List;

public class RecipeRepository {
    private List<Recipe> recipes;

    private static RecipeRepository instance;

    private RecipeRepository() {
        recipes = List.of(
                new Recipe("Pancakes", "Delicious pancakes", List.of(
                        new IngredientInRecipe(IngredientRepository.getInstance().getIngredientByName("Floor"), 300, UnitOfMeasure.GRAM, Importance.PRIMARY),
                        new IngredientInRecipe(IngredientRepository.getInstance().getIngredientByName("Egg"), 100, UnitOfMeasure.GRAM, Importance.PRIMARY),
                        new IngredientInRecipe(IngredientRepository.getInstance().getIngredientByName("Milk"), 100, UnitOfMeasure.MILLILITER, Importance.PRIMARY),
                        new IngredientInRecipe(IngredientRepository.getInstance().getIngredientByName("Sugar"), 100, UnitOfMeasure.GRAM, Importance.BASIC),
                        new IngredientInRecipe(IngredientRepository.getInstance().getIngredientByName("Salt"), 100, UnitOfMeasure.GRAM, Importance.BASIC),
                        new IngredientInRecipe(IngredientRepository.getInstance().getIngredientByName("Butter"), 100, UnitOfMeasure.GRAM, Importance.OPTIONAL)
                )),
                new Recipe("Pasta", "Delicious pasta", List.of()),
                new Recipe("Pizza", "Delicious pizza", List.of())
        );
    }

    public static RecipeRepository getInstance() {
        if (instance == null) {
            instance = new RecipeRepository();
        }
        return instance;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }


}
