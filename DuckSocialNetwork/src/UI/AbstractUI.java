package UI;

import Service.DuckSocialService;
import java.util.Scanner;

public abstract class AbstractUI implements UI {
    protected DuckSocialService service;
    protected Scanner scanner;

    public AbstractUI(DuckSocialService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    // Metode comune pentru toate UI-urile
    protected void printHeader(String title) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println(" " + title);
        System.out.println("=".repeat(50));
    }

    protected void printSection(String sectionName) {
        System.out.println("\n" + "-".repeat(30));
        System.out.println(" " + sectionName);
        System.out.println("-".repeat(30));
    }

    protected int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
            System.out.print(prompt);
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return value;
    }

    protected long readLong(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextLong()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
            System.out.print(prompt);
        }
        long value = scanner.nextLong();
        scanner.nextLine(); // Consume newline
        return value;
    }

    protected double readDouble(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
            System.out.print(prompt);
        }
        double value = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        return value;
    }

    protected String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    protected boolean readBoolean(String prompt) {
        System.out.print(prompt + " (y/n): ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("y") || input.equals("yes");
    }

    protected void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    protected void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    @Override
    public abstract void run();

    @Override
    public abstract void showMenu();

    @Override
    public abstract void handleUserInput(); // Schimbăm la void
}