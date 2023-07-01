package Client.Model.Building.Nature;

public enum RockType {
    NORTH('n'),
    EAST('e'),
    WEST('w'),
    SOUTH('s');

    char direction;

    RockType(char direction) {
        this.direction = direction;
    }

    public static RockType getRockByDirection(char direction) {
        for (RockType rock : RockType.values()) {
            if (direction == rock.direction)
                return rock;
        }
        return null;
    }
}
