/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example.Persona.Faculty;

import info5100.university.example.Persona.*;
import info5100.university.example.CourseSchedule.CourseOffer;
import java.util.ArrayList;

/**
 *
 * @author kal bugrara
 * @authro Gavin Wen
 * @author Jiahao Zeng
 */
public class FacultyProfile {

    Person person;
    ArrayList<FacultyAssignment> facultyassignments;

    public FacultyProfile(Person p) {

        person = p;
        facultyassignments = new ArrayList();
    }

    // Teacher → courses (the direction the Faculty "Manage Courses" screen needs)
    public ArrayList<CourseOffer> getCourseOffers() {
        ArrayList<CourseOffer> offers = new ArrayList<>();
        for (FacultyAssignment fa : facultyassignments) {
            offers.add(fa.getCourseOffer());   // put this assignment's course offer into the result
        }
        return offers;
    }

    public double getProfAverageOverallRating() {

        double sum = 0.0;
        //for each facultyassignment extract class rating
        //add them up and divide by the number of teaching assignmnet;
        for (FacultyAssignment fa : facultyassignments) {

            sum = sum + fa.getRating();

        }
        //divide by the total number of faculty assignments

        return sum / (facultyassignments.size() * 1.0); //this ensure we have double/double

    }

    public FacultyAssignment AssignAsTeacher(CourseOffer co) {

        FacultyAssignment fa = new FacultyAssignment(this, co);
        facultyassignments.add(fa);

        return fa;
    }

    public FacultyProfile getCourseOffer(String courseid) {
        return null; //complete it later
    }

    public boolean isMatch(String id) {
        if (person.getPersonId().equals(id)) {
            return true;
        }
        return false;
    }

    public Person getPerson() {
        return person;
    }

}
