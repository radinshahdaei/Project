package Model.Building.Factory;

import Model.Resources.Resource;
import Model.Resources.ResourceType;

import java.util.ArrayList;

import static Model.Resources.Resource.createResource;
import static Model.Resources.Resource.getResources;

public enum FactoryType {

    IRON_MINE("iron mine", 100, 2, getResources("wood", "20"), createResource(ResourceType.IRON, 2), null),
    QUARRY("quarry", 100, 3, getResources("wood", "20"), createResource(ResourceType.STONE, 2), null),
    WOOD_CUTTER("wood cutter", 100, 1, getResources("wood", "3"), createResource(ResourceType.WOOD, 5), null),
    PITCH_RIG("pitch rig", 100, 1, getResources("wood", "20"), createResource(ResourceType.PITCH, 1), null),
    APPLE_ORCHARD("apple orchard", 100, 1, getResources("wood", "5"), createResource(ResourceType.APPLE, 3), null),
    HOPS_FARMER("hops farmer", 100, 1, getResources("wood", "15"), createResource(ResourceType.HOPS, 3), null),
    HUNTER_POST("hunter post", 100, 1, getResources("wood", "5"), createResource(ResourceType.MEAT, 3), null),
    DIARY_FARMER("diary farmer", 100, 1, getResources("wood", "10"), createResource(ResourceType.CHEESE, 3), null),
    WHEAT_FARMER("wheat farmer", 100, 1, getResources("wood", "15"), createResource(ResourceType.WHEAT, 5), null),
    MILL("mill", 100, 3, getResources("wood", "20"), createResource(ResourceType.FLOUR, 3), createResource(ResourceType.WHEAT, 3)),
    BAKERY("bakery", 100, 1, getResources("wood", "10"), createResource(ResourceType.BREAD, 3), createResource(ResourceType.FLOUR, 3)),
    BREWER("brewer", 100, 1, getResources("wood", "10"), createResource(ResourceType.ALE, 2), createResource(ResourceType.HOPS, 1)),
    INN("inn", 100, 1, getResources("wood", "20", "gold", "100"), null, createResource(ResourceType.ALE, 2));

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
