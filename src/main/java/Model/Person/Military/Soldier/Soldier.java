package Model.Person.Military.Soldier;

import Model.Government;
import Model.Person.Military.MilitaryUnit;
import Model.Person.Military.Siege.Siege;
import Model.Person.Military.Siege.SiegeType;
import Model.Resources.Resource;
import Model.User;

import java.util.ArrayList;

public class Soldier extends MilitaryUnit {
    SoldierGuild soldierGuild;
    Boolean canUseLadder;

    public Soldier(String name, int x, int y, int speed, int attack, int defence, int range, User owner, ArrayList<Resource> price, SoldierGuild soldierGuild, Boolean canUseLadder) {
        super(name, x, y, speed, attack, defence, range, price, owner);
        this.soldierGuild = soldierGuild;
        this.canUseLadder = canUseLadder;
    }
    public static MilitaryUnit createUnit(String name,int x,int y,User owner){
        SoldierType unit = SoldierType.getUnitByName(name);
        if (unit == null) return null;
        int speed = unit.speed;
        int attack = unit.attack;
        int defence = unit.defence;
        int range = unit.range;
        boolean canUseLadder = unit.canUseLadder;
        ArrayList<Resource> price = unit.price;
        SoldierGuild type = unit.type;
        return new Soldier(name,x,y,speed,attack,defence,range,owner,price,type,canUseLadder);
    }
}
