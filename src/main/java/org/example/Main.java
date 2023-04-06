package org.example;

import org.example.application.Application;
import org.example.application.Menu;
import org.example.template.Pair;

import java.util.List;
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
        Application.start();
    }

}