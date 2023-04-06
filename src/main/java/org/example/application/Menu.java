package org.example.application;

import org.example.template.Pair;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

public class Menu {
    private List<Pair<String, Consumer<Integer>>> menuItems;

    public Menu(List<Pair<String, Consumer<Integer>>> menuItems) {
        this.menuItems = menuItems;
    }

    public void showMenu() {

        while(true) {
            System.out.println("0. Exit");
            for (int i = 0; i < menuItems.size(); i++) {
                System.out.println((i + 1) + ". " + menuItems.get(i).first);
            }
            System.out.print("Enter your choice (0-" + menuItems.size() + "): ");
            int choice = AcceptInput(0, menuItems.size());
            if (choice == 0) {
                break;
            }
            menuItems.get(choice - 1).second.accept(choice);
        }
    }

    private int AcceptInput(int min, int max) {
        Scanner myInput = new Scanner( System.in );
        Boolean isInputValid = false;
        int choice = 0;
        while(!isInputValid)
        {
            try {
                while (!myInput.hasNextInt()) {
                    System.out.println("Invalid input. Please try again.");
                    myInput.next();
                }
                choice = myInput.nextInt();
                if (choice >= min && choice <= max) {
                    isInputValid = true;
                } else {
                    System.out.println("Invalid input. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
            }
        }
        return choice;
    }


}
