import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String EASY_LEVEL = "EASY";
    private static final String MEDIUM_LEVEL = "MEDIUM";
    private static final String HARD_LEVEL = "HARD";

    private static String level;

    public static List<Map> maps = new ArrayList<>();
    public static Map map1 = new Map("Lietuva");
    public static Map map2 = new Map("Latvija");
    public static Location location1 = new Location("Vilnius");
    public static Location location2 = new Location("Klaipeda");
    public static Location location3 = new Location("Ryga");
    public static Location location4 = new Location("Ventspils");

    public static String getLevel() {
        return level;
    }

    public Game() {
        level = EASY_LEVEL;
        maps.add(map1);
        maps.add(map2);
        map1.locations.add(location1);
        map1.locations.add(location2);
        map2.locations.add(location3);
        map2.locations.add(location4);
    }

    public static void start(Player player) {

        boolean exit = false;

        showStartMenu();

        while(!exit) {

            switch (readUserInputChar()) {
                case '1':
                    // START GAME
                    selectMap(player);
                    //showStartMenu();
                    break;
                case '2':
                    // CREATE / IMPORT MAP
                    System.out.println("\nPlace for map create / import.");
                    //showStartMenu();
                    break;
                case '3':
                    // HIGH SCORE
                    System.out.println("\nPlace for high scores.");
                    //showStartMenu();
                    break;
                case 'E':
                    exit = true;
                    break;
                default:
                    System.out.println("\nSelected number/letter does not exist.");
                    showStartMenu();
                    break;
            }
        }
    }

    private static void selectMap(Player player) {
        String userInput;
        boolean isFound = false;

        showMapsOptions();
        userInput = readUserInputString();

        for (int i = 0; i < maps.size(); i++) {
            if (userInput.equalsIgnoreCase("B")) {
                selectLevel(player);
            } else if (userInput.equalsIgnoreCase(String.valueOf(i))) {
                player.setMap(maps.get(i));
                selectLevel(player);
                isFound = true;
            }
        }
        if(!isFound) {
            System.out.println("\nSelected number/letter does not exist.");
            selectMap(player);
        }
    }

    public static void selectLevel(Player player) {
        showLevelsOptions();

        switch (readUserInputChar()) {
            case '1':
                level = EASY_LEVEL;
                enterPlayerName(player);
                showGameMenu();
                break;
            case '2':
                level = MEDIUM_LEVEL;
                enterPlayerName(player);
                showGameMenu();
                break;
            case '3':
                level = HARD_LEVEL;
                enterPlayerName(player);
                showGameMenu();
                break;
            case 'B':
                selectMap(player);
                break;
            default:
                System.out.println("\nSelected number/letter does not exist.");
                selectLevel(player);
                break;
        }
    }

    private static void enterPlayerName(Player player) {
        System.out.print("Enter player's name: ");
        player.setName(readUserInputString());

        // TESTING DATA - START
        System.out.println("\nSelected map: " + player.getMap().getName());

        for(Location l : player.getMap().locations) {
            System.out.println("Location: " + l.getName());
        }

        System.out.println("Selected level: " + getLevel());

        System.out.println("Player's name: " + player.getName());
        // END
    }

    public static void showStartMenu() {
        System.out.println("\n---------------------------START MENU---------------------------");
        System.out.println("1. START GAME");
        System.out.println("2. CREATE / IMPORT MAP");
        System.out.println("3. HIGH SCORE");
        System.out.println("----------------------------------------------------------------");
        System.out.println("E. EXIT GAME");
        System.out.println("----------------------------------------------------------------");

        System.out.print("Choose number/letter:");
    }

    public static void showMapsOptions() {
        System.out.println("\n----------------------------MAPS----------------------------");
        for (int i = 0; i < maps.size(); i++) {
            System.out.println(i + ". " + maps.get(i).getName().toUpperCase());
        }

        System.out.println("------------------------------------------------------------");
        System.out.println("B. BACK");
        System.out.println("------------------------------------------------------------");

        System.out.print("Choose number/letter:");
    }

    public static void showLevelsOptions() {
        System.out.println("\n---------------------------LEVELS---------------------------");
        System.out.println("1. EASY");
        System.out.println("2. MEDIUM");
        System.out.println("3. HARD");
        System.out.println("------------------------------------------------------------");
        System.out.println("B. BACK");
        System.out.println("E. EXIT GAME");
        System.out.println("------------------------------------------------------------");

        System.out.print("Choose number/letter:");
    }

    public static void showGameMenu() {
        System.out.println("\n---------------------------GAME MENU---------------------------");
        System.out.println("1. LOCATIONS");
        System.out.println("2. INVENTORY");
        System.out.println("3. ACHIEVEMENTS");
        System.out.println("---------------------------------------------------------------");
        System.out.println("M. GO TO START MENU");
        System.out.println("E. EXIT GAME");
        System.out.println("---------------------------------------------------------------");

        System.out.print("Choose number/letter:");
    }

    public static char readUserInputChar() {
        char userInput = Character.toUpperCase(scanner.next().charAt(0));
        scanner.nextLine();

        return userInput;
    }

    public static String readUserInputString() {
        String userInput =  scanner.nextLine().toUpperCase();

        return userInput;
    }
}
