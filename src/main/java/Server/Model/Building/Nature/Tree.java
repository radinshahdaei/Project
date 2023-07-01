package Server.Model.Building.Nature;

import Client.Model.Building.Building;
import Client.Model.Building.Nature.TreeType;

public class Tree extends Building {
    TreeType type;

    public Tree(String name, int x, int y, TreeType type) {
        super(name, 100, x, y, 0, null, null);
        this.type = type;
    }

    public static Building createTree(String typeName, int x, int y) {
        TreeType type = TreeType.getTreeByName(typeName);
        String name = typeName + " tree";
        return new Tree(name, x, y, type);
    }
}
