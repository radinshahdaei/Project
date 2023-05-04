package Model.Person;

public class Person {
    private String name;
    private PersonType type;

    public Person(String name, PersonType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }
}
