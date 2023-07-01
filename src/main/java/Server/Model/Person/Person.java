package Server.Model.Person;

import Client.Model.Person.PersonType;
import Client.Model.User;

public class Person {
    private String name;
    private PersonType type;

    private User owner;

    public Person(String name, PersonType type, User owner) {
        this.name = name;
        this.type = type;
        this.owner = owner;
    }

    public void makeMilitary() {
        this.type = PersonType.MILITARY_UNIT;
    }

    public void makeWorker() {
        this.type = PersonType.WORKER;
    }

    public PersonType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }
}
