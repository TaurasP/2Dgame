import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        startGame();
    }

    public static void startGame() {
        boolean exit = false;
        int choice;
        showStartMenu();

        while(!exit) {
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // START GAME
                    System.out.println("\nGame started.");
                    showStartMenu();
                    break;
                case 2:
                    // CREATE / IMPORT MAP
                    System.out.println("\nPlace for map create / import.");
                    showStartMenu();
                    break;
                case 3:
                    // HIGH SCORE
                    System.out.println("\nPlace for high scores.");
                    showStartMenu();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("\nPoint does not exist.");
                    showStartMenu();
                    break;
            }
        }
    }

    public static void showStartMenu() {
        System.out.println("\n---------------------------MENU---------------------------");
        System.out.println("1. START GAME");
        System.out.println("2. CREATE / IMPORT MAP");
        System.out.println("3. HIGH SCORE");
        System.out.println("----------------------------------------------------------");
        System.out.println("0. EXIT GAME.");
        System.out.println("----------------------------------------------------------");

        System.out.print("Choose number from MENU:");
    }
}
