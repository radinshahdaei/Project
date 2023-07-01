package Server.Model.Building.Nature;

public enum TreeType {
    DATE("date"),
    COCONUT("coconut"),
    OLIVE("olive"),
    CHERRY("cherry"),
    DESERT("desert");
    String name;

    TreeType(String name) {
        this.name = name;
    }

    public static TreeType getTreeByName(String name) {
        for (TreeType tree : TreeType.values()) {
            if (name.equalsIgnoreCase(tree.name))
                return tree;
        }
        return null;
    }
}
