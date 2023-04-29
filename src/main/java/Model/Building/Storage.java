package Model.Building;

import Model.Resources.Resource;
import Model.Resources.ResourceType;

import java.util.ArrayList;

public class Storage extends Building {
    ArrayList<Resource> storage = new ArrayList<>();
    int maxCapacity;

    public Storage(String name, int hp, int x, int y, int workers, ArrayList<Resource> price, ArrayList<Resource> storage, int maxCapacity) {
        super(name, hp, x, y, workers, price);
        this.storage = storage;
        this.maxCapacity = maxCapacity;
    }

    public static Building createBuilding(String name, int x, int y) {
        StorageType building = StorageType.getBuildingByName(name);
        if (building == null) return null;
        int hp = building.hp;
        int workers = building.workers;
        ArrayList<Resource> price = building.price;
        ArrayList<Resource> storage = building.storage;
        int maxCapacity = building.maxCapacity;
        return new Storage(name, hp, x, y, workers, price, storage, maxCapacity);
    }

    public Resource getStoredResourceByType(ResourceType resourceType) {
        for (Resource resource : storage) {
            if (resourceType.equals(resource.getResourceType())) {
                return resource;
            }
        }
        return null;
    }

    public void addToStorage(Resource resource) {
        Resource storedResource = getStoredResourceByType(resource.getResourceType());
        storedResource.addCount(resource.getCount());
        if (storedResource.getCount() > maxCapacity) {
            storedResource.setCount(maxCapacity);
        }

    }

    public void removeFromStorage(Resource resource) {
        Resource storedResource = getStoredResourceByType(resource.getResourceType());
        storedResource.addCount(-resource.getCount());
        if (storedResource.getCount() <= 0) {
            storage.remove(storedResource);
        }
    }

}
