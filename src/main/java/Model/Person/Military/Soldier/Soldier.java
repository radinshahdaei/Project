package Model.Person.Military.Soldier;

import Model.Government;
import Model.Person.Military.MilitaryUnit;
import Model.Resources.Resource;

import java.util.ArrayList;

public class Soldier extends MilitaryUnit {
    SoldierGuild soldierGuild;
    Boolean canUseLadder;

    public Soldier(String name, int x, int y, int speed, int attack, int defence, int range, Government military, ArrayList<Resource> price, SoldierGuild soldierGuild, Boolean canUseLadder) {
        super(name, x, y, speed, attack, defence, range, military, price);
        this.soldierGuild = soldierGuild;
        this.canUseLadder = canUseLadder;
    }
}
