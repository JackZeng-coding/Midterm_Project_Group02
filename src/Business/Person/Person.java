/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Person;

/**
 * A real person in the system. One person can hold several role profiles.
 *
 * @author kal bugrara
 * @author Jiahao Zeng (added name/email fields and auto-generated ID)
 */
public class Person {

    private static int nextId = 1000;   // shared counter for all Person objects
    
    private String id;              // auto-generated, never changes
    private String firstName;
    private String lastName;
    private String email;

    public Person(String firstName, String lastName, String email) {

        this.id = String.valueOf(nextId);
        nextId = nextId + 1;

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

    }

    public String getPersonId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean isMatch(String id) {
        if (getPersonId().equals(id)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return getFullName();
    }
}
