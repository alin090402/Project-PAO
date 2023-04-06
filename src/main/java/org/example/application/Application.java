package org.example.application;

import org.example.model.User;
import org.example.service.IngredientService;
import org.example.service.RecipeService;
import org.example.service.UserService;
import org.example.template.Pair;

import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

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
                        })

                )
        );

        ingredientsMenu = new Menu(
                List.of(
                        new Pair<String, Consumer<Integer>>("Display all ingredients", (Integer choice) -> {
                            System.out.println(ingredientService.getAllIngredients());
                        })

                )
        );

        usersMenu = new Menu(
                List.of(
                        new Pair<String, Consumer<Integer>>("Register", (Integer choice) -> {
                            Scanner scanner = new Scanner(System.in);
                            System.out.println("Enter username:");
                            String username = scanner.nextLine().trim();
                            userService.register(username);
                        }),
                        new Pair<String, Consumer<Integer>>("Login", (Integer choice) -> {
                            Scanner scanner = new Scanner(System.in);
                            System.out.println("Enter username:");
                            String username = scanner.nextLine().trim();
                            currentUser = userService.FindUserByUsername(username);
                        }),
                        new Pair<String, Consumer<Integer>>("Logout", (Integer choice) -> {
                            currentUser = null;
                        }),
                        new Pair<String, Consumer<Integer>>("Display all users", (Integer choice) -> {
                            System.out.println(userService.getAllUsers());
                        })
                )
        );



        mainMenu.showMenu();

    }


}
