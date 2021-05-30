import com.webfirmframework.wffweb.css.css3.AlignContent;
import com.webfirmframework.wffweb.tag.html.Body;
import com.webfirmframework.wffweb.tag.html.H1;
import com.webfirmframework.wffweb.tag.html.Html;
import com.webfirmframework.wffweb.tag.html.P;
import com.webfirmframework.wffweb.tag.html.attribute.global.Id;
import com.webfirmframework.wffweb.tag.html.attribute.global.Style;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.html5.attribute.global.Hidden;
import com.webfirmframework.wffweb.tag.html.metainfo.Head;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;
import com.webfirmframework.wffweb.tag.html.tables.*;
import com.webfirmframework.wffweb.tag.htmlwff.NoTag;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class Game {

    static final String FILE_NAME_MAP = "Map.xlsx";
    static final String FILE_NAME_PLAYERS_HIGH_SCORES = "Highscores.xlsx";
    static final String HTML_PLAYERS_HIGH_SCORES = "top100.html";
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

    public static Achievement achievement1KilledEnemy = new Achievement("Your 1st killed enemy.");
    public static Achievement achievement5KilledEnemies = new Achievement("5 enemies killed.");

    public static List<GameMap> maps = new ArrayList<>();

    public static GameMap map1 = new GameMap("Lietuva");
    public static GameMap map2 = new GameMap("Latvija");

    public static Location location1 = new Location("Vilnius");
    public static Location location2 = new Location("Klaipeda");
    public static Location location3 = new Location("Ryga");
    //public static Location location4 = new Location("Ventspils");

    public static Shop shop = new Shop();

    public static int getEnemiesNumberFromLevel() {
        int enemiesNumber = 0;

        if (level == EASY_LEVEL) {
            enemiesNumber = ENEMIES_EASY_LEVEL;
        } else if (level == MEDIUM_LEVEL) {
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
        //map2.locations.add(location4);
    }

    public static void start(Player player) throws IOException {
        player.achievementsList.add(achievement1KilledEnemy);
        player.achievementsList.add(achievement5KilledEnemies);

        boolean exit = false;

        showStartMenu();

        while (!exit) {

            switch (readUserInputChar()) {
                case '1':
                    // START GAME
                    selectMap(player);
                    break;
                case '2':
                    // CREATE / IMPORT MAP
                    selectToCreateOrImportMap();
                    break;
                case '3':
                    // HIGH SCORES - in progress, not finished
                    exportPlayersHighScoreToExcel(player);
                    showHighScoresOnWeb(sortHighScoreList(getTop100HighScoresFromExcel()));
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
    // BUBBLE SORT ALGORITHM (DESCENDING)
    public static List<Player> sortHighScoreList(List<Player> highScoreList) {
        int num, i, j, temp;
        num = highScoreList.size();

        for (i = 0; i < num; i++) {
            for (i = 0; i < (num - 1); i++) {
                for (j = 0; j < num - i - 1; j++) {
                    if (highScoreList.get(j).getHighScore() < highScoreList.get(j + 1).getHighScore()) {
                        temp = highScoreList.get(j).getHighScore();
                        highScoreList.get(j).setHighScore(highScoreList.get(j + 1).getHighScore());
                        highScoreList.get(j + 1).setHighScore(temp);
                    }
                }
            }
        }

        /*System.out.println("\n---------------------------HIGH SCORES---------------------------");
        int id = 1;
        for (Player p : highScoreList) {
            System.out.println(id + ". " + p.getName() + ": " + p.getHighScore() + " points.");
            id++;
        }*/

        return highScoreList;
    }

    // BUBBLE SORT ALGORITHM (ASCENDING)
    /*public static List<Player> sortHighScoreList(List<Player> highScoreList) {
        int n = highScoreList.size();
        int k;
        for (int m = n; m >= 0; m--) {
            for (int i = 0; i < n - 1; i++) {
                k = i + 1;
                if (highScoreList.get(i).getHighScore() > highScoreList.get(k).getHighScore()) {
                    swapNumbers(i, k, highScoreList);
                }
            }
        }

        *//*System.out.println("\n---------------------------HIGH SCORES---------------------------");
        int id = 1;
        for (Player i : highScoreList) {
            System.out.println(id + ". " + i.getName() + ": " + i.getHighScore() + " points.");
            id++;
        }*//*
        return highScoreList;
    }

    private static void swapNumbers(int i, int j, List<Player> highScoreList) {
        int temp;
        temp = highScoreList.get(i).getHighScore();
        highScoreList.get(i).setHighScore(highScoreList.get(j).getHighScore());
        highScoreList.get(j).setHighScore(temp);
    }*/

    public static void showHighScoresOnWeb(List<Player> playersHighScoreList) throws IOException {

        final Style style3 = new Style("border: 1px solid black;");

        Html rootTag = new Html(null,
                new Style("background:white;")).give(html -> {
            new Head(html).give(head -> {
                new NoTag(head, "\n");
            });
            new NoTag(html, "\n");
            new Body(html).give(body -> {
                new NoTag(body, " ");
                new Div(body).give(div -> {
                    new NoTag(div, " ");
                    new Table(div,
                            new Style("table-layout: fixed;width: 50%;border-collapse: collapse;   border: 1px solid black; margin-left: auto;   margin-right: auto;")).give(table -> {
                        new NoTag(table, " ");
                        new TBody(table).give(tbody -> {
                            new Tr(tbody).give(tr -> {
                                new NoTag(tr, " ");
                                new Th(tr,
                                        style3).give(th -> {
                                    new NoTag(th, "Place");
                                });
                                new NoTag(tr, " ");
                                new Th(tr,
                                        style3).give(th1 -> {
                                    new NoTag(th1, "Player");
                                });
                                new NoTag(tr, " ");
                                new Th(tr,
                                        style3).give(th2 -> {
                                    new NoTag(th2, "Score");
                                });
                                new NoTag(tr, " ");
                            });
                            new NoTag(tbody, " ");

                            // ITERATE PLAYERS, ADD: NAME, SCORE
                            for (int i = 0; i < playersHighScoreList.size(); i++) {
                                List<Integer> playerIDList = new ArrayList<>();
                                for (int j = 1; j < playersHighScoreList.size() + 1; j++) {
                                    playerIDList.add(j);
                                }

                                int playerID = playerIDList.get(i);
                                String playerName = playersHighScoreList.get(i).getName();
                                int playerScore = playersHighScoreList.get(i).getHighScore();

                                new Tr(tbody).give(tr1 -> {
                                    new NoTag(tr1, " ");
                                    new Td(tr1,
                                            style3).give(td -> {
                                        new NoTag(td, String.valueOf(playerID));
                                    });
                                    new NoTag(tr1, " ");
                                    new Td(tr1,
                                            style3).give(td1 -> {
                                        new NoTag(td1, playerName);
                                    });
                                    new NoTag(tr1, " ");
                                    new Td(tr1,
                                            style3).give(td2 -> {
                                        new NoTag(td2, String.valueOf(playerScore));
                                    });
                                    new NoTag(tr1, " ");
                                });
                                new NoTag(tbody, " ");
                            }
                        });
                    });
                    new NoTag(div, " ");
                });
                new NoTag(body, "\n");
            });
        });

        /*Contact tech-support@webfirmframework.com for any help.
         */

        File newHtmlFile = new File(HTML_PLAYERS_HIGH_SCORES);
        FileUtils.writeStringToFile(newHtmlFile, rootTag.toHtmlString());

        showStartMenu();
    }

    private static void selectMap(Player player) throws IOException {
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
        if (!isFound) {
            System.out.println("\nSelected number/letter does not exist.");
            selectMap(player);
        }
    }

    public static void selectToCreateOrImportMap() {
        showCreateAndImportMapOptions();

        switch (readUserInputChar()) {
            case '1':
                // CREATE MAP
                createMap();
                showStartMenu();
                break;
            case '2':
                // IMPORT MAP
                importMapFromExcel();
                showStartMenu();
                break;
            case 'B':
                showStartMenu();
                break;
            default:
                System.out.println("\nSelected number/letter does not exist.");
                selectToCreateOrImportMap();
                break;
        }
    }

    public static void createMap() {
        System.out.print("Enter map's name: ");
        String mapName = readUserInputString();
        GameMap newMap = new GameMap(mapName);
        maps.add(newMap);

        System.out.print("Enter number of locations you want to add: ");
        int numberOfLocations = readUserInputInt();

        for (int i = 0; i < numberOfLocations; i++) {
            System.out.print("Enter location's name: ");
            String locationName = readUserInputString();

            Location newLocation = new Location(locationName);
            newMap.locations.add(newLocation);
        }
        System.out.print("Map " + mapName + " was successfully created with " + numberOfLocations + " locations: ");
        for (Location l : newMap.locations) {
            System.out.print(l.getName() + " ");
        }
    }

    public static void selectLevel(Player player) throws IOException {
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

    private static void enterPlayerName(Player player) throws IOException {
        System.out.print("Enter player's name: ");
        player.setName(readUserInputString());

        equipStartingItems(player);
        generateEnemies();
        selectFromGameMenu(player);
    }

    private static void selectFromGameMenu(Player player) throws IOException {
        showGameMenu();

        switch (readUserInputChar()) {
            case '1':
                selectLocation(player);
                break;
            case '2':
                selectInventory(player);
                break;
            case '3':
                selectShop(player);
                break;
            case '4':
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

    private static void selectLocation(Player player) throws IOException {
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
        if (!isSelected) {
            System.out.println("\nSelected number/letter does not exist.");
            selectLocation(player);
        }
    }

    private static void selectInventory(Player player) throws IOException {
        showInventoryOptions(player);

        switch (readUserInputChar()) {
            case '1':
                selectInventoryItem(player, WEAPON);
                break;
            case '2':
                selectInventoryItem(player, ARMOR);
                break;
            case '3':
                selectInventoryItem(player, POTION);
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

    public static void selectInventoryItem(Player player, String itemType) throws IOException {
        String userInput;
        boolean isSelected = false;
        Item selectedItem;

        showInventoryItems(player, itemType);

        List<Item> selectedItemList = getSelectedPlayerItemList(player, itemType);

        userInput = readUserInputString();

        for (int i = 0; i < selectedItemList.size(); i++) {
            if (userInput.equalsIgnoreCase("B")) {
                selectInventory(player);
                isSelected = true;

            } else if (userInput.equalsIgnoreCase(String.valueOf(i))) {
                selectedItem = selectedItemList.get(i);
                selectInventoryItemAction(player, itemType, selectedItem);

                isSelected = true;
            }
        }
        if (!isSelected) {
            System.out.println("\nSelected number/letter does not exist.");
            selectInventoryItem(player, itemType);
        }
    }

    public static void selectInventoryItemAction(Player player, String itemType, Item item) throws IOException {
        showInventoryItemAction(item);

        switch (readUserInputChar()) {
            case '1':
                // EQUIP
                equipItem(player, item);
                selectInventoryItem(player, itemType);
                break;
            case '2':
                // SELL
                sellItem(player, item);
                selectInventoryItem(player, itemType);
                break;
            case 'B':
                // BACK
                selectInventoryItem(player, itemType);
                break;
            default:
                System.out.println("\nSelected number/letter does not exist.");
                selectInventoryItemAction(player, itemType, item);
                break;
        }
    }

    private static void selectShop(Player player) throws IOException {
        showShopOptions(player);

        switch (readUserInputChar()) {
            case '1':
                selectShopItem(player, WEAPON);
                break;
            case '2':
                selectShopItem(player, ARMOR);
                break;
            case '3':
                selectShopItem(player, POTION);
                break;
            case 'B':
                selectFromGameMenu(player);
                break;
            default:
                System.out.println("\nSelected number/letter does not exist.");
                selectShop(player);
                break;
        }
    }

    public static void selectShopItem(Player player, String itemType) throws IOException {
        String userInput;
        boolean isSelected = false;
        Item selectedItem;

        showShopItems(player, itemType);

        List<Item> selectedItemList = getSelectedShopItemList(player, itemType);

        userInput = readUserInputString();

        for (int i = 0; i < selectedItemList.size(); i++) {
            if (userInput.equalsIgnoreCase("B")) {
                selectShop(player);
                isSelected = true;
            } else if (userInput.equalsIgnoreCase(String.valueOf(i))) {
                if (itemType == WEAPON || itemType == ARMOR) {
                    selectedItem = selectedItemList.get(i);
                    buyItem(player, selectedItem);
                } else if (itemType == POTION) {
                    switch (userInput) {
                        case "1":
                            buyItem(player, shop.potion25);
                            break;
                        case "2":
                            buyItem(player, shop.potion50);
                            break;
                        case "3":
                            buyItem(player, shop.potion75);
                            break;
                        /*default:
                            System.out.println("\nSelected number/letter does not exist.");
                            break;*/
                    }
                }
            }
            isSelected = true;
        }
        if (!isSelected) {
            System.out.println("\nSelected number/letter does not exist.");
            selectShopItem(player, itemType);
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
                    player.armorPoints = item.armor;
                }
            } else {
                if (item.type == WEAPON) {
                    item.isEquipped = true;
                    player.damagePoints += item.damage;
                } else if (item.type == ARMOR) {
                    item.isEquipped = true;
                    player.armorPoints = item.armor;
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

        if (item.type == WEAPON) {
            for (int i = 0; i < player.weaponsList.size(); i++) {
                if (player.weaponsList.get(i).isEquipped) {
                    counter += 1;
                }
            }
        } else if (item.type == ARMOR) {
            for (int i = 0; i < player.armorList.size(); i++) {
                if (player.armorList.get(i).isEquipped) {
                    counter += 1;
                }
            }
        }

        return counter == 1 ? true : false;
    }

    public static void unequipEquippedItem(Player player, Item item) {
        if (item.type == WEAPON) {
            for (int i = 0; i < player.weaponsList.size(); i++) {
                if (player.weaponsList.get(i).isEquipped) {
                    player.weaponsList.get(i).isEquipped = false;
                    player.damagePoints -= player.weaponsList.get(i).damage;
                }
            }
        } else if (item.type == ARMOR) {
            for (int i = 0; i < player.armorList.size(); i++) {
                if (player.armorList.get(i).isEquipped) {
                    player.armorList.get(i).isEquipped = false;
                    player.armorPoints -= player.armorList.get(i).armor;
                }
            }
        }
    }

    public static void sellItem(Player player, Item item) {
        if (item.type == WEAPON) {
            for (int i = 0; i < player.weaponsList.size(); i++) {
                if (player.weaponsList.get(i).getName() == item.name) {
                    item.isEquipped = false;
                    player.weaponsList.remove(i);
                    player.gold += item.price;
                    if (item.isEquipped) {
                        player.damagePoints -= item.damage;
                    }
                }
            }
        } else if (item.type == ARMOR) {
            for (int i = 0; i < player.armorList.size(); i++) {
                if (player.armorList.get(i).getName() == item.name) {
                    item.isEquipped = false;
                    player.armorList.remove(i);
                    player.armorPoints -= item.armor;
                    player.gold += item.price;
                    if (item.isEquipped) {
                        player.armorPoints -= item.armor;
                    }
                }
            }
        } else if (item.type == POTION) {
            for (int i = 0; i < player.potionsList.size(); i++) {
                if (player.potionsList.get(i).getName() == item.name) {
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

    public static void buyItem(Player player, Item item) throws IOException {
        if (player.gold >= item.price) {
            if (item.type == WEAPON) {
                player.weaponsList.add(item);
                //item.isEquipped = false;
                equipItem(player, item);
                player.gold -= item.price;
                System.out.println("You bought " + item.name + ". It is equipped.");
            } else if (item.type == ARMOR) {
                player.armorList.add(item);
                //item.isEquipped = false;
                equipItem(player, item);
                player.gold -= item.price;
                System.out.println("You bought " + item.name + ". It is equipped.");
            } else if (item.type == POTION) {
                player.gold -= item.price;
                player.lifePoints += item.lifePoints;
                System.out.println("You bought " + item.name + ". It is equipped.");
            }
        } else {
            System.out.println("You don't have enough gold.");
        }
        selectShopItem(player, item.type);
    }

    public static void equipStartingItems(Player player) {
        Weapon dagger = new Weapon("Dagger", 100, 20);
        Armor woodenShield = new Armor("Wooden Shield", 50, 5);

        player.weaponsList.add(dagger);
        dagger.setEquipped(true);
        player.armorList.add(woodenShield);
        woodenShield.setEquipped(true);

        for (int i = 0; i < player.weaponsList.size(); i++) {
            if (player.weaponsList.get(i).isEquipped) {
                player.damagePoints += player.weaponsList.get(i).damage;
            }
            if (player.armorList.get(i).isEquipped) {
                player.armorPoints += player.armorList.get(i).armor;
            }
        }
    }

    public static List<Item> getSelectedPlayerItemList(Player player, String itemType) {
        List<Item> itemList = new ArrayList<>();

        if (itemType.equalsIgnoreCase(WEAPON)) {
            itemList = player.weaponsList;
        } else if (itemType.equalsIgnoreCase(ARMOR)) {
            itemList = player.armorList;
        } else if (itemType.equalsIgnoreCase(POTION)) {
            itemList = player.potionsList;
        }
        return itemList;
    }

    public static List<Item> getSelectedShopItemList(Player player, String itemType) {
        List<Item> itemList = new ArrayList<>();

        if (itemType.equalsIgnoreCase(WEAPON)) {
            itemList = shop.weaponsListInShop;
        } else if (itemType.equalsIgnoreCase(ARMOR)) {
            itemList = shop.armorListInShop;
        } else if (itemType.equalsIgnoreCase(POTION)) {
            itemList = shop.potionsListInShop;
        }
        return itemList;
    }

    private static void attackEnemy(Player player) throws IOException {
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
        if (!isSelected) {
            System.out.println("\nSelected number/letter does not exist.");
            attackEnemy(player);
        }
    }

    public static void attack(Player player, int selectedEnemyIndex) {
        Enemy enemy = player.getMap().getLastLocation().enemies.get(selectedEnemyIndex);

        if (enemy.isAlive) {
            //showPlayerLPAndGold(player);

            if (player.lifePoints > enemy.damagePoints) {
                enemy.lifePoints = enemy.lifePoints - player.damagePoints;
                player.lifePoints = player.lifePoints + player.armorPoints - enemy.damagePoints;

                /*if (enemy.lifePoints > 0) {
                    System.out.println(enemy.type + " has " + enemy.lifePoints + " life points left.");
                    System.out.println("You have " + player.lifePoints + " life points left.");
                }*/

                if (enemy.lifePoints <= 0) {
                    System.out.println(enemy.type + " has 0 life points left. He is dead.");
                    enemy.isAlive = false;
                    player.enemiesKilledCounter++;
                    player.gold += 10;

                    player.getMap().getLastLocation().enemies.remove(selectedEnemyIndex);
                    System.out.println("You have " + player.lifePoints + " life points left.");
                    checkAchievements(player);

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

    public static void showCreateAndImportMapOptions() {
        System.out.println("\n---------------------------CREATE / IMPORT MAP---------------------------");
        System.out.println(maps.size() + " maps in total.");
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("1. CREATE MAP");
        System.out.println("2. IMPORT MAP");
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("B. BACK");
        System.out.println("-------------------------------------------------------------------------");

        System.out.print("Choose a number to select an item:");
    }

    private static void showLocations(Player player) throws IOException {
        System.out.println("\n--------------------------LOCATIONS--------------------------");
        showPlayerLPAndGold(player);
        if (player.getMap().locations.size() > 0) {
            for (int i = 0; i < player.getMap().locations.size(); i++) {
                System.out.println(i + ". " + player.getMap().locations.get(i).getName().toUpperCase());
            }
            System.out.println("-------------------------------------------------------------");
            System.out.println("B. BACK");
            System.out.println("-------------------------------------------------------------");
        } else {
            congratulatePlayer(player);
            // need to edit main while engine, export startmenu to separate method
            showStartMenu();
        }
    }

    public static void congratulatePlayer(Player player) throws IOException {
        System.out.println("All locations are clear. No more enemies left.");
        System.out.println("Congratulations! You won the game!");
        // Give achievement or increment some flag to earn achievement later
        // Save data, think of scoring system, put player to high score leaderboard, reset his life points, gold, weapons, armor, potions. Anything else?
        // Create picture from symbols or animation from symbols

        player.setHighScore(100);
        exportPlayersHighScoreToExcel(player);
        showHighScoresOnWeb(sortHighScoreList(getTop100HighScoresFromExcel()));
        // reset player data after he wins - debug it
    }

    private static void showAchievements(Player player) throws IOException {
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

    public static void showEnemies(Player player) throws IOException {
        System.out.println("\n--------------------------ENEMIES--------------------------");
        showPlayerLPGoldDamageAndArmorPoints(player);

        if (player.getMap().getLastLocation().enemies.size() > 0) {
            for (int i = 0; i < player.getMap().getLastLocation().enemies.size(); i++) {
                if (player.getMap().getLastLocation().enemies.get(i).isAlive) {
                    System.out.println(i + ". Enemy | life points: " + player.getMap().getLastLocation().enemies.get(i).getLifePoints() + " | damage points: " + player.getMap().getLastLocation().enemies.get(i).getDamagePoints());
                } else {
                    player.getMap().getLastLocation().enemies.remove(i);
                }
            }
            System.out.println("-----------------------------------------------------------");
            // shop selection option implementation
            System.out.println("B. BACK");
            System.out.println("-----------------------------------------------------------");

            System.out.print("Choose a number to attack an enemy:");
        } else {
            System.out.println("Location is clear. No more enemies left.");
            // Give achievement or increment some flag to earn achievement later
            selectLocation(player);
        }
    }

    public static void showInventoryOptions(Player player) {
        System.out.println("\n---------------------------INVENTORY---------------------------");
        showPlayerLPGoldDamageAndArmorPoints(player);
        System.out.println("1. WEAPONS");
        System.out.println("2. ARMOR");
        System.out.println("3. POTIONS");
        System.out.println("---------------------------------------------------------------");
        System.out.println("B. BACK");
        System.out.println("---------------------------------------------------------------");

        System.out.print("Choose a number to select an item:");
    }

    public static void showInventoryItems(Player player, String itemType) throws IOException {
        if (itemType.equalsIgnoreCase(WEAPON) && !player.weaponsList.isEmpty()) {
            System.out.println("\n---------------------------WEAPONS---------------------------");
            showPlayerLPGoldDamageAndArmorPoints(player);
            for (int i = 0; i < player.weaponsList.size(); i++) {
                if (player.weaponsList.get(i).isEquipped) {
                    System.out.println(i + ". " + player.weaponsList.get(i).getName().toUpperCase() + " | price: " + player.weaponsList.get(i).getPrice() + " | damage points: " + (player.baseDamagePoints + player.weaponsList.get(i).damage) + " | (EQUIPPED)");
                } else {
                    System.out.println(i + ". " + player.weaponsList.get(i).getName().toUpperCase() + " | price: " + player.weaponsList.get(i).getPrice() + " | damage points: " + (player.baseDamagePoints + player.weaponsList.get(i).damage));
                }
            }
        } else if (itemType.equalsIgnoreCase(ARMOR) && !player.armorList.isEmpty()) {
            System.out.println("\n---------------------------ARMOR---------------------------");
            showPlayerLPGoldDamageAndArmorPoints(player);
            for (int i = 0; i < player.armorList.size(); i++) {
                if (player.armorList.get(i).isEquipped) {
                    System.out.println(i + ". " + player.armorList.get(i).getName().toUpperCase() + " | price: " + player.armorList.get(i).getPrice() + " | armor points: " + player.armorList.get(i).armor + " | (EQUIPPED)");
                } else {
                    System.out.println(i + ". " + player.armorList.get(i).getName().toUpperCase() + " | price: " + player.armorList.get(i).getPrice() + " | armor points: " + player.armorList.get(i).armor);
                }
            }
        } else if (itemType.equalsIgnoreCase(POTION) && !player.potionsList.isEmpty()) {
            System.out.println("\n---------------------------POTIONS---------------------------");
            showPlayerLPGoldDamageAndArmorPoints(player);
            for (int i = 0; i < player.potionsList.size(); i++) {
                System.out.println(i + ". " + player.potionsList.get(i).getName().toUpperCase() + " | price: " + player.potionsList.get(i).getPrice() + ").");
            }
        } else {
            if (itemType == WEAPON) {
                System.out.println("You have 0 weapons.");
            } else if (itemType == ARMOR) {
                System.out.println("You have 0 armor.");
            } else if (itemType == POTION) {
                System.out.println("You have 0 potions.");
            }
            selectInventory(player);
        }

        System.out.println("------------------------------------------------------------");
        System.out.println("B. BACK");
        System.out.println("------------------------------------------------------------");

        if (itemType == POTION) {
            System.out.print("Choose a number to USE / SELL a potion:");
        } else {
            System.out.print("Choose a number to (UN)EQUIP / SELL an item:");
        }
    }

    public static void showShopOptions(Player player) {
        System.out.println("\n---------------------------SHOP---------------------------");
        showPlayerLPGoldDamageAndArmorPoints(player);
        System.out.println("1. WEAPONS");
        System.out.println("2. ARMOR");
        System.out.println("3. POTIONS");
        System.out.println("----------------------------------------------------------");
        System.out.println("B. BACK");
        System.out.println("----------------------------------------------------------");

        System.out.print("Choose a number to select an item category:");
    }

    public static void showShopItems(Player player, String itemType) throws IOException {
        if (itemType.equalsIgnoreCase(WEAPON) && !shop.weaponsListInShop.isEmpty()) {
            System.out.println("\n---------------------------WEAPONS---------------------------");
            showPlayerLPGoldDamageAndArmorPoints(player);
            for (int i = 0; i < shop.weaponsListInShop.size(); i++) {
                System.out.println(i + ". " + shop.weaponsListInShop.get(i).getName().toUpperCase() + " | price: " + shop.weaponsListInShop.get(i).getPrice() + " | damage points: " + (player.baseDamagePoints + shop.weaponsListInShop.get(i).damage));
            }
        } else if (itemType.equalsIgnoreCase(ARMOR) && !shop.armorListInShop.isEmpty()) {
            System.out.println("\n---------------------------ARMOR---------------------------");
            showPlayerLPGoldDamageAndArmorPoints(player);
            for (int i = 0; i < shop.armorListInShop.size(); i++) {
                System.out.println(i + ". " + shop.armorListInShop.get(i).getName().toUpperCase() + " | price: " + shop.armorListInShop.get(i).getPrice() + " | armor points: " + shop.armorListInShop.get(i).armor);
            }
        } else if (itemType.equalsIgnoreCase(POTION) && !shop.potionsListInShop.isEmpty()) {
            int potion25Counter = 0;
            int potion50Counter = 0;
            int potion75Counter = 0;

            System.out.println("\n---------------------------POTIONS---------------------------");
            showPlayerLPGoldDamageAndArmorPoints(player);
            for (int i = 0; i < shop.potionsListInShop.size(); i++) {
                if (shop.potionsListInShop.get(i).name.equals(Shop.POTION_25_LP)) {
                    potion25Counter += 1;
                }
                if (shop.potionsListInShop.get(i).name.equals(Shop.POTION_50_LP)) {
                    potion50Counter += 1;
                }
                if (shop.potionsListInShop.get(i).name.equals(Shop.POTION_75_LP)) {
                    potion75Counter += 1;
                }
            }
            if (potion25Counter >= 1) {
                System.out.println("1. " + Shop.POTION_25_LP.toUpperCase() + " | price: " + Shop.potion25.getPrice() + " gold");
            }
            if (potion50Counter >= 1) {
                System.out.println("2. " + Shop.POTION_50_LP.toUpperCase() + " | price: " + Shop.potion50.getPrice() + " gold");
            }
            if (potion75Counter >= 1) {
                System.out.println("3. " + Shop.POTION_75_LP.toUpperCase() + " | price: " + Shop.potion75.getPrice() + " gold");
            }
        } else {
            if (itemType == WEAPON) {
                System.out.println("Sorry. 0 weapons left in the shop.");
            }
            if (itemType == ARMOR) {
                System.out.println("Sorry. 0 armor left in the shop.");
            }
            if (itemType == POTION) {
                System.out.println("Sorry. 0 potions left in the shop.");
            }
            selectInventory(player);
        }

        System.out.println("------------------------------------------------------------");
        System.out.println("B. BACK");
        System.out.println("------------------------------------------------------------");

        System.out.print("Choose a number to buy an item:");
    }

    public static void showPlayerLPAndGold(Player player) {
        System.out.println("You have: " + player.gold + " gold | " + player.lifePoints + " life points");
        System.out.println("-----------------------------------------------------------");
    }

    public static void showPlayerLPGoldDamageAndArmorPoints(Player player) {
        System.out.println("You have: " + player.gold + " gold | " + player.lifePoints + " life points | " + player.damagePoints + " damage points | " + player.armorPoints + " armor points");
        System.out.println("-----------------------------------------------------------");
    }

    public static void showInventoryItemAction(Item item) {
        System.out.println("\n---------------------------" + item.getName().toUpperCase() + "---------------------------");
        if (item.type == POTION) {
            System.out.println("1. USE");
        } else {
            System.out.println("1. (UN)EQUIP");
        }
        System.out.println("2. SELL");
        System.out.println("------------------------------------------------------------");
        System.out.println("B. BACK");
        System.out.println("------------------------------------------------------------");

        System.out.print("Choose a number to make an action with an item:");
    }

    public static void showStartMenu() {
        System.out.println("\n---------------------------START MENU---------------------------");
        System.out.println("1. START GAME");
        System.out.println("2. CREATE / IMPORT MAP");
        System.out.println("3. HIGH SCORE");
        System.out.println("----------------------------------------------------------------");
        System.out.println("E. EXIT GAME");
        System.out.println("----------------------------------------------------------------");

        System.out.print("Choose a number/letter:");
    }

    public static void showMapsOptions() {
        System.out.println("\n----------------------------MAPS----------------------------");
        for (int i = 0; i < maps.size(); i++) {
            System.out.println(i + ". " + maps.get(i).getName().toUpperCase());
        }
        System.out.println("------------------------------------------------------------");
        System.out.println("B. BACK");
        System.out.println("------------------------------------------------------------");

        System.out.print("Choose a number to select a map:");
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

        System.out.print("Choose a number to select game difficulty level");
    }

    public static void showGameMenu() {
        System.out.println("\n---------------------------GAME MENU---------------------------");
        System.out.println("1. LOCATIONS");
        System.out.println("2. INVENTORY");
        System.out.println("3. SHOP");
        System.out.println("4. ACHIEVEMENTS");
        System.out.println("---------------------------------------------------------------");
        System.out.println("M. GO TO START MENU");
        System.out.println("E. EXIT GAME");
        System.out.println("---------------------------------------------------------------");

        System.out.print("Choose a number/letter:");
    }

    // HANDLE EXCEPTIONS
    public static char readUserInputChar() {
        char userInput = Character.toUpperCase(SCANNER.next().charAt(0));
        SCANNER.nextLine();

        return userInput;
    }

    // HANDLE EXCEPTIONS
    public static String readUserInputString() {
        String userInput = SCANNER.nextLine().toUpperCase();

        return userInput;
    }

    // HANDLE EXCEPTIONS
    public static int readUserInputInt() {
        int userInput = SCANNER.nextInt();
        SCANNER.nextLine();

        return userInput;
    }

    public static void checkAchievements(Player player) {
        if (player.enemiesKilledCounter == 1) {
            System.out.println("\n---------------------------NEW ACHIEVEMENT---------------------------");
            System.out.println(achievement1KilledEnemy.getName().toUpperCase());
            System.out.println("---------------------------------------------------------------------");
        }
        if (player.enemiesKilledCounter == 5) {
            System.out.println("\n---------------------------NEW ACHIEVEMENT---------------------------");
            System.out.println(achievement5KilledEnemies.getName().toUpperCase());
            System.out.println("---------------------------------------------------------------------");
        }
    }

    public static void importMapFromExcel() {
        try {
            FileInputStream file = new FileInputStream(new File(FILE_NAME_MAP));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);

            Row secondRow = sheet.getRow(1);
            Cell cellMapName = secondRow.getCell(0);
            String mapName = cellMapName.getStringCellValue();

            int lastRowNumber = sheet.getLastRowNum() + 1;
            boolean mapExists, locationExists;

            // read MAP and LOCATIONS from excel
            for (int i = 1; i < lastRowNumber; i++) {
                mapExists = false;
                Row row = sheet.getRow(i);
                Cell cellLocationName = row.getCell(1);

                if (cellLocationName != null) {
                    Location newLocation;
                    // ITERATE MAPS
                    for (int j = 0; j < maps.size(); j++) {

                        // IF MAP EXISTS
                        if (maps.get(j).getName().equalsIgnoreCase(mapName)) {
                            mapExists = true;
                            locationExists = false;

                            // ITERATE LOCATIONS
                            for (int k = 0; k < maps.get(j).locations.size(); k++) {

                                // IF LOCATION EXISTS
                                if (maps.get(j).locations.get(k).getName().equalsIgnoreCase(cellLocationName.getStringCellValue())) {
                                    locationExists = true;
                                }
                            }

                            // IF LOCATION DOESN'T EXIST
                            if (!locationExists) {
                                newLocation = new Location(cellLocationName.getStringCellValue());
                                maps.get(j).locations.add(newLocation);
                            }
                            //break; // ar reikia?
                        }
                    }

                    // IF MAP DOESN'T EXIST
                    if (!mapExists) {
                        GameMap newMap = new GameMap(mapName);
                        newLocation = new Location(cellLocationName.getStringCellValue());
                        maps.add(newMap);
                        newMap.locations.add(newLocation);
                    }
                }
            }
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // edit code so that players stay after game is closed and not overwritten next time - check for update excel file solutions
    public static void exportPlayersHighScoreToExcel(Player player) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet("High scores");

        int rowCount = 0;

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Place");
        headerRow.createCell(1).setCellValue("Player");
        headerRow.createCell(2).setCellValue("Score");
        List<Player> players = new ArrayList<>();
        //players.add(player);

        // test data
        Player player1 = new Player("Petras");
        player1.setHighScore(100);

        Player player2 = new Player("Jonas");
        player2.setHighScore(50);

        Player player3 = new Player("Tomas");
        player3.setHighScore(200);

        players.add(player1);
        players.add(player2);
        players.add(player3);

        for (int i = 0; i < players.size(); i++) {
            Row row = sheet.createRow(++rowCount);

            Cell playerID = row.createCell(0);
            playerID.setCellValue(i + 1);

            Cell playerName = row.createCell(1);
            playerName.setCellValue(players.get(i).getName());

            Cell highScore = row.createCell(2);
            highScore.setCellValue(players.get(i).getHighScore());
        }

        try {
            FileOutputStream out = new FileOutputStream(new File(FILE_NAME_PLAYERS_HIGH_SCORES));
            workbook.write(out);
            out.close();
            System.out.println("Players high scores were successfully exported to " + FILE_NAME_PLAYERS_HIGH_SCORES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Player> getTop100HighScoresFromExcel() {
        List<Player> top100HighScoresList = new ArrayList<>();

        try {
            FileInputStream file = new FileInputStream(new File(FILE_NAME_PLAYERS_HIGH_SCORES));

            XSSFWorkbook workbook = new XSSFWorkbook(file);

            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
                Row row = sheet.getRow(i);
                Cell playerName = row.getCell(1);
                Cell highScore = row.getCell(2);

                Player player = new Player();
                player.setName(playerName.getStringCellValue());
                player.setHighScore((int)highScore.getNumericCellValue());
                top100HighScoresList.add(player);
            }

            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return top100HighScoresList;
    }



}
    /*public static void importMapsFromExcel(){
        try {
            List<String> tempMapsNamesListFromExcel = new ArrayList<>();
            List<String> tempLocationsNamesListFromExcel = new ArrayList<>();

            List<String> tempMapsNamesListFromGame = new ArrayList<>();
            List<String> tempLocationsNamesListFromGame = new ArrayList<>();

            //Map<String, String> tempMapsAndLocations = new HashMap<>();

            FileInputStream file = new FileInputStream(new File(FILE_NAME));

            XSSFWorkbook workbook = new XSSFWorkbook(file);

            XSSFSheet sheet = workbook.getSheetAt(0);

            int lastRowNumber = sheet.getLastRowNum() + 1;

            for (int i = 0; i < maps.size(); i++) {
                 tempMapsNamesListFromGame.add(maps.get(i).getName());
            }

            // read MAPS and LOCATIONS from excel
            for (int i = 1; i < lastRowNumber; i++) {
                Row row = sheet.getRow(i);
                Cell cellMap = row.getCell(0);
                Cell cellLocation = row.getCell(1);

                if (cellMap != null && cellLocation != null) {
                    tempMapsNamesListFromExcel.add(cellMap.getStringCellValue());
                    tempLocationsNamesListFromExcel.add(cellLocation.getStringCellValue());
                    //tempMapsAndLocations.put(cellMap.getStringCellValue(), cellLocation.getStringCellValue());
                }
            }

            for(String s : tempMapsNamesListFromGame) {
                System.out.println(s);
            }

            //tempMapsAndLocations.forEach((key, value) -> System.out.println(key + " " + value));


            *//*for(String s : tempMapsList) {
                System.out.println(s);
            }

            for(String s : tempLocationsList) {
                System.out.println(s);
            }*//*


            //boolean isCreated = false;
            // ITERATE MAPS FROM EXCEL
            *//*for (int i = 0; i < tempMapsList.size(); i++) {

                // ITERATE MAPS
                for (int j = 0; j < maps.size(); j++) {

                    // IF MAP ALREADY EXISTS
                    if (tempMapsList.contains(maps.get(j))) {
                        System.out.println(tempMapsList.get(i) + " exists");

                        // ITERATE LOCATIONS FROM EXCEL
                        for (int k = 0; k < tempLocationsList.size(); k++) {

                            // ITERATE LOCATIONS
                            for (int l = 0; l < maps.get(j).locations.size(); l++) {
                                // IF LOCATION EXISTS
                                *//**//*if (tempLocationsList.get(l).equalsIgnoreCase(maps.get(j).locations.get(k).getName())) {
                                    System.out.println(tempLocationsList.get(l) + " exists");
                                }*//**//*
                            }
                        }
                        //else {
                        // IF LOCATION DOESN'T EXIST
                        //System.out.println(tempLocationsList.get(j) + " doesn't exist");
                        //maps.get(i).locations.add(new Location(tempLocationsList.get(j)));
                        //}
                        //break;
                    // IF MAP DOESN'T EXIST
                    } else {
                        //System.out.println(tempMapsList.get(i) + " doesn't exist");
                        //System.out.println(tempLocationsList.get(j) + " doesn't exist");

                        //Map newMap = new Map(tempMapsList.get(i));
                        //maps.add(newMap);

                        //newMap.locations.add(new Location(cellLocation.getStringCellValue()));
                    }
                }
                //break;
            }*//*
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /*public static void exportMapsAndLocationsToExcel() {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet("Maps");

        int rowCount = 0;

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Country");
        headerRow.createCell(1).setCellValue("City");

        for (int i = 0; i < maps.size(); i++) {
            for (int j = 0; j < maps.get(i).locations.size(); j++) {
                Row row = sheet.createRow(++rowCount);

                Cell mapNameCell = row.createCell(0);
                mapNameCell.setCellValue(maps.get(i).getName());

                Cell locationNameCell = row.createCell(1);
                locationNameCell.setCellValue(maps.get(i).locations.get(j).getName());
            }
        }
        try {
            FileOutputStream out = new FileOutputStream(new File(FILE_NAME));
            workbook.write(out);
            out.close();
            System.out.println("Maps were successfully exported to " + FILE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/