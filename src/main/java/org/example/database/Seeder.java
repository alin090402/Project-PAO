package org.example.database;

import org.example.model.*;
import org.example.model.enumeration.Importance;
import org.example.model.enumeration.Role;
import org.example.model.enumeration.UnitOfMeasure;
import org.example.repository.IngredientRepository;
import org.example.repository.RecipeRepository;
import org.example.repository.UserRepository;

import java.util.*;

public class Seeder {
    private static final Set<User> users = new TreeSet<>(List.of(
            new PremiumUser(1, Role.ADMIN, "admin", new ArrayList<>(), new Inventory(new ArrayList<>()), new Date(new Date().getTime() + 30 * 24 * 60 * 60 * 1000)),
            new User(2, Role.USER, "user", new ArrayList<>(), new Inventory(new ArrayList<>())),
            new User(3, Role.USER, "user2", new ArrayList<>(), new Inventory(new ArrayList<>())),
            new User(4, Role.USER, "user3", new ArrayList<>(), new Inventory(new ArrayList<>()))
    ));
    private static final List<Recipe> recipes = List.of(
            new Recipe("Pancakes", "Delicious pancakes", Set.of(
                    new IngredientInRecipe(IngredientRepository.getInstance().getIngredientByName("Flour"), 300, UnitOfMeasure.G, Importance.ESSENTIAL),
                    new IngredientInRecipe(IngredientRepository.getInstance().getIngredientByName("Egg"), 100, UnitOfMeasure.G, Importance.ESSENTIAL),
                    new IngredientInRecipe(IngredientRepository.getInstance().getIngredientByName("Milk"), 100, UnitOfMeasure.ML, Importance.ESSENTIAL),
                    new IngredientInRecipe(IngredientRepository.getInstance().getIngredientByName("Sugar"), 100, UnitOfMeasure.G, Importance.BASIC),
                    new IngredientInRecipe(IngredientRepository.getInstance().getIngredientByName("Salt"), 100, UnitOfMeasure.G, Importance.BASIC),
                    new IngredientInRecipe(IngredientRepository.getInstance().getIngredientByName("Butter"), 100, UnitOfMeasure.G, Importance.OPTIONAL)
            )),
            new Recipe("Pasta", "Delicious pasta", Set.of()),
            new Recipe("Pizza", "Delicious pizza", Set.of())
    );

    private static final List<Ingredient> ingredients = new ArrayList<>(List.of(
            new Ingredient("Flour",new Nutrients(50, 20, 30)),
            new Ingredient("Egg",new Nutrients(10, 20, 30)),
            new Ingredient("Milk",new Nutrients(20, 20, 30)),
            new Ingredient("Sugar",new Nutrients(30, 20, 30)),
            new Ingredient("Salt",new Nutrients(40, 20, 30)),
            new Ingredient("Butter",new Nutrients(50, 20, 30)),
            new Ingredient("Tomato",new Nutrients(60, 20, 30)),
            new Ingredient("Cheese",new Nutrients(70, 20, 30))
    ));

    public static void main(String[] args) {
        Database database = Database.getInstance();
        try {
            database.init();
            for (User user : users) {
                UserRepository.getInstance().addUser(user);
            }
            for (Ingredient ingredient : ingredients) {
                IngredientRepository.getInstance().addIngredient(ingredient);
            }
            for (Recipe recipe : recipes) {
                RecipeRepository.getInstance().addRecipe(recipe);
            }
        }
        catch (Exception e)
        {
            System.out.println("Could not connect to database");
            System.out.println(e.getMessage());
        }
    }
}
