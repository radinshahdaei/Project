package Client.Model.Building.Factory;

import Client.Model.Resources.Resource;
import Client.Model.Resources.ResourceType;

import java.util.ArrayList;

public enum FactoryType {

    IRON_MINE("iron mine", 100, 2, Resource.getResources("wood", "20"), Resource.createResource(ResourceType.IRON, 2), null),
    QUARRY("quarry", 100, 3, Resource.getResources("wood", "20"), Resource.createResource(ResourceType.STONE, 2), null),
    WOOD_CUTTER("wood cutter", 100, 1, Resource.getResources("wood", "3"), Resource.createResource(ResourceType.WOOD, 5), null),
    PITCH_RIG("pitch rig", 100, 1, Resource.getResources("wood", "20"), Resource.createResource(ResourceType.PITCH, 1), null),
    APPLE_ORCHARD("apple orchard", 100, 1, Resource.getResources("wood", "5"), Resource.createResource(ResourceType.APPLE, 3), null),
    HOPS_FARMER("hops farmer", 100, 1, Resource.getResources("wood", "15"), Resource.createResource(ResourceType.HOPS, 3), null),
    HUNTER_POST("hunter post", 100, 1, Resource.getResources("wood", "5"), Resource.createResource(ResourceType.MEAT, 3), null),
    DIARY_FARMER("diary farmer", 100, 1, Resource.getResources("wood", "10"), Resource.createResource(ResourceType.CHEESE, 3), null),
    WHEAT_FARMER("wheat farmer", 100, 1, Resource.getResources("wood", "15"), Resource.createResource(ResourceType.WHEAT, 5), null),
    MILL("mill", 100, 3, Resource.getResources("wood", "20"), Resource.createResource(ResourceType.FLOUR, 3), Resource.createResource(ResourceType.WHEAT, 3)),
    BAKERY("bakery", 100, 1, Resource.getResources("wood", "10"), Resource.createResource(ResourceType.BREAD, 3), Resource.createResource(ResourceType.FLOUR, 3)),
    BREWER("brewer", 100, 1, Resource.getResources("wood", "10"), Resource.createResource(ResourceType.ALE, 2), Resource.createResource(ResourceType.HOPS, 1)),
    INN("inn", 100, 1, Resource.getResources("wood", "20", "gold", "100"), null, Resource.createResource(ResourceType.ALE, 2));

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
