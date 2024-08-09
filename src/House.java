// house is for those who are coming because they will be in the house
// created by Miles O'Hara-Dewhurst
// created on 5/8/2024
// last edited on 5/8/2024

import java.util.ArrayList;

public class House {
    private ArrayList<String> people;
    private double price;
    private ArrayList<Integer> drinks;

    public House() {
        people = new ArrayList<>();
        price = 2417;
        drinks = new ArrayList<>();
    }

    public double getPrice() {
        return price;
    }

    public int getDrinks() {
        int i = 0;
        int totalDrinks = 0;
        while (i < drinks.size()) {
            totalDrinks += drinks.get(i);
            i++;
        }
        return totalDrinks;
    }

    public String toString() {
        return people.size() + " people are coming. They are: " + people + "\nThe current price per person is: $" + (price/people.size());
    }

    public double calculatePrice() {
        return price/people.size();
    }

    public int getAmtOfPeople() {
        return people.size();
    }

    public ArrayList<String> getPeople() {
        return people;
    }

    public void addPerson(Person person, String inDrinks) {
        people.add(person.getName());
        int i;
        try {
            i = Integer.parseInt(inDrinks);
            if (i == 0) {
                i = 20;
            }
        } catch (Exception ex) {
            i = 20;
        }
        drinks.add(i);
        System.out.println(person.getName() + " added to house");
    }

}
