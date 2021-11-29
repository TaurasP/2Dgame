abstract class BaseCharacter {
    String type;
    int lifePoints;
    int armorPoints;
    int damagePoints;
    boolean isAlive = true;
    String name;
    int gold = 0;

    abstract String getType();

    abstract int getLifePoints();

    abstract int getArmorPoints();

    abstract int getDamagePoints();

    abstract boolean getIsAlive();

    abstract int getGold();

    abstract void setLifePoints(int lifePoints);

    abstract void setName(String name);

    abstract String getName();

    abstract void setArmorPoints(int armorPoints);

    abstract void setDamagePoints(int damagePoints);

    abstract void setIsAlive(boolean alive);

    abstract void setGold(int gold);
}
