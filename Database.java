import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Database {
    // weapon maker
    public static Image mainfletcher = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Weapon Makers/fletcher/0_0img0.png").toExternalForm());
    public static Image mainBlackSmith = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Weapon Makers/blacksmith/0_0img0.png").toExternalForm());
    public static Image mainArmourer = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Weapon Makers/armourer/0_0img0.png").toExternalForm());
    public static Image mainTanner = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Weapon Makers/tanner/0_0img0.png").toExternalForm());

    // unit
    public static Image arabArcher = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Units/sc_gameinfo_units_arabarcher.png").toExternalForm());
    public static Image arabWordsMan = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Units/sc_gameinfo_units_arabswordsman.png").toExternalForm());
    public static Image assassin = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Units/sc_gameinfo_units_assassin.png").toExternalForm());
    public static Image fireballista = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Units/sc_gameinfo_units_fireballista.png").toExternalForm());
    public static Image fireThrower = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Units/sc_gameinfo_units_firethrower.png").toExternalForm());
    public static Image horseArcher = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Units/sc_gameinfo_units_horsearcher.png").toExternalForm());
    public static Image slave = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Units/sc_gameinfo_units_slave.png").toExternalForm());
    public static Image slinger = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Units/sc_gameinfo_units_slinger.png").toExternalForm());

    // Arabian troops
    public static Image arabAssassin = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Troops/Arabian Troops/body_arab_assasin.gm1/0_0img0.png").toExternalForm());
    public static Image arabBallista = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Troops/Arabian Troops/body_arab_ballista.gm1/0_0img0.png").toExternalForm());
    public static Image grenadier = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Troops/Arabian Troops/body_arab_grenadier.gm1/0_0img0.png").toExternalForm());
    public static Image shortBow = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Troops/Arabian Troops/body_arab_shortbow.gm1/0_0img0.png").toExternalForm());
    public static Image arabSlave = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Troops/Arabian Troops/body_arab_slave.gm1/0_0img0.png").toExternalForm());
    public static Image arabSlinger = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Troops/Arabian Troops/body_arab_slinger.gm1/0_0img0.png").toExternalForm());
    public static Image arabSwordsman = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Troops/Arabian Troops/body_arab_swordsman.gm1/0_0img0.png").toExternalForm());
    public static Image arabHorseArcher = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Troops/Arabian Troops/body_horse_archer.gm1/0_0img0.png").toExternalForm());

    //European troops
    public static Image europeanArcher = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Troops/European Troops/body_archer.gm1/0_0img0.png").toExternalForm());
    public static Image batteringRam = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Troops/European Troops/body_battering_ram.gm1/0_0img0.png").toExternalForm());
    public static Image fightingMonk = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Troops/European Troops/body_fighting_monk.gm1/0_0img0.png").toExternalForm());
    public static Image ladderBearer = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Troops/European Troops/body_ladder_bearer.gm1/0_0img0.png").toExternalForm());
    public static Image maceMan = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Troops/European Troops/body_maceman.gm1/0_0img0.png").toExternalForm());
    public static Image mangoNel = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Troops/European Troops/body_mangonel.gm1/0_0img0.png").toExternalForm());
    public static Image siegeEngineer = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Troops/European Troops/body_siege_engineer.gm1/0_0img0.png").toExternalForm());
    public static Image siegeTower = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Troops/European Troops/body_siege_tower.gm1/0_0img0.png").toExternalForm());
    public static Image tunneler = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Troops/European Troops/body_tunnelor.gm1/0_0img0.png").toExternalForm());

    //Food industry workers
    public static Image baker =  new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Food Industry Worker/body_baker.gm1/0_0img0.png").toExternalForm());
    public static Image brewer =  new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Food Industry Worker/body_brewer.gm1/0_0img0.png").toExternalForm());
    public static Image farmer =  new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Food Industry Worker/body_farmer.gm1/0_0img0.png").toExternalForm());
    public static Image hunter =  new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Food Industry Worker/body_hunter.gm1/0_0img0.png").toExternalForm());
    public static Image innKeeper =  new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Food Industry Worker/body_innkeeper.gm1/0_0img0.png").toExternalForm());
    public static Image miller =  new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Food Industry Worker/body_miller.gm1/0_0img0.png").toExternalForm());

    //Animals
    public static Image cow = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Animals/body_cow.gm1/0_0img0.png").toExternalForm());
    public static Image crow = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Animals/body_crow.gm1/0_0img0.png").toExternalForm());
    public static Image deer = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Animals/body_deer.gm1/0_0img0.png").toExternalForm());
    public static Image dog = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Animals/body_dog.gm1/0_0img0.png").toExternalForm());
    public static Image girl = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Animals/body_girl.gm1/0_0img0.png").toExternalForm());
    public static Image lion = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Animals/body_lion.gm1/0_0img0.png").toExternalForm());
    public static Image chicken = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Animals/body_chicken.gm1/0_0img0.png").toExternalForm());
    public static Image camel = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Animals/body_camel.gm1/0_0img0.png").toExternalForm());
    public static Image rabbit = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Animals/body_rabbit.gm1/0_0img0.png").toExternalForm());

    //Others
    public static Image boy = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Other NPCs/body_boy.gm1/0_0img0.png").toExternalForm());
    public static Image drunkard = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Other NPCs/body_drunkard.gm1/0_0img0.png").toExternalForm());
    public static Image fireEater = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Other NPCs/body_fire_eater.gm1/0_0img0.png").toExternalForm());
    public static Image fireMan = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Other NPCs/body_fireman.gm1/0_0img0.png").toExternalForm());
    public static Image ghost = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Other NPCs/body_ghost.gm1/0_0img0.png").toExternalForm());
    public static Image healer = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Other NPCs/body_healer.gm1/0_0img0.png").toExternalForm());
    public static Image jester = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Other NPCs/body_jester.gm1/0_0img0.png").toExternalForm());
    public static Image juggler = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Other NPCs/body_juggler.gm1/0_0img0.png").toExternalForm());
    public static Image lady = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Other NPCs/body_lady.gm1/0_0img0.png").toExternalForm());
    public static Image peasant = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Other NPCs/body_peasant.gm1/0_0img0.png").toExternalForm());
    public static Image priest = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Other NPCs/body_priest.gm1/0_0img0.png").toExternalForm());

    //Resource Gatherers
    public static Image horseTrader = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Resource Gatherers/body_horse_trader.gm1/0_0img0.png").toExternalForm());
    public static Image ironMiner = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Resource Gatherers/body_iron_miner.gm1/0_0img0.png").toExternalForm());
    public static Image stoneMason = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Resource Gatherers/body_stonemason.gm1/0_0img0.png").toExternalForm());
    public static Image trader = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Resource Gatherers/body_trader.gm1/0_0img0.png").toExternalForm());
    public static Image woodCutter = new Image(Database.class.getResource("/Images/NPCs _ Soldiers/Resource Gatherers/body_woodcutter.gm1/0_0img0.png").toExternalForm());
}
