package org.example.model;

import org.example.model.enumeration.Importance;
import org.example.model.enumeration.UnitOfMeasure;

public class IngredientInRecipe {
    private Ingredient ingredient;
    private double amount;

    private UnitOfMeasure unitOfMeasure;
    private Importance importance;

    public IngredientInRecipe(Ingredient ingredient, double amount, UnitOfMeasure unitOfMeasure, Importance importance) {
        this.ingredient = ingredient;
        this.amount = amount;
        this.unitOfMeasure = unitOfMeasure;
        this.importance = importance;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public double getAmount() {
        return amount;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public Importance getImportance() {
        return importance;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    public String toString()
    {
        return ingredient.toString() + " " + amount + " " + unitOfMeasure.toString() + "   " + importance.toString();
    }


}
