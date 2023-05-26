package org.example.model;

/**
 * It is entity class that represents the ingredients.
 */
public class Ingredient extends BaseModel {
    private String name;

    private Nutrients nutrients;

    public Ingredient(String name, Nutrients Nutrients) {
        super();
        this.name = name;
        this.nutrients = Nutrients;
    }

    public String getName() {
        return name;
    }

    public Nutrients getNutrients() {
        return nutrients;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNutrients(Nutrients nutrients) {
        this.nutrients = nutrients;
    }

    public String toString()
    {
        return name;
    }
}
