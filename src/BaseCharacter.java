public class BaseCharacter {
    String type;
    int lifePoints;
    int armorPoints;
    int damagePoints;
    boolean isAlive = true;

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

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }
}
