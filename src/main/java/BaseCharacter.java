public class BaseCharacter {
    String type;
    int lifePoints;
    int armorPoints;
    int damagePoints;
    boolean isAlive = true;
    String name;
    int gold = 0;

    public String getType() {
        return type;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public int getArmorPoints() {
        return armorPoints;
    }

    public int getDamagePoints() {
        return damagePoints;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public int getGold() {
        return gold;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setArmorPoints(int armorPoints) {
        this.armorPoints = armorPoints;
    }

    public void setDamagePoints(int damagePoints) {
        this.damagePoints = damagePoints;
    }

    public void setIsAlive(boolean alive) {
        isAlive = alive;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}
