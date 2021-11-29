public class Enemy extends BaseCharacter {
    public Enemy() {
        type = "Enemy";
        lifePoints = 50;
        armorPoints = 0;
        damagePoints = 10;
        boolean isAlive;
    }

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
