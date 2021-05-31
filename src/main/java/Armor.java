public class Armor extends Item{

    public Armor(String name, int price, int armor) {
        this.type = "Armor";
        this.name = name;
        this.price = price;
        this.armor = armor;
        this.isEquipped = false;
    }
}
