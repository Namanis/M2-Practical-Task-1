package org.example.menu;

import org.example.model.*;
import org.example.shelter.Shelter;

import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    private final Shelter<Animal> shelter;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleMenu(Shelter<Animal> shelter) {
        this.shelter = shelter;
    }

    public void start() {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1 -> addAnimal();
                case 2 -> printAnimals(shelter.getAllAnimals());
                case 3 -> findBySpecies();
                case 4 -> printAnimals(shelter.findAvailableAnimals());
                case 5 -> markAdopted();
                case 0 -> {
                    running = false;
                    System.out.println("Thank you and Goodbye!");
                }
                default -> System.out.println("Invalid option, try again.");
            }
            System.out.println();
        }
    }

    private static final List<MenuOption> OPTIONS = List.of(
            new MenuOption(1, "Add animal"),
            new MenuOption(2, "List all animals"),
            new MenuOption(3, "Find animals by species"),
            new MenuOption(4, "List available animals"),
            new MenuOption(5, "Mark animal as adopted"),
            new MenuOption(0, "Exit")
    );

    private void printMenu() {
        for (MenuOption option : OPTIONS) {
            System.out.println(option.number() + ". " + option.label());
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    private void addAnimal() {
        System.out.println("""
                Species:
                1. Dog
                2. Cat
                3. Bird""");
        int type = readInt("Choose species: ");
        String name = readLine("Name: ");
        int age = readInt("Age: ");

        Animal animal = switch (type) {
            case 1 -> new Dog(new AnimalId(), name, age);
            case 2 -> new Cat(new AnimalId(), name, age);
            case 3 -> new Bird(new AnimalId(), name, age);
            default -> null;
        };

        if (animal == null) {
            System.out.println("Invalid species.");
            return;
        }
        shelter.addAnimal(animal);
        System.out.println("Animal added: " + animal);
    }

    private void findBySpecies() {
        String species = readLine("Species: ");
        printAnimals(shelter.findBySpecies(species));
    }

    private void markAdopted() {
        String id = readLine("Animal ID: ");
        try {
            shelter.markAsAdopted(id);
            System.out.println("Animal marked as adopted.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void printAnimals(List<Animal> animals) {
        if (animals.isEmpty()) {
            System.out.println("No animals found.");
            return;
        }
        animals.forEach(System.out::println);
    }

    private String readLine(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("Input cannot be empty.");
        }
    }
}
