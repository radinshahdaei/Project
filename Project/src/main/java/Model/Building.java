package Model;

public class Building {
    private int hp;
    private String name;
    private int goldPrice;
    private int woodPrice;
    private int ironPrice;
    private int stonePrice;
    private int pitchPrice;
    private int workers;
    public Building(int hp, String name) {
        this.hp = hp;
        this.name = name;
        this.goldPrice = 0;
        this.woodPrice = 0;
        this.ironPrice = 0;
        this.stonePrice = 0;
        this.pitchPrice = 0;
        this.workers = 0;
    }

    public int getWorkers() {
        return workers;
    }

    public int getHp() {
        return hp;
    }

    public String getName() {
        return name;
    }

    public int getGoldPrice() {
        return goldPrice;
    }

    public int getWoodPrice() {
        return woodPrice;
    }

    public int getIronPrice() {
        return ironPrice;
    }

    public int getStonePrice() {
        return stonePrice;
    }

    public int getPitchPrice() {
        return pitchPrice;
    }
}
