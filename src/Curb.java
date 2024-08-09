// curb is for those who are not coming as they should bite the curb
// created by Miles O'Hara-Dewhurst
// created on 5/8/2024
// last edited on 5/8/2024

import java.util.ArrayList;
import java.util.Objects;

public class Curb {
    private ArrayList<String> people;

    public Curb() {
        people = new ArrayList<>();
    }

    public String toString() {
        return people.size() + " people can bite the curb. They are: " + people;
    }

    public ArrayList<String> getPeople() {
        return people;
    }

    public int getAmtOfPeople() {
        return people.size();
    }

    public void addPerson(Person person) {
        people.add(person.getName());
        System.out.println(person.getName() + " added to curb");
    }

    public void removePerson(String inName) {
        int i = 0;
        while (i < people.size()) {
            if (Objects.equals(inName, (people.get(i)))) {
                people.remove(i);
                System.out.println(inName + " no longer has to bite the curb");
                return;
            }
            i++;
        }
        System.out.println(inName + " is not biting the curb");
    }
}
