package org.example.model;

/**
 * It is a class that stores the nutritional value of ingredients per 100 grams/100 ml.
 */
public class Nutrients {
    private final double carbohydrates;
    private final double proteins;
    private final double fats;

    public Nutrients(double carbohydrates, double proteins, double fats) {
        this.carbohydrates = carbohydrates;
        this.proteins = proteins;
        this.fats = fats;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public double getProteins() {
        return proteins;
    }

    public double getFats() {
        return fats;
    }

    public double getCalories() {
        return 4 * proteins + 9 * carbohydrates + 4 * fats;
    }

    @Override
    public String toString() {
        return "Nutrients{" +
                "carbohydrates=" + carbohydrates +
                ", proteins=" + proteins +
                ", fats=" + fats +
                '}';
    }
}
