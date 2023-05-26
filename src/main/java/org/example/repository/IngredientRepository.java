package org.example.repository;

import org.example.database.Database;
import org.example.model.Ingredient;
import org.example.model.Nutrients;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredientRepository {

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
        try{
            String getIngredient = "SELECT name, carbohydrates, proteins, fats FROM ingredients WHERE name = ?;";
            PreparedStatement preparedStatement = Database.getInstance().getPreparedStatement(getIngredient);
            preparedStatement.setString(1, name);
            ResultSet res = Database.getInstance().executePreparedQuery(preparedStatement);
            if (res.next()) {
                double carbohydrates = res.getDouble("carbohydrates");
                double proteins = res.getDouble("proteins");
                double fats = res.getDouble("fats");
                return new Ingredient(name, new Nutrients(carbohydrates, proteins, fats));
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Ingredient> getIngredients() {
        List<Ingredient> answer = new ArrayList<>();
        try {
            String getIngredients = "SELECT name, carbohydrates, proteins, fats FROM ingredients;";
            ResultSet res = Database.getInstance().executeQuery(getIngredients);
            while (res.next()) {
                String name = res.getString("name");
                double carbohydrates = res.getDouble("carbohydrates");
                double proteins = res.getDouble("proteins");
                double fats = res.getDouble("fats");
                answer.add(new Ingredient(name, new Nutrients(carbohydrates, proteins, fats)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return answer;

    }

    public void addIngredient(Ingredient ingredient) {
        try {
            String addIngredient = "INSERT INTO ingredients (name, carbohydrates, proteins, fats) VALUES (?, ?, ?, ?);";
            PreparedStatement preparedStatement = Database.getInstance().getPreparedStatement(addIngredient);
            preparedStatement.setString(1, ingredient.getName());
            preparedStatement.setDouble(2, ingredient.getNutrients().getCarbohydrates());
            preparedStatement.setDouble(3, ingredient.getNutrients().getProteins());
            preparedStatement.setDouble(4, ingredient.getNutrients().getFats());
            Database.getInstance().executePrepared(preparedStatement);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //ingredients.add(new Ingredient(name, Nutrients));
    }

    public Ingredient getIngredientById(int id) {
        try{
            String getIngredient = "SELECT name, carbohydrates, proteins, fats FROM ingredients WHERE id = ?;";
            PreparedStatement preparedStatement = Database.getInstance().getPreparedStatement(getIngredient);
            preparedStatement.setInt(1, id);
            ResultSet res = Database.getInstance().executePreparedQuery(preparedStatement);
            if (res.next()) {
                String name = res.getString("name");
                double carbohydrates = res.getDouble("carbohydrates");
                double proteins = res.getDouble("proteins");
                double fats = res.getDouble("fats");
                return new Ingredient(name, new Nutrients(carbohydrates, proteins, fats));
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
