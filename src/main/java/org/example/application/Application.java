package org.example.application;

import org.example.model.*;
import org.example.model.enumeration.Importance;
import org.example.model.enumeration.Role;
import org.example.model.enumeration.UnitOfMeasure;
import org.example.service.IngredientService;
import org.example.service.RecipeService;
import org.example.service.UserService;
import org.example.template.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

import static java.lang.System.exit;

public abstract class Application {

    private static final IngredientService ingredientService = IngredientService.getInstance();
    private static final RecipeService recipeService = RecipeService.getInstance();
    private static final UserService userService = UserService.getInstance();
    private static Application instance;
    private static Menu mainMenu;

    private static Menu recipesMenu;

    private static Menu ingredientsMenu;

    private static Menu usersMenu;

    private static User currentUser;

    private static Menu inventoryMenu;

    private static Menu loginMenu;

    private static Menu favoriteRecipesMenu;


    static public void start() {

        mainMenu = new Menu(
                List.of(
                        new Pair<String, Consumer<Integer>>("Recipes", (Integer choice) -> {
                            recipesMenu.showMenu();
                        }),
                        new Pair<String, Consumer<Integer>>("Ingredients", (Integer choice) -> {
                            ingredientsMenu.showMenu();
                        }),
                        new Pair<String, Consumer<Integer>>("Users", (Integer choice) -> {
                            usersMenu.showMenu();
                        })
                )
        );
        recipesMenu = new Menu(
                List.of(
                        new Pair<String, Consumer<Integer>>("Display all recipes", (Integer choice) -> {
                            System.out.println(recipeService.getAllRecipes());
                        }),
                        new Pair<>("Add recipe", (Integer choice) -> {
                            Scanner scanner = new Scanner(System.in);
                            System.out.println("Enter recipe name:");
                            String name = scanner.nextLine().trim();
                            System.out.println("Enter recipe description:");
                            String description = scanner.nextLine().trim();
                            //let the user list the ingredients and quantities needed for the recipe
                            List<IngredientInRecipe> ingredients = new ArrayList<>();
                            Ingredient ingredient = null;
                            while (ingredient == null)
                            {
                                System.out.println("Enter ingredient name:");
                                ingredient = ingredientService.getIngredientByName(scanner.nextLine().trim());
                            }
                            System.out.println("Enter quantity:");
                            double quantity = scanner.nextDouble();
                            System.out.println("Enter unit: (g, kg, ml, l)");
                            String unitString = scanner.nextLine().trim();
                            System.out.println("Enter Importance:(Basic, Optional, Essential)");
                            String importanceString = scanner.nextLine().trim();
                            UnitOfMeasure unitOfMeasure = UnitOfMeasure.valueOf(unitString.toUpperCase());
                            Importance importance = Importance.valueOf(importanceString.toUpperCase());
                            ingredients.add(new IngredientInRecipe(ingredient, quantity, unitOfMeasure, importance));
                        }),
                        new Pair<>("Expand recipe", (Integer choice) -> { //show data about the recipe: ingredients amount, calories and nutrients
                            Scanner scanner = new Scanner(System.in);
                            System.out.println("Enter recipe name:");
                            String name = scanner.nextLine().trim();
                            Recipe recipe = recipeService.getRecipeByName(name);
                            System.out.println(recipe);
                            for (IngredientInRecipe ingredientInRecipe : recipe.getIngredients())
                            {
                                System.out.println(ingredientInRecipe + "\n");
                            }
                            Nutrients nutrients = recipeService.calculateNutrientsInRecipe(recipe);
                            System.out.println("Calories: " + nutrients.getCalories());
                            System.out.println(nutrients);

                        }),
                        new Pair<>("Favorite recipe", (Integer choice) -> {
                            favoriteRecipesMenu.showMenu();
                        })

                )
        );

        favoriteRecipesMenu = new Menu(
                List.of(
                        new Pair<>("Display favorite recipes", (Integer choice1) -> {
                            System.out.println(currentUser.getFavoriteRecipes());
                        }),
                        new Pair<>("Add favorite recipe", (Integer choice1) -> {
                            Scanner scanner = new Scanner(System.in);
                            System.out.println("Enter recipe name:");
                            String name = scanner.nextLine().trim();
                            userService.addRecipeToFavorite(currentUser, recipeService.getRecipeByName(name));
                        }),
                        new Pair<>("Remove favorite recipe", (Integer choice1) -> {
                            Scanner scanner = new Scanner(System.in);
                            System.out.println("Enter recipe name:");
                            String name = scanner.nextLine().trim();
                            userService.removeRecipeFromFavorite(currentUser, recipeService.getRecipeByName(name));
                        })
                )
        );

        ingredientsMenu = new Menu(
                List.of(
                        new Pair<String, Consumer<Integer>>("Display all ingredients", (Integer choice) -> {
                            System.out.println(ingredientService.getAllIngredients());
                        }),
                        new Pair<String, Consumer<Integer>>("Add ingredient", (Integer choice) -> {
                            Scanner scanner = new Scanner(System.in);
                            System.out.println("Enter ingredient name:");
                            String name = scanner.nextLine().trim();
                            //read nutrients
                            System.out.println("Enter proteins:");
                            double proteins = scanner.nextDouble();
                            System.out.println("Enter fats:");
                            double fats = scanner.nextDouble();
                            System.out.println("Enter carbohydrates:");
                            double carbohydrates = scanner.nextDouble();

                            ingredientService.addIngredient(name,new Nutrients( carbohydrates, proteins, fats));
                        }),
                        new Pair<String, Consumer<Integer>>("Inventory", (Integer choice) -> {
                            inventoryMenu.showMenu();
                        })


                )

        );

        usersMenu = new Menu(
                List.of(
                        new Pair<String, Consumer<Integer>>("Logout", (Integer choice) -> {
                            exit(0);
                        }),
                        new Pair<String, Consumer<Integer>>("Display all users", (Integer choice) -> {
                            System.out.println(userService.getAllUsers());
                        })
                )
        );

        inventoryMenu = new Menu(
                List.of(
                        new Pair<String, Consumer<Integer>>("Add ingredient", (Integer choice) -> {
                            Scanner scanner = new Scanner(System.in);
                            Ingredient ingredient = null;
                            while (ingredient == null)
                            {
                                System.out.println("Enter ingredient name:");
                                ingredient = ingredientService.getIngredientByName(scanner.nextLine().trim());
                            }

                            currentUser.getInventory().addIngredient(ingredient);
                        }),
                        new Pair<String, Consumer<Integer>>("Remove ingredient", (Integer choice) -> {
                            Scanner scanner = new Scanner(System.in);
                            Ingredient ingredient = null;
                            while (ingredient == null)
                            {
                                System.out.println("Enter ingredient name:");
                                ingredient = ingredientService.getIngredientByName(scanner.nextLine().trim());
                            }
                            currentUser.getInventory().removeIngredient(ingredient);
                        }),
                        new Pair<String, Consumer<Integer>>("Display inventory", (Integer choice) -> {
                            System.out.println(currentUser.getInventory());
                        })
                )
        );

        loginMenu = new Menu(
                List.of(
                        new Pair<String, Consumer<Integer>>("Register", (Integer choice) -> {
                            Scanner scanner = new Scanner(System.in);
                            while(currentUser == null) {
                                System.out.println("Enter username:");
                                String username = scanner.nextLine().trim();
                                userService.register(username);
                                logInUser(userService.FindUserByUsername(username));
                            }
                            mainMenu.showMenu();
                        }),
                        new Pair<String, Consumer<Integer>>("Login", (Integer choice) -> {
                            Scanner scanner = new Scanner(System.in);
                            while(currentUser == null) {
                                System.out.println("Enter username:");
                                String username = scanner.nextLine().trim();
                                logInUser(userService.FindUserByUsername(username));
                            }
                            mainMenu.showMenu();
                        })
                )
        );





        loginMenu.showMenu();

    }

    private static void logInUser(User user)
    {
        currentUser = user;
        EditMenusForAdmin();
        EditMenusForPremium();
        System.out.println("Logged in as " + currentUser.getUsername());
    }

    private static void EditMenusForAdmin()
    {
        if (currentUser != null && currentUser.getRole() == Role.ADMIN)
        {
            usersMenu.addOption(new Pair<String, Consumer<Integer>>("Delete user", (Integer choice) -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter username:");
                String username = scanner.nextLine().trim();
                userService.deleteUser(username);
            }));
            usersMenu.addOption(new Pair<String, Consumer<Integer>>("Give premium to user", (Integer choice) -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter username:");
                String username = scanner.nextLine().trim();
                userService.givePremium(username);
            }));
        }
    }

    private static void EditMenusForPremium()
    {
        if (currentUser != null && currentUser.isPremium())
        {
            recipesMenu.addOption(new Pair<>("Recomend recipe", (Integer choice) -> {
                List<Recipe> recipes = recipeService.recommendRecipes(currentUser.getFavoriteRecipes(), currentUser.getInventory());
                System.out.println(recipes);
            }));
        }
    }

}
