package Model.Building.Factory;

import Model.Building.Building;
import Model.Building.Storage.Storage;
import Model.Game;
import Model.Resources.Resource;
import Model.Resources.ResourceModel;
import Model.Resources.ResourceType;
import Model.User;

import java.util.ArrayList;

public class Factory extends Building {
    Resource produced;
    Resource consumed;

    public Factory(String name, int hp, int x, int y, int workers, ArrayList<Resource> price, Resource produced, Resource consumed, User owner) {
        super(name, hp, x, y, workers, price, owner);
        this.produced = produced;
        this.consumed = consumed;
    }

    public static Building createBuilding(String name, int x, int y, User owner) {
        FactoryType building = FactoryType.getBuildingByName(name);
        if (building == null) return null;
        int hp = building.hp;
        int workers = building.workers;
        ArrayList<Resource> price = building.price;
        Resource produced = building.produced;
        Resource consumed = building.consumed;
        return new Factory(name, hp, x, y, workers, price, produced, consumed, owner);
    }

    public void doWork() {
        if (consumed != null) {
            ResourceModel resourceModel = consumed.getResourceType().resourceModel;
            String storageName = getStorageName(resourceModel);
            Storage storage = (Storage) Game.currentGovernment.findBuildingByName(storageName);
            if (storage == null) return;
            storage.removeFromStorage(consumed);
        }

        ResourceModel resourceModel = produced.getResourceType().resourceModel;
        String storageName = getStorageName(resourceModel);
        Storage storage = (Storage) Game.currentGovernment.findBuildingByName(storageName);
        if (storage == null) return;
        storage.addToStorage(produced);
        if (super.getName().equals("diary farmer")) //diary farmer also produces cows!
            storage.addToStorage(Resource.createResource(ResourceType.COW, 1));
    }

    public String getStorageName(ResourceModel resourceModel) {
        String storageName = null;
        if (resourceModel.equals(ResourceModel.FOOD)) {
            storageName = "granary";
        } else if (resourceModel.equals(ResourceModel.WEAPON)) {
            storageName = "armory";
        } else if (resourceModel.equals(ResourceModel.HORSE)) {
            storageName = "stable";
        } else if (resourceModel.equals(ResourceModel.OTHER)) {
            storageName = "stockpile";
        }
        return storageName;
    }


}
