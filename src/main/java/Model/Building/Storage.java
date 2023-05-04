package Model.Building;

import Model.Resources.Resource;
import Model.Resources.ResourceModel;
import Model.Resources.ResourceType;
import Model.User;

import java.util.ArrayList;

import static Model.Resources.Resource.getResourcesByType;

public class Storage extends Building {
    private ArrayList<Resource> storage = new ArrayList<>();
    private int maxCapacity;

    public Storage(String name, int hp, int x, int y, int workers, ArrayList<Resource> price, ArrayList<Resource> storage, int maxCapacity, User owner) {
        super(name, hp, x, y, workers, price, owner);
        this.storage = storage;
        this.maxCapacity = maxCapacity;
    }

    public ArrayList<Resource> getStorage() {
        return storage;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public static Building createBuilding(String name, int x, int y, User owner) {
        StorageType building = StorageType.getBuildingByName(name);
        if (building == null) return null;
        int hp = building.hp;
        int workers = building.workers;
        ArrayList<Resource> price = building.price;
        ArrayList<Resource> storageBuffer = building.storage;
        ArrayList<Resource> storage = new ArrayList<>();
        for (Resource tempResource : storageBuffer) {
            Resource resource = new Resource(tempResource.getResourceType(), tempResource.getCount());
            storage.add(resource);
        }
        int maxCapacity = building.maxCapacity;
        return new Storage(name, hp, x, y, workers, price, storage, maxCapacity, owner);
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

    public void setStorage(ArrayList<Resource> storage) {
        this.storage = storage;
    }
}
