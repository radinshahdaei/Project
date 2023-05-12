package Model.Resources;

import java.util.ArrayList;
import java.util.HashMap;

public class Resource {
    ResourceType resourceType;
    int count;

    public Resource(ResourceType resourceType, int count) {
        this.resourceType = resourceType;
        this.count = count;
    }

    public static ArrayList<Resource> getResources(String... args) {
        ArrayList<Resource> resources = new ArrayList<>();
        for (int i = 0; i < args.length; i += 2) {
            ResourceType resourceType = ResourceType.getResourceByName(args[i]);
            int count = Integer.parseInt(args[i + 1]);
            Resource resource = new Resource(resourceType, count);
            resources.add(resource);
        }
        return resources;
    }

    public static HashMap<Resource, Resource> getWeapons(String... args) {
        HashMap<Resource, Resource> weapons = new HashMap<>();
        for (int i = 0; i < args.length; i += 4) {
            ResourceType weapon = ResourceType.getResourceByName(args[i]);
            int weaponCount = Integer.parseInt(args[i + 1]);
            ResourceType weaponPrice = ResourceType.getResourceByName(args[i + 2]);
            int weaponPriceCount = Integer.parseInt(args[i + 3]);
            Resource weaponResource = new Resource(weapon, weaponCount);
            Resource priceResource = new Resource(weaponPrice, weaponPriceCount);
            weapons.put(weaponResource, priceResource);
        }
        return weapons;
    }

    public static Resource createResource(ResourceType resourceType, int count) {
        return new Resource(resourceType, count);
    }

    public static ArrayList<Resource> getResourcesByType(ResourceModel resourceModel, int count) {
        ArrayList<Resource> resources = new ArrayList<>();
        for (ResourceType resourceType : ResourceType.values()) {
            if (resourceModel.equals(resourceType.resourceModel)) {
                Resource resource = new Resource(resourceType, count);
                resources.add(resource);
            }
        }
        return resources;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void addCount(int amount) {
        count += amount;
        if (count < 0) count = 0;
    }
}
