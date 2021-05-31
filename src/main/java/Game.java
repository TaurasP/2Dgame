import com.webfirmframework.wffweb.tag.html.Body;
import com.webfirmframework.wffweb.tag.html.H1;
import com.webfirmframework.wffweb.tag.html.Html;
import com.webfirmframework.wffweb.tag.html.attribute.global.Style;
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

    public final String FILE_NAME_MAP = "Map.xlsx";
    public final String FILE_NAME_PLAYERS_SCORES = "Scores.xlsx";
    public final String FILE_NAME_PLAYERS_HIGH_SCORES = "top100.html";
    public final Scanner SCANNER = new Scanner(System.in);
    public final String WEAPON = "Weapon";
    public final String ARMOR = "Armor";
    public final String POTION = "Potion";
    private final String EASY_LEVEL = "EASY";
    private final String MEDIUM_LEVEL = "MEDIUM";
    private final String HARD_LEVEL = "HARD";
    public final int ENEMIES_EASY_LEVEL = 2;
    public final int ENEMIES_MEDIUM_LEVEL = 4;
    public final int ENEMIES_HARD_LEVEL = 6;

    private String level;

    public Achievement achievement1KilledEnemy = new Achievement("Your 1st killed enemy.");
    public Achievement achievement5KilledEnemies = new Achievement("5 enemies killed.");
    public Achievement achievement10KilledEnemies = new Achievement("10 enemies killed.");
    public Achievement achievement1LocationCleared = new Achievement("Your 1st cleared location.");

    public List<GameMap> maps = new ArrayList<>();

    public GameMap map1 = new GameMap("Lietuva");
    public GameMap map2 = new GameMap("Latvija");

    public Location location1 = new Location("Vilnius");
    public Location location2 = new Location("Klaipeda");
    public Location location3 = new Location("Ryga");
    //public Location location4 = new Location("Ventspils");

    public Shop shop = new Shop();

    public int getEnemiesNumberFromLevel() {
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

    public void generateEnemies() {
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

    public void start(Player player) throws IOException {
        boolean exit = false;

        while (!exit) {
            showStartMenu();
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
                    // HIGH SCORES
                    exportPlayersHighScoresToHTML(sortHighScoreList(getPlayersScoresFromExcel()));
                    openPlayersHighScoresHTML();
                    break;
                case 'E':
                    exit = true;
                    break;
                default:
                    System.out.println("\nSelected number/letter does not exist.");
                    break;
            }
        }
    }

    // BUBBLE SORT ALGORITHM (DESCENDING)
    public List<Player> sortHighScoreList(List<Player> highScoreList) {
        String tempName;
        int num, i, j, tempHighScore;
        num = highScoreList.size();

        for (i = 0; i < num; i++) {
            for (i = 0; i < (num - 1); i++) {
                for (j = 0; j < num - i - 1; j++) {
                    if (highScoreList.get(j).getHighScore() < highScoreList.get(j + 1).getHighScore()) {
                        tempHighScore = highScoreList.get(j).getHighScore();
                        tempName = highScoreList.get(j).getName();

                        highScoreList.get(j).setHighScore(highScoreList.get(j + 1).getHighScore());
                        highScoreList.get(j).setName(highScoreList.get(j + 1).getName());

                        highScoreList.get(j + 1).setHighScore(tempHighScore);
                        highScoreList.get(j + 1).setName(tempName);
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

    public void checkIfPlayerEarnedAchievement(Player player) {
        if (player.enemiesKilledCounter == 1) {
            player.achievementsList.add(achievement1KilledEnemy);
            showAchievement(achievement1KilledEnemy);
        }
        if (player.enemiesKilledCounter == 5) {
            player.achievementsList.add(achievement5KilledEnemies);
            showAchievement(achievement5KilledEnemies);
        }
        if (player.enemiesKilledCounter == 10) {
            player.achievementsList.add(achievement10KilledEnemies);
            showAchievement(achievement10KilledEnemies);
        }
        if(player.locationsClearedCounter == 1) {
            player.achievementsList.add(achievement1LocationCleared);
            showAchievement(achievement1LocationCleared);
        }
    }

    public void showAchievement(Achievement achievement) {
        System.out.println("\n----------------------NEW ACHIEVEMENT----------------------");
        System.out.println(achievement.getName().toUpperCase());
        System.out.println("-----------------------------------------------------------");
    }

    public void exportPlayersHighScoresToHTML(List<Player> playersHighScoreList) throws IOException {

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
                    new H1(div,
                            new Style("text-align:center;padding: 10px;")).give(h1 -> {
                        new NoTag(h1, "High scores");
                    });
                    new NoTag(div, " ");
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

        File newHtmlFile = new File(FILE_NAME_PLAYERS_HIGH_SCORES);
        FileUtils.writeStringToFile(newHtmlFile, rootTag.toHtmlString());
    }

    private void selectMap(Player player) throws IOException {
        String userInput;
        boolean isFound = false;

        showMapsOptions();
        userInput = readUserInputString();

        for (int i = 0; i < maps.size(); i++) {
            if (userInput.equalsIgnoreCase(String.valueOf(i))) {
                player.setMap(maps.get(i));
                selectLevel(player);
                isFound = true;
            }
            /*if (userInput.equalsIgnoreCase("B")) {
                resetGame(player);
            } else if (userInput.equalsIgnoreCase(String.valueOf(i))) {
                player.setMap(maps.get(i));
                selectLevel(player);
                isFound = true;
            }*/
        }
        if (!isFound) {
            System.out.println("\nSelected number/letter does not exist.");
            selectMap(player);
        }
    }

    public void selectToCreateOrImportMap() {
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

    public void createMap() {
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

    public void selectLevel(Player player) throws IOException {
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
            /*case 'B':
                // REMOVE previously selected MAP for PLAYER
                player = null;
                selectMap(player);
                break;
            case 'M':
                resetGame(player);
                break;*/
            default:
                System.out.println("\nSelected number/letter does not exist.");
                selectLevel(player);
                break;
        }
    }

    private void enterPlayerName(Player player) throws IOException {
        System.out.print("Enter player's name: ");
        player.setName(readUserInputString());

        equipStartingItems(player);
        generateEnemies();
        selectFromGameMenu(player);
    }

    private void selectFromGameMenu(Player player) throws IOException {
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
                selectFromGameMenu(player);
                break;
            case '5':
                exportPlayersHighScoresToHTML(sortHighScoreList(getPlayersScoresFromExcel()));
                openPlayersHighScoresHTML();
                selectFromGameMenu(player);
                break;
            case 'R':
                restartGame(player);
                break;
            case 'E':
                System.exit(0);
                break;
            default:
                System.out.println("\nSelected number/letter does not exist.");
                selectFromGameMenu(player);
                break;
        }
    }

    public void restartGame(Player player){
        /*for (int i = 0; i < maps.size(); i++) {
            if (maps.get(i).getName().equalsIgnoreCase(player.gameMap.getName())) {
                for (int j = 0; j < player.gameMap.locations.size(); j++) {
                    player.gameMap.locations.get(j).enemies = null;
                }

            }
        }
        player.gameMap.locations = null;
        player.gameMap = null;*/
        player = null;
    }

    private void selectLocation(Player player) throws IOException {
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

    private void selectInventory(Player player) throws IOException {
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

    public void selectInventoryItem(Player player, String itemType) throws IOException {
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

    public void selectInventoryItemAction(Player player, String itemType, Item item) throws IOException {
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

    private void selectShop(Player player) throws IOException {
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

    public void selectShopItem(Player player, String itemType) throws IOException {
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

    public void equipItem(Player player, Item item) {
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

    public boolean isOtherItemEquipped(Player player, Item item) {
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

    public void unequipEquippedItem(Player player, Item item) {
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

    public void sellItem(Player player, Item item) {
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

    public void usePotion(Player player, Item item) {
        for (int i = 0; i < player.potionsList.size(); i++) {
            if (player.potionsList.get(i).name == item.name) {
                player.lifePoints += item.lifePoints;
                player.potionsList.remove(i);
            }
        }
    }

    public void buyItem(Player player, Item item) throws IOException {
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

    public void equipStartingItems(Player player) {
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

    public List<Item> getSelectedPlayerItemList(Player player, String itemType) {
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

    public List<Item> getSelectedShopItemList(Player player, String itemType) {
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

    private void attackEnemy(Player player) throws IOException {
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

    public void attack(Player player, int selectedEnemyIndex) {
        int hits = 0;
        Enemy enemy = player.getMap().getLastLocation().enemies.get(selectedEnemyIndex);

        if (enemy.isAlive) {
            //showPlayerLPAndGold(player);

            if (player.lifePoints > enemy.damagePoints) {
                enemy.lifePoints = enemy.lifePoints - player.damagePoints;
                player.lifePoints = player.lifePoints + player.armorPoints - enemy.damagePoints;
                hits++;

                if (enemy.lifePoints <= 0) {
                    //System.out.println(enemy.type + " has 0 life points left. He is dead.");
                    player.enemiesKilledCounter++;
                    player.gold += 10;
                    scorePlayer(player, hits);
                    enemy.isAlive = false;

                    player.getMap().getLastLocation().enemies.remove(selectedEnemyIndex);

                    if (player.getMap().getLastLocation().enemies.isEmpty()) {
                        for (int i = 0; i < player.getMap().locations.size(); i++) {

                            if (player.getMap().getLastLocation() == player.getMap().locations.get(i)) {
                                player.gold += 50;
                                player.locationsClearedCounter++;
                                player.getMap().locations.remove(i);
                            }
                        }
                    }
                }
                checkIfPlayerEarnedAchievement(player);
            } else {
                System.out.println("You cannot attack selected enemy. You have not enough life points.");
            }
        } else {
            System.out.println(player.type + " is already dead. You cannot attack.");
        }
    }

    public void scorePlayer(Player player, int hits) {
        if(hits == 1) {
            player.setHighScore(player.getHighScore() + 100);
        } else if(hits == 2) {
            player.setHighScore(player.getHighScore() + 50);
        } else {
            player.setHighScore(player.getHighScore() + 25);
        }
    }

    public void showCreateAndImportMapOptions() {
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

    private void showLocations(Player player) throws IOException {
        System.out.println("\n-------------------------LOCATIONS-------------------------");
        showPlayerLPAndGold(player);
        if (player.getMap().locations.size() > 0) {
            for (int i = 0; i < player.getMap().locations.size(); i++) {
                System.out.println(i + ". " + player.getMap().locations.get(i).getName().toUpperCase());
            }
            System.out.println("-----------------------------------------------------------");
            System.out.println("B. BACK");
            System.out.println("-----------------------------------------------------------");
            System.out.print("Choose a number to select a location:");
        } else {
            System.out.println("All locations are clear. No more enemies left.");
            congratulatePlayer(player);
            System.exit(0);
        }
    }

    public void congratulatePlayer(Player player) throws IOException {
        System.out.println("\n--------------------------WINNER---------------------------");

        System.out.println("Congratulations! You have won the game!");
        System.out.println("-----------------------------------------------------------");
        // Create picture from symbols or animation from symbols

        exportPlayerHighScoreToExcel(player);
        exportPlayersHighScoresToHTML(sortHighScoreList(getPlayersScoresFromExcel()));
        openPlayersHighScoresHTML();
    }

    public void openPlayersHighScoresHTML() {
        try {
            File file = new File(FILE_NAME_PLAYERS_HIGH_SCORES);
            if(!Desktop.isDesktopSupported())
            {
                System.out.println("Your system is not supported");
                return;
            }
            Desktop desktop = Desktop.getDesktop();
            if(file.exists())
                desktop.open(file);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void showAchievements(Player player) throws IOException {
        if (player.achievementsList.size() > 0) {
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
        } else {
            System.out.println("Sorry. So far no achievements earned.");
        }
    }

    public void showEnemies(Player player) throws IOException {
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

    public void showInventoryOptions(Player player) {
        System.out.println("\n---------------------------INVENTORY---------------------------");
        showPlayerLPGoldDamageAndArmorPoints(player);
        System.out.println("1. WEAPONS");
        System.out.println("2. ARMOR");
        System.out.println("3. POTIONS");
        System.out.println("---------------------------------------------------------------");
        System.out.println("B. BACK");
        System.out.println("---------------------------------------------------------------");

        System.out.print("Choose a number to select an item category:");
    }

    public void showInventoryItems(Player player, String itemType) throws IOException {
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

    public void showShopOptions(Player player) {
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

    public void showShopItems(Player player, String itemType) throws IOException {
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
                Shop shop = new Shop();
                if (shop.potionsListInShop.get(i).name.equals(shop.POTION_25_LP)) {
                    potion25Counter += 1;
                }
                if (shop.potionsListInShop.get(i).name.equals(shop.POTION_50_LP)) {
                    potion50Counter += 1;
                }
                if (shop.potionsListInShop.get(i).name.equals(shop.POTION_75_LP)) {
                    potion75Counter += 1;
                }
            }
            if (potion25Counter >= 1) {
                System.out.println("1. " + shop.POTION_25_LP.toUpperCase() + " | price: " + shop.potion25.getPrice() + " gold");
            }
            if (potion50Counter >= 1) {
                System.out.println("2. " + shop.POTION_50_LP.toUpperCase() + " | price: " + shop.potion50.getPrice() + " gold");
            }
            if (potion75Counter >= 1) {
                System.out.println("3. " + shop.POTION_75_LP.toUpperCase() + " | price: " + shop.potion75.getPrice() + " gold");
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

    public void showPlayerLPAndGold(Player player) {
        System.out.println("You have: " + player.gold + " gold | " + player.lifePoints + " life points");
        System.out.println("-----------------------------------------------------------");
    }

    public void showPlayerLPGoldDamageAndArmorPoints(Player player) {
        System.out.println("You have: " + player.gold + " gold | " + player.lifePoints + " life points | " + player.damagePoints + " damage points | " + player.armorPoints + " armor points");
        System.out.println("-----------------------------------------------------------");
    }

    public void showInventoryItemAction(Item item) {
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

    public void showStartMenu() {

        System.out.println("\n------------------------START MENU-------------------------");
        System.out.println("1. START GAME");
        System.out.println("2. CREATE / IMPORT MAP");
        System.out.println("3. HIGH SCORES");
        System.out.println("-----------------------------------------------------------");
        System.out.println("E. EXIT GAME");
        System.out.println("-----------------------------------------------------------");

        System.out.print("Choose a number/letter from a START MENU:");
    }

    public void showMapsOptions() {
        System.out.println("\n----------------------------MAPS---------------------------");
        for (int i = 0; i < maps.size(); i++) {
            System.out.println(i + ". " + maps.get(i).getName().toUpperCase());
        }
        System.out.println("-----------------------------------------------------------");
        //System.out.println("B. BACK");
        //System.out.println("-----------------------------------------------------------");

        System.out.print("Choose a number to select a map:");
    }

    public void showLevelsOptions() {
        System.out.println("---------------------------LEVELS--------------------------");
        System.out.println("1. EASY");
        System.out.println("2. MEDIUM");
        System.out.println("3. HARD");
        System.out.println("-----------------------------------------------------------");
        //System.out.println("B. BACK");
        //System.out.println("M. BACK TO START MENU");
        //System.out.println("-----------------------------------------------------------");
        System.out.print("Choose a number to select a difficulty level: ");
    }

    public void showGameMenu() {
        System.out.println("\n-------------------------GAME MENU-------------------------");
        System.out.println("1. LOCATIONS");
        System.out.println("2. INVENTORY");
        System.out.println("3. SHOP");
        System.out.println("4. ACHIEVEMENTS");
        System.out.println("5. HIGH SCORES");
        System.out.println("-----------------------------------------------------------");
        System.out.println("R. RESTART");
        System.out.println("E. EXIT GAME");
        System.out.println("-----------------------------------------------------------");

        System.out.print("Choose a number/letter from a GAME MENU:");
    }

    // HANDLE EXCEPTIONS
    public char readUserInputChar() {
        char userInput = Character.toUpperCase(SCANNER.next().charAt(0));
        SCANNER.nextLine();

        return userInput;
    }

    // HANDLE EXCEPTIONS
    public String readUserInputString() {
        String userInput = SCANNER.nextLine().toUpperCase();

        return userInput;
    }

    // HANDLE EXCEPTIONS
    public int readUserInputInt() {
        int userInput = SCANNER.nextInt();
        SCANNER.nextLine();

        return userInput;
    }

    public void importMapFromExcel() {
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

    public void exportPlayerHighScoreToExcel(Player player) throws IOException {
        try {
            FileInputStream file = new FileInputStream(new File(FILE_NAME_PLAYERS_SCORES));

            XSSFWorkbook workbook = new XSSFWorkbook(file);

            XSSFSheet sheet = workbook.getSheetAt(0);

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Player");
            headerRow.createCell(1).setCellValue("Score");

            Row row = sheet.createRow(sheet.getLastRowNum() + 1);

            Cell playerName = row.createCell(0);
            Cell highScore = row.createCell(1);

            playerName.setCellValue(player.getName());
            highScore.setCellValue(player.getHighScore());


            FileOutputStream fos = new FileOutputStream(new File(FILE_NAME_PLAYERS_SCORES));
            workbook.write(fos);
            fos.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Player> getPlayersScoresFromExcel() {
        List<Player> playersScoresList = new ArrayList<>();
        Player player;

        try {
            FileInputStream file = new FileInputStream(new File(FILE_NAME_PLAYERS_SCORES));

            XSSFWorkbook workbook = new XSSFWorkbook(file);

            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
                Row row = sheet.getRow(i);
                Cell playerName = row.getCell(0);
                Cell highScore = row.getCell(1);

                player = new Player();
                player.setName(playerName.getStringCellValue());
                player.setHighScore((int)highScore.getNumericCellValue());
                playersScoresList.add(player);
            }

            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playersScoresList;
    }
}

    /*public void exportMapsAndLocationsToExcel() {
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
    }
}*/