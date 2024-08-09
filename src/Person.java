// an object for an individual person
// created by Miles O'Hara-Dewhurst
// created on 5/8/2024
// last edited on 5/8/2024

import java.util.Objects;

public class Person {
    private final String name;
    private String phone;
    private String email;
    private Boolean paid;
    private Boolean coming;
    private int drinks;

    public Person(String inName, String inComing) {
        name = inName;
        paid = false;
        coming = Objects.equals(inComing, "true");
    }

    public Person(String inName, String inNumber, String inEmail, String inComing, String inDrinks) {
        name = inName;
        phone = inNumber;
        email = inEmail;

        if (Objects.equals(inComing, "true")) {
            coming = Boolean.TRUE;
        } else {
            coming = Boolean.FALSE;
        }
    }

    public String getName() {
        return name;
    }

    public boolean getComing() {
        return coming;
    }
}
