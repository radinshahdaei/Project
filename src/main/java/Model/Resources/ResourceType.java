package Model.Resources;

public enum ResourceType {
    WOOD("wood", ResourceModel.OTHER),
    STONE("stone", ResourceModel.OTHER),
    IRON("iron", ResourceModel.OTHER),
    PITCH("pitch", ResourceModel.OTHER),
    WHEAT("wheat", ResourceModel.OTHER),
    FLOUR("flour", ResourceModel.OTHER),
    HOPS("hops", ResourceModel.OTHER),
    ALE("ale", ResourceModel.FOOD),
    GOLD("gold", ResourceModel.OTHER),
    MEAT("meat", ResourceModel.FOOD),
    APPLE("apple", ResourceModel.FOOD),
    CHEESE("cheese", ResourceModel.FOOD),
    BREAD("bread", ResourceModel.FOOD),
    BOW("bow", ResourceModel.WEAPON),
    CROSSBOW("crossbow", ResourceModel.WEAPON),
    SPEAR("spear", ResourceModel.WEAPON),
    PIKE("pike", ResourceModel.WEAPON),
    MACE("mace", ResourceModel.WEAPON),
    SWORD("sword", ResourceModel.WEAPON),
    LEATHER_ARMOUR("leather armor", ResourceModel.WEAPON),
    METAL_ARMOUR("metal armor", ResourceModel.WEAPON),
    COW("cow", ResourceModel.OTHER),
    HORSE("horse", ResourceModel.HORSE);

    public String name;
    public ResourceModel resourceModel;

    ResourceType(String name, ResourceModel resourceModel) {
        this.name = name;
        this.resourceModel = resourceModel;
    }

    public static ResourceType getResourceByName(String name) {
        for (ResourceType resourceType : ResourceType.values()) {
            if (name.equalsIgnoreCase(resourceType.name))
                return resourceType;
        }
        return null;
    }

    public ResourceModel getResourceModel() {
        return resourceModel;
    }
}
