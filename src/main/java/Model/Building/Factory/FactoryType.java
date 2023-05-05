package Model.Building.Factory;

import Model.Resources.Resource;
import Model.Resources.ResourceType;

import java.util.ArrayList;

import static Model.Resources.Resource.createResource;
import static Model.Resources.Resource.getResources;

public enum FactoryType {

    IRON_MINE("iron mine", 100, 2, getResources("wood", "20"), createResource(ResourceType.IRON, 1), null),
    QUARRY("quarry", 100, 3, getResources("wood", "20"), createResource(ResourceType.STONE, 1), null),
    WOOD_CUTTER("wood cutter", 100, 1, getResources("wood", "3"), createResource(ResourceType.WOOD, 1), null),
    PITCH_RIG("pitch rig", 100, 1, getResources("wood", "20"), createResource(ResourceType.PITCH, 1), null),
    APPLE_ORCHARD("apple orchard", 100, 1, getResources("wood", "5"), createResource(ResourceType.APPLE, 1), null),
    HOPS_FARMER("hops farmer", 100, 1, getResources("wood", "15"), createResource(ResourceType.HOPS, 1), null),
    HUNTER_POST("hunter post", 100, 1, getResources("wood", "5"), createResource(ResourceType.MEAT, 1), null),
    DIARY_FARMER("diary farmer", 100, 1, getResources("wood", "10"), createResource(ResourceType.CHEESE, 1), null),
    WHEAT_FARMER("wheat farmer", 100, 1, getResources("wood", "15"), createResource(ResourceType.WHEAT, 1), null),
    MILL("mill", 100, 3, getResources("wood", "20"), createResource(ResourceType.FLOUR, 1), createResource(ResourceType.WHEAT, 1)),
    BAKERY("bakery", 100, 1, getResources("wood", "10"), createResource(ResourceType.BREAD, 1), createResource(ResourceType.FLOUR, 1)),
    BREWER("brewer", 100, 1, getResources("wood", "10"), createResource(ResourceType.ALE, 1), createResource(ResourceType.HOPS, 1));

    String name;
    int hp;
    int workers;
    ArrayList<Resource> price = new ArrayList<>();
    Resource produced;
    Resource consumed;
    FactoryType(String name, int hp, int workers, ArrayList<Resource> price, Resource produced, Resource consumed) {
        this.name = name;
        this.hp = hp;
        this.workers = workers;
        this.price = price;
        this.produced = produced;
        this.consumed = consumed;
    }

    public static FactoryType getBuildingByName(String name) {
        for (FactoryType building : FactoryType.values()) {
            if (name.equalsIgnoreCase(building.name))
                return building;
        }
        return null;
    }
}
