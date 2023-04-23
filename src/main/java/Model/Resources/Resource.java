package Model.Resources;

import java.util.ArrayList;

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
    }
}
