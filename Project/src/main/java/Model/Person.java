package Model;

public class Person {
    private String name;
    private int speed;
    private int defend;
    private int attack;
    private int maxRange;
    private int minRange;
    private int cost;
    private boolean ladder;
    private boolean needHorse;
    private String mood;
    public Person(String name, int speed, int defend, int attack, int maxRange, int minRange) {
        this.name = name;
        this.speed = speed;
        this.defend = defend;
        this.attack = attack;
        this.maxRange = maxRange;
        this.minRange = minRange;
        this.cost = 0;
        this.ladder = false;
        this.needHorse = false;
        this.mood = null;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDefend() {
        return defend;
    }

    public int getAttack() {
        return attack;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public String getName() {
        return name;
    }

    public boolean isLadder() {
        return ladder;
    }

    public void setLadder(boolean ladder) {
        this.ladder = ladder;
    }

    public int getMinRange() {
        return minRange;
    }

    public int getCost() {
        return cost;
    }

    public boolean isNeedHorse() {
        return needHorse;
    }

    public void setNeedHorse(boolean needHorse) {
        this.needHorse = needHorse;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }
}
