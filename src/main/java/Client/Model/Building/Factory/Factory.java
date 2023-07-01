package Client.Model.Building.Factory;

import Client.Model.Game;
import Client.Model.Resources.Resource;
import Client.Model.Resources.ResourceModel;
import Client.Model.Resources.ResourceType;
import Client.Model.User;
import Client.Model.Building.Building;
import Client.Model.Building.Storage.Storage;

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
        if (this.getHp() == 0) return;
        if (consumed != null) {
            ResourceModel resourceModel = consumed.getResourceType().resourceModel;
            String storageName = getStorageName(resourceModel);
            Storage storage = (Storage) Game.getGovernmentByUser(this.getOwner()).findBuildingByName(storageName);
            if (storage == null) return;
            storage.removeFromStorage(consumed);
        }
        if (produced == null) return;
        ResourceModel resourceModel = produced.getResourceType().resourceModel;
        String storageName = getStorageName(resourceModel);
        Storage storage = (Storage) Game.getGovernmentByUser(this.getOwner()).findBuildingByName(storageName);
        if (storage == null) return;
        storage.addToStorage(produced);
        if (super.getName().equals("diary farmer")) //diary farmer also produces cows!
            storage.addToStorage(Resource.createResource(ResourceType.COW, 1));
    }

    public static String getStorageName(ResourceModel resourceModel) {
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
