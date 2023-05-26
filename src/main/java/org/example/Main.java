package org.example;

import org.example.application.Application;
import org.example.application.Menu;
import org.example.database.Database;
import org.example.repository.IngredientRepository;
import org.example.template.Pair;
import org.example.utils.ConfigLoader;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;

public class Main {

    public static void main(String[] args) {
//        Menu menu = new Menu(List.of(
//                new Pair<String, Consumer<Integer>>("print first option", (Integer choice) -> {
//                    System.out.println("first option");
//                }),
//                new Pair<String, Consumer<Integer>>("print second option", (Integer choice) -> {
//                    System.out.println("second option");
//                }),
//                new Pair<String, Consumer<Integer>>("print third option", (Integer choice) -> {
//                    System.out.println("third option");
//                })
//            )
//        );
//        menu.showMenu();
        Database database = Database.getInstance();
        try {
            database.init();
        }
        catch (SQLException e)
        {
            System.out.println("Could not connect to database");
            System.out.println(e.getMessage());
        }
        Application.start();
    }

}