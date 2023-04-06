package org.example.service;

import org.example.model.Ingredient;
import org.example.model.Nutrients;
import org.example.repository.IngredientRepository;

import java.util.List;

public class IngredientService {
    private IngredientRepository ingredientRepository;

    private IngredientService() {
        ingredientRepository = IngredientRepository.getInstance();
    }

    public static IngredientService instance;

    public static IngredientService getInstance() {
        if (instance == null) {
            instance = new IngredientService();
        }
        return instance;
    }

    public void addIngredient(String name, Nutrients nutrients) {
        ingredientRepository.addIngredient(name, nutrients);
    }

    public Ingredient getIngredientByName(String name) {
        return ingredientRepository.getIngredientByName(name);
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.getIngredients();
    }
}
