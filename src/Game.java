import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    private static final Scanner SCANNER = new Scanner(System.in);
    public static final String WEAPON = "Weapon";
    public static final String ARMOR = "Armor";
    public static final String POTION = "Potion";
    private static final String EASY_LEVEL = "EASY";
    private static final String MEDIUM_LEVEL = "MEDIUM";
    private static final String HARD_LEVEL = "HARD";
    private static final int ENEMIES_EASY_LEVEL = 2;
    private static final int ENEMIES_MEDIUM_LEVEL = 4;
    private static final int ENEMIES_HARD_LEVEL = 6;

    private static String level;

    //public static List<Achievement> achievements = new ArrayList<>();

    public static Achievement achievement1 = new Achievement("New player.");
    public static Achievement achievement2 = new Achievement("5 enemies killed.");

    public static List<Map> maps = new ArrayList<>();

    public static Map map1 = new Map("Lietuva");
    public static Map map2 = new Map("Latvija");

    public static Location location1 = new Location("Vilnius");
    public static Location location2 = new Location("Klaipeda");
    public static Location location3 = new Location("Ryga");
    public static Location location4 = new Location("Ventspils");

    public static int getEnemiesNumberFromLevel() {
        int enemiesNumber = 0;

        if(level == EASY_LEVEL) {
            enemiesNumber = ENEMIES_EASY_LEVEL;
        } else if(level == MEDIUM_LEVEL) {
            enemiesNumber = ENEMIES_MEDIUM_LEVEL;
        } else if (level == HARD_LEVEL) {
            enemiesNumber = ENEMIES_HARD_LEVEL;
        }
        return enemiesNumber;
    }

    public static void generateEnemies() {
        for (int i = 0; i < maps.size(); i++) {
            for (int j = 0; j < maps.get(i).locations.size(); j++) {
                for (int k = 0; k < getEnemiesNumberFromLevel(); k++) {
                    maps.get(i).locations.get(j).enemies.add(new Enemy());
                }
            }
        }
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
        player.achievementsList.add(achievement1);
        player.achievementsList.add(achievement2);

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
                //showGameMenu();
                break;
            case '2':
                level = MEDIUM_LEVEL;
                enterPlayerName(player);
                //showGameMenu();
                break;
            case '3':
                level = HARD_LEVEL;
                enterPlayerName(player);
                //showGameMenu();
                break;
            case 'B':
                selectMap(player);
                break;
            case 'M':
                showStartMenu();
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

        generateEnemies();
        selectFromGameMenu(player);
    }

    private static void selectFromGameMenu(Player player) {
        showGameMenu();

        switch (readUserInputChar()) {
            case '1':
                selectLocation(player);
                break;
            case '2':
                selectInventory(player);
                break;
            case '3':
                showAchievements(player);
                break;
            case 'M':
                showStartMenu();
                break;
            /*case 'E':
                exit = true;
                break;*/
            default:
                System.out.println("\nSelected number/letter does not exist.");
                selectFromGameMenu(player);
                break;
        }
    }

    private static void selectLocation(Player player) {
        String userInput;
        boolean isSelected = false;

        showLocations(player);

        userInput = readUserInputString();

        for (int i = 0; i < player.getMap().locations.size(); i++) {
            if (userInput.equalsIgnoreCase("B")) {
                selectFromGameMenu(player);
                isSelected = true;

            } else if (userInput.equalsIgnoreCase(String.valueOf(i))) {
                player.getMap().setLastLocation(player.getMap().locations.get(i));
                attackEnemy(player);

                isSelected = true;
            }
        }
        if(!isSelected) {
            System.out.println("\nSelected number/letter does not exist.");
            selectLocation(player);
        }
    }

    private static void selectInventory(Player player) {
        showInventory();

        switch (readUserInputChar()) {
            case '1':
                selectItem(player, WEAPON);
                break;
            case '2':
                selectItem(player, ARMOR);
                break;
            case '3':
                selectItem(player, POTION);
                break;
            case 'B':
                selectFromGameMenu(player);
                break;
            default:
                System.out.println("\nSelected number/letter does not exist.");
                selectInventory(player);
                break;
        }
    }

    public static void selectItem(Player player, String itemType) {
        String userInput;
        boolean isSelected = false;
        Item selectedItem;

        showItems(player, itemType);

        List<Item> selectedItemList = getSelectedItemList(player, itemType);

        userInput = readUserInputString();

        for (int i = 0; i < selectedItemList.size(); i++) {
            if (userInput.equalsIgnoreCase("B")) {
                selectInventory(player);
                isSelected = true;

            } else if (userInput.equalsIgnoreCase(String.valueOf(i))) {
                selectedItem = selectedItemList.get(i);
                selectItemAction(player, itemType, selectedItem);

                isSelected = true;
            }
        }
        if(!isSelected) {
            System.out.println("\nSelected number/letter does not exist.");
            selectItem(player, itemType);
        }
    }

    public static void selectItemAction(Player player, String itemType, Item item) {
        showItemAction(item);

        switch (readUserInputChar()) {
            case '1':
                // EQUIP
                equipItem(player, item);
                selectItem(player, itemType);
                break;
            case '2':
                // SELL
                sellItem(player, item);
                selectItem(player, itemType);
                break;
            case 'B':
                // BACK
                selectItem(player, itemType);
                break;
            default:
                System.out.println("\nSelected number/letter does not exist.");
                selectItemAction(player, itemType, item);
                break;
        }
    }

    public static void equipItem(Player player, Item item) {
        if (!item.isEquipped) {
            if (isOtherItemEquipped(player, item)) {
                if (item.type == WEAPON) {
                    unequipEquippedItem(player, item);
                    item.isEquipped = true;
                    player.damagePoints += item.damage;
                } else if (item.type == ARMOR) {
                    unequipEquippedItem(player, item);
                    item.isEquipped = true;
                    player.armorPoints += item.armor;
                }
            } else {
                if (item.type == WEAPON) {
                    item.isEquipped = true;
                    player.damagePoints += item.damage;
                } else if (item.type == ARMOR) {
                    item.isEquipped = true;
                    player.armorPoints += item.armor;
                }
            }
        } else {
            System.out.println(item.name + " is already equipped.");
        }

        if (item.type == POTION) {
            usePotion(player, item);
        }
    }

    public static boolean isOtherItemEquipped(Player player, Item item) {
        int counter = 0;

        if(item.type == WEAPON) {
            for (int i = 0; i < player.weaponsList.size(); i++) {
                if(player.weaponsList.get(i).isEquipped) {
                    counter += 1;
                }
            }
        } else if(item.type == ARMOR) {
            for (int i = 0; i < player.armorList.size(); i++) {
                if(player.armorList.get(i).isEquipped) {
                    counter += 1;
                }
            }
        }

        return counter == 1 ? true : false;
    }

    public static void unequipEquippedItem(Player player, Item item) {
        if(item.type == WEAPON) {
            for (int i = 0; i < player.weaponsList.size(); i++) {
                if(player.weaponsList.get(i).isEquipped) {
                    player.weaponsList.get(i).isEquipped = false;
                }
            }
        } else if(item.type == ARMOR) {
            for (int i = 0; i < player.armorList.size(); i++) {
                if(player.armorList.get(i).isEquipped) {
                    player.armorList.get(i).isEquipped = false;
                }
            }
        }
    }

    public static void sellItem(Player player, Item item) {
        if(item.type == WEAPON) {
            for (int i = 0; i < player.weaponsList.size(); i++) {
                if(player.weaponsList.get(i).getName() == item.name) {
                    item.isEquipped = false;
                    player.weaponsList.remove(i);
                    player.damagePoints -= item.damage;
                    player.gold += item.price;
                }
            }
        } else if(item.type == ARMOR) {
            for (int i = 0; i < player.armorList.size(); i++) {
                if(player.armorList.get(i).getName() == item.name) {
                    item.isEquipped = false;
                    player.armorList.remove(i);
                    player.armorPoints -= item.armor;
                    player.gold += item.price;
                }
            }
        } else if(item.type == POTION) {
            for (int i = 0; i < player.potionsList.size(); i++) {
                if(player.potionsList.get(i).getName() == item.name) {
                    player.potionsList.remove(i);
                    player.gold += item.price;
                }
            }
        }
    }

    public static void usePotion(Player player, Item item) {
        for (int i = 0; i < player.potionsList.size(); i++) {
            if (player.potionsList.get(i).name == item.name) {
                player.lifePoints += item.lifePoints;
                player.potionsList.remove(i);
            }
        }
    }

    // use for shop
    public static void buyItem (Player player, Item item){
        if (player.gold >= item.price) {
            if (item.type == WEAPON) {
                item.isEquipped = false;
                player.gold -= item.price;
            } else if (item.type == ARMOR) {
                item.isEquipped = false;
                player.gold -= item.price;
            } else if (item.type == POTION) {
                item.isEquipped = false;
                player.gold -= item.price;
            }
        } else {
            System.out.println("You don't have enough gold.");
        }

    }

    public static List<Item> getSelectedItemList(Player player, String itemType) {
        List<Item> itemList = new ArrayList<>();

        if(itemType.equalsIgnoreCase(WEAPON)) {
            itemList = player.weaponsList;
        } else if(itemType.equalsIgnoreCase(ARMOR)) {
            itemList = player.armorList;
        } else if(itemType.equalsIgnoreCase(POTION)) {
            itemList = player.potionsList;
        }
        return itemList;
    }

    private static void attackEnemy(Player player) {
        String userInput;
        int selectedEnemyIndex;
        boolean isSelected = false;

        showEnemies(player);

        userInput = readUserInputString();

        for (int i = 0; i < player.getMap().getLastLocation().enemies.size(); i++) {
            if (userInput.equalsIgnoreCase("B")) {
                selectLocation(player);
            } else if (userInput.equalsIgnoreCase(String.valueOf(i))) {
                selectedEnemyIndex = i;
                attack(player, selectedEnemyIndex);

                attackEnemy(player);
                isSelected = true;
            }
        }
        if(!isSelected) {
            System.out.println("\nSelected number/letter does not exist.");
            attackEnemy(player);
        }
    }

    public static void attack(Player player, int selectedEnemyIndex) {
        Enemy enemy = player.getMap().getLastLocation().enemies.get(selectedEnemyIndex);

        if(enemy.isAlive) {
            if (player.lifePoints > enemy.damagePoints) {
                enemy.lifePoints = enemy.lifePoints - player.damagePoints;
                player.lifePoints = player.lifePoints - enemy.damagePoints;

                if (enemy.lifePoints > 0) {
                    System.out.println(enemy.type + " has " + enemy.lifePoints + " life points left.");
                    System.out.println("You have " + player.lifePoints + " life points left.");
                }

                if (enemy.lifePoints <= 0) {
                    System.out.println(enemy.type + " has 0 life points left. He is dead.");
                    enemy.isAlive = false;
                    player.getMap().getLastLocation().enemies.remove(selectedEnemyIndex);
                    System.out.println("You have " + player.lifePoints + " life points left.");

                    if (player.getMap().getLastLocation().enemies.isEmpty()) {
                        for (int i = 0; i < player.getMap().locations.size(); i++) {

                            if (player.getMap().getLastLocation() == player.getMap().locations.get(i)) {
                                player.getMap().locations.remove(i);
                            }
                        }
                    }
                }
        } else {
                System.out.println("You cannot attack selected enemy. You have not enough life points.");
            }
        } else {
            System.out.println(player.type + " is already dead. You cannot attack.");
        }
    }

    private static void showLocations(Player player) {
        System.out.println("\n--------------------------LOCATIONS--------------------------");
        if (player.getMap().locations.size() > 0) {
            for (int i = 0; i < player.getMap().locations.size(); i++) {
                System.out.println(i + ". " + player.getMap().locations.get(i).getName().toUpperCase());
            }
            System.out.println("-------------------------------------------------------------");
            System.out.println("B. BACK");
            System.out.println("-------------------------------------------------------------");
        } else {
            System.out.println("All locations are clear. No more enemies left.");
            System.out.println("Congratulations! You passed the game.");
            // Give achievement or increment some flag to earn achievement later
            // Save data, think of scoring system, put player to high score leaderboard, reset his life points, gold, weapons, armor, potions. Anything else?

            // need to edit main while engine, export startmenu to separate method
            showStartMenu();
        }
    }

    private static void showAchievements(Player player) {
        System.out.println("\n--------------------------ACHIEVEMENTS--------------------------");
        for (int i = 0; i < player.achievementsList.size(); i++) {
            System.out.println(i + 1 + ". " + player.achievementsList.get(i).getName().toUpperCase());
        }
        System.out.println("----------------------------------------------------------------");
        System.out.println("B. BACK");
        System.out.println("----------------------------------------------------------------");

        switch (readUserInputChar()) {
            case 'B':
                selectFromGameMenu(player);
                break;
            default:
                System.out.println("\nSelected number/letter does not exist.");
                showAchievements(player);
                break;
        }
    }

    public static void showEnemies(Player player) {
        System.out.println("\n--------------------------ENEMIES--------------------------");
        if (player.getMap().getLastLocation().enemies.size() > 0) {
            for (int i = 0; i < player.getMap().getLastLocation().enemies.size(); i++) {
                if(player.getMap().getLastLocation().enemies.get(i).isAlive) {
                    System.out.println(i + ". Enemy life points: " + player.getMap().getLastLocation().enemies.get(i).getLifePoints() + ", damage points: " + player.getMap().getLastLocation().enemies.get(i).getDamagePoints() + ".");
                } else {
                    player.getMap().getLastLocation().enemies.remove(i);
                }
            }
            System.out.println("-----------------------------------------------------------");
            // shop selection option implementation
            System.out.println("B. BACK");
            System.out.println("-----------------------------------------------------------");

            System.out.print("Choose number/letter:");
        } else {
            System.out.println("Location is clear. No more enemies left.");
            // Give achievement or increment some flag to earn achievement later
            selectLocation(player);
        }
    }

    public static void showInventory() {
        System.out.println("\n---------------------------INVENTORY---------------------------");
        System.out.println("1. WEAPONS");
        System.out.println("2. ARMOR");
        System.out.println("3. POTIONS");
        System.out.println("---------------------------------------------------------------");
        System.out.println("B. BACK");
        System.out.println("---------------------------------------------------------------");

        System.out.print("Choose number/letter:");
    }

    public static void showItems(Player player, String itemType) {
        if(itemType.equalsIgnoreCase(WEAPON) && !player.weaponsList.isEmpty()) {
            System.out.println("\n---------------------------WEAPONS---------------------------");
            for (int i = 0; i < player.weaponsList.size(); i++) {
                if(player.weaponsList.get(i).isEquipped) {
                    System.out.println(i + ". " + player.weaponsList.get(i).getName().toUpperCase() + " | price: " + player.weaponsList.get(i).getPrice() + " | damage points: " + player.weaponsList.get(i).damage + " | (EQUIPPED)");
                } else{
                    System.out.println(i + ". " + player.weaponsList.get(i).getName().toUpperCase() + " | price: " + player.weaponsList.get(i).getPrice() + " | damage points: " + player.weaponsList.get(i).damage);
                }
            }
        } else if(itemType.equalsIgnoreCase(ARMOR) && !player.armorList.isEmpty()) {
            System.out.println("\n---------------------------ARMOR---------------------------");
            for (int i = 0; i < player.armorList.size(); i++) {
                if(player.armorList.get(i).isEquipped) {
                    System.out.println(i + ". " + player.armorList.get(i).getName().toUpperCase() + " | price: " + player.armorList.get(i).getPrice() + " | armor points: " + player.armorList.get(i).armor + " | (EQUIPPED)");
                } else {
                    System.out.println(i + ". " + player.armorList.get(i).getName().toUpperCase() + " | price: " + player.armorList.get(i).getPrice() + " | armor points: " + player.armorList.get(i).armor);
                }
            }
        } else if(itemType.equalsIgnoreCase(POTION) && !player.potionsList.isEmpty()) {
            System.out.println("\n---------------------------POTIONS---------------------------");
            for (int i = 0; i < player.potionsList.size(); i++) {
                System.out.println(i + ". " + player.potionsList.get(i).getName().toUpperCase() + " | price: " + player.potionsList.get(i).getPrice() + ").");
            }
        } else {
            if(itemType == WEAPON) {
                System.out.println("You have 0 weapons left.");
            } else if(itemType == ARMOR) {
                System.out.println("You have 0 armor left.");
            } else if(itemType == POTION) {
                System.out.println("You have 0 potions left.");
            }
            selectInventory(player);
        }

        System.out.println("------------------------------------------------------------");
        System.out.println("B. BACK");
        System.out.println("------------------------------------------------------------");

        if(itemType == POTION) {
            System.out.print("Choose number/letter to USE / SELL:");
        } else {
            System.out.print("Choose number/letter to (UN)EQUIP / SELL:");
        }
    }

    public static void showItemAction(Item item) {
        System.out.println("\n---------------------------" + item.getName().toUpperCase() + "---------------------------");
        if(item.type == POTION) {
            System.out.println("1. USE");
        } else {
            System.out.println("1. (UN)EQUIP");
        }
        System.out.println("2. SELL");
        System.out.println("------------------------------------------------------------");
        System.out.println("B. BACK");
        System.out.println("------------------------------------------------------------");

        System.out.print("Choose number/letter:");
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
        System.out.println("M. BACK TO START MENU");
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
        char userInput = Character.toUpperCase(SCANNER.next().charAt(0));
        SCANNER.nextLine();

        return userInput;
    }

    public static String readUserInputString() {
        String userInput = SCANNER.nextLine().toUpperCase();

        return userInput;
    }

    /*public static int readUserInputInt() {
        int userInput =  scanner.nextInt();
        scanner.nextLine();

        return userInput;
    }*/
}
