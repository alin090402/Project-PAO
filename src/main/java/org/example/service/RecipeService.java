package org.example.service;

import org.example.model.IngredientInRecipe;
import org.example.model.Inventory;
import org.example.model.Nutrients;
import org.example.model.Recipe;
import org.example.model.enumeration.Importance;
import org.example.model.enumeration.UnitOfMeasure;
import org.example.repository.RecipeRepository;

import java.util.ArrayList;
import java.util.List;

public class RecipeService {

    private RecipeRepository recipeRepository;

    private RecipeService() {
        recipeRepository = RecipeRepository.getInstance();
    }

    public static RecipeService instance;

    public static RecipeService getInstance() {
        if (instance == null) {
            instance = new RecipeService();
        }
        return instance;
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.getRecipes();
    }

    public Recipe getRecipeByName(String name) {
        return recipeRepository.getRecipeByName(name);
    }

    public List<Recipe> recommendRecipes(List<Recipe> favoriteRecipes, Inventory inventory) {
        List<Recipe> recipes = new ArrayList<>(recipeRepository.getRecipes());
        recipes.sort((recipe1, recipe2) -> {
            double score1 = calculateScore(recipe1, favoriteRecipes, inventory);
            double score2 = calculateScore(recipe2, favoriteRecipes, inventory);
            return Double.compare(score2, score1);
        });

        for (Recipe recipe : recipes) {
            System.out.println(recipe.getName() + " " + calculateScore(recipe, favoriteRecipes, inventory));
        }
        recipes = recipes.subList(0, 3);
        return recipes;
    }

    private double calculateScore(Recipe recipe, List<Recipe> favoriteRecipes, Inventory inventory) {
        double score = 0;
        double scoreForFavorite = 100;

        if (favoriteRecipes.contains(recipe)) {
            score += 100;
        }

        int totalWeight = 0; // 4 for essential, 2 for basic, 1 for optional
        for (IngredientInRecipe ingredientInRecipe : recipe.getIngredients()) {
            switch (ingredientInRecipe.getImportance()) {
                case ESSENTIAL:
                    totalWeight += 4;
                    break;
                case OPTIONAL:
                    totalWeight += 2;
                    break;
            }
        }
        if (totalWeight == 0) {
            totalWeight = 1;
        }
        double scoreForOptionalIngredient = 100 / totalWeight;
        double scoreForEssentialIngredient = 200 / totalWeight;

        for (IngredientInRecipe ingredientInRecipe : recipe.getIngredients()) {
            if (inventory.getIngredients().contains(ingredientInRecipe.getIngredient())) {
                switch (ingredientInRecipe.getImportance()) {
                    case ESSENTIAL:
                        score += scoreForEssentialIngredient;
                        break;
                    case OPTIONAL:
                        score += scoreForOptionalIngredient;
                        break;
                }
            }
            else {
                switch (ingredientInRecipe.getImportance()) {
                    case ESSENTIAL:
                        score -= 2 * scoreForEssentialIngredient;
                        break;
                }
            }
        }
        return score;
    }
    public Nutrients calculateNutrientsInRecipe(Recipe recipe)
    {
        double proteins = 0;
        double fats = 0;
        double carbohydrates = 0;
        for (IngredientInRecipe ingredientInRecipe : recipe.getIngredients()) {
            Nutrients ingredientNutrients = calculateNutrients(ingredientInRecipe);
            if (ingredientNutrients == null) {
                continue;
            }
            proteins += ingredientNutrients.getProteins();
            fats += ingredientNutrients.getFats();
            carbohydrates += ingredientNutrients.getCarbohydrates();
        }
        return new Nutrients(proteins, fats, carbohydrates);
    }

    private Nutrients calculateNutrients(IngredientInRecipe ingredientInRecipe)
    {
        if (ingredientInRecipe.getIngredient().getNutrients() == null) {
            return null;
        }
        double weight = ingredientInRecipe.getAmount();
        switch (ingredientInRecipe.getUnitOfMeasure()) {
            case G:
                weight /= 100;
                break;
            case ML:
                weight /= 100;
                break;
            case KG:
                weight *= 10.0;
                break;
            case L:
                weight *= 10.0;
                break;
            default:
                weight *= 1.0;
                break;
        }
        double protein = ingredientInRecipe.getIngredient().getNutrients().getProteins() * weight;
        double fats = ingredientInRecipe.getIngredient().getNutrients().getFats() * weight;
        double carbohydrates = ingredientInRecipe.getIngredient().getNutrients().getCarbohydrates() * weight;
        return new Nutrients(protein, fats, carbohydrates);
    }
}
