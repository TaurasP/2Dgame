import java.util.ArrayList;
import java.util.List;

public class Player extends BaseCharacter {

    public GameMap gameMap;
    public List<Achievement> achievementsList = new ArrayList<>();
    public List<Item> weaponsList = new ArrayList<>();
    public List<Item> armorList = new ArrayList<>();
    public List<Item> potionsList = new ArrayList<>();

    public int enemiesKilledCounter;
    public int locationsClearedCounter;
    public int highScore = 0;
    public int baseDamagePoints = 5;

    public Player() {
        type = "Player";
        lifePoints = 100;
        armorPoints = 0;
        damagePoints = baseDamagePoints;
        boolean isAlive = true;
        gold = 100; // pagal lygi padaryti skirtinga gold kieki?
        enemiesKilledCounter = 0;
        locationsClearedCounter = 0;
    }

    public Player(String name) {
        this.name = name;
        type = "Player";
        lifePoints = 100;
        armorPoints = 0;
        damagePoints = 0;
        boolean isAlive = true;
        gold = 100; // pagal lygi padaryti skirtinga gold kieki?
        enemiesKilledCounter = 0;
        locationsClearedCounter = 0;
    }

    public GameMap getMap() {
        return gameMap;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}
