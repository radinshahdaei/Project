package Model.Person;

import Model.User;

public class Person {
    private String name;
    private PersonType type;

    private User owner;

    public Person(String name, PersonType type, User owner) {
        this.name = name;
        this.type = type;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }
}
