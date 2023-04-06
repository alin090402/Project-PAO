package org.example.service;

import org.example.model.Recipe;
import org.example.repository.RecipeRepository;

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
}
