package org.example.repository;

import org.example.database.Database;
import org.example.model.IngredientInRecipe;
import org.example.model.Recipe;
import org.example.model.enumeration.Importance;
import org.example.model.enumeration.UnitOfMeasure;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecipeRepository {

    private static RecipeRepository instance;

    public static RecipeRepository getInstance() {
        if (instance == null) {
            instance = new RecipeRepository();
        }
        return instance;
    }

    public List<Recipe> getRecipes() {
        try {
            List<Recipe> recipes = new ArrayList<Recipe>();
            String getRecipe = "SELECT id, name, description FROM recipes;";
            ResultSet res = Database.getInstance().executeQuery(getRecipe);
            while (res.next()) {
                int id_recipe = res.getInt("id");
                String name = res.getString("name");
                String description = res.getString("description");
                Recipe recipe = new Recipe(name, description, new HashSet<>());
                String getIngredients = "SELECT ingredient_id, quantity, unit, importance FROM recipe_ingredient " +
                        "WHERE recipe_id = " + id_recipe + ";";
                ResultSet ingredients = Database.getInstance().executeQuery(getIngredients);
                while (ingredients.next()) {
                    int id = ingredients.getInt("ingredient_id");
                    double quantity = ingredients.getDouble("quantity");
                    UnitOfMeasure unit = UnitOfMeasure.valueOf(ingredients.getString("unit"));
                    Importance importance = Importance.valueOf(ingredients.getString("importance"));
                    assert recipe != null;
                    recipe.getIngredients().add(new IngredientInRecipe(IngredientRepository.getInstance().getIngredientById(id), quantity, unit, importance));
                }
                recipes.add(recipe);
            }
            return recipes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Recipe getRecipeByName(String name) {
        try {
            Recipe recipe = null;
            int id_recipe = 0;
            String getRecipe = "SELECT id, name, description FROM recipes WHERE name = ?;";
            PreparedStatement preparedStatement = Database.getInstance().getPreparedStatement(getRecipe);
            preparedStatement.setString(1, name);
            ResultSet res = Database.getInstance().executePreparedQuery(preparedStatement);
            if (res.next()) {
                id_recipe = res.getInt("id");
                String description = res.getString("description");
                recipe = new Recipe(name, description, new HashSet<>());
            }
            String getIngredients = "SELECT ingredient_id, quantity, unit, importance FROM recipe_ingredient " +
                    "WHERE recipe_id = "+ id_recipe + ";";
            ResultSet ingredients = Database.getInstance().executeQuery(getIngredients);
            while (ingredients.next()) {
                int id = ingredients.getInt("ingredient_id");
                double quantity = ingredients.getDouble("quantity");
                UnitOfMeasure unit = UnitOfMeasure.valueOf(ingredients.getString("unit"));
                Importance importance = Importance.valueOf(ingredients.getString("importance"));
                assert recipe != null;
                recipe.getIngredients().add(new IngredientInRecipe(IngredientRepository.getInstance().getIngredientById(id), quantity, unit, importance));
            }
            return recipe;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addRecipe(Recipe recipe) {

        try {
            String recipeSql = "INSERT INTO recipes (name, description) VALUES (?, ?)";
            PreparedStatement recipeStatement = Database.getInstance().getPreparedStatement(recipeSql);
            recipeStatement.setString(1, recipe.getName());
            recipeStatement.setString(2, recipe.getDescription());
            Database.getInstance().executePrepared(recipeStatement);

            String recipeIngredientSql =
                    "INSERT INTO " +
                            "recipe_ingredient (recipe_id, ingredient_id, quantity, unit, importance) " +
                            "VALUES ((SELECT id FROM recipes WHERE recipes.name = ? ), " +
                            "(SELECT id FROM ingredients WHERE ingredients.name = ? ), ?, ?, ?)";
            PreparedStatement recipeIngredientStatement = Database.getInstance().getPreparedStatement(recipeIngredientSql);
            recipeIngredientStatement.setString(1, recipe.getName());
            for (IngredientInRecipe ingredientInRecipe : recipe.getIngredients()) {
                recipeIngredientStatement.setString(2, ingredientInRecipe.getIngredient().getName());
                recipeIngredientStatement.setDouble(3, ingredientInRecipe.getAmount());
                recipeIngredientStatement.setString(4, ingredientInRecipe.getUnitOfMeasure().toString());
                recipeIngredientStatement.setString(5, ingredientInRecipe.getImportance().toString());
                Database.getInstance().executePrepared(recipeIngredientStatement);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //recipes.add(recipe);
    }

}
