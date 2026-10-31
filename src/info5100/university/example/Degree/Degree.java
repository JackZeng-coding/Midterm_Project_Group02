/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example.Degree;

import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.CourseSchedule.SeatAssignment;
import info5100.university.example.Persona.StudentProfile;
import java.util.ArrayList;

/**
 *
 * @author kal bugrara
 * @author Gavin Wen
 */
public class Degree {

    String title;
    ArrayList<Course> corelist;
    ArrayList<Course> electives;
    private int requiredCredits = 32; // NEU requirement, put here in case want to change

    public Degree(String name) {
        title = name;
        corelist = new ArrayList();
        electives = new ArrayList();
    }

    public void addCoreCourse(Course c) {
        corelist.add(c);

    }

    public void addElectiveCourse(Course c) {
        electives.add(c);

    }

    public boolean isStudentReadyToGraduate(StudentProfile sp) {

        //Extract transcript from studentprofile
        //Extract the list of courses taken so far from the student transcript
        //For each core course in the core list of the degree do the following:
        //Check if the core class at hand is in the transcrip
        //Repeat this check for the electives as well
        ArrayList<SeatAssignment> sas = sp.getCourseList(); //seatAssignments extracted from course loads

        if (!validateCoreClasses(sas)) {
            return false;
        }//core classes satisfied

        //Do the electives check in case some taken classes are not part of the electives
        int electivesTaken = getTotalElectiveCoursesTaken(sas);
        if (electivesTaken < 1) { // subject to change if want to add strict number of electives taken to pass
            return false;
        }
      
        //Check for the total number of credit hours that it is above 32
        int totalCredits = getTotalCreditsTaken(sas);
        if (totalCredits < requiredCredits) {
            return false;
        }

        else return true; //student has at least 32 credit hours per NEU requirements

    }

    public boolean validateCoreClasses(ArrayList<SeatAssignment> sas) {

        //For each core course in the core list of the degree do the following:
        //Check if the core class at hand is in the transcrip
        //Repeat this check for the electives as well
        for (Course c : corelist) {
            if (!isCoreSatisfied(sas, c)) { //if selected core class not on the trascript return false
                return false;
            }
        }
        return true; //all core classes are on the transcript satifying core reqs

    }

    public boolean isCoreSatisfied(ArrayList<SeatAssignment> sas, Course c) {
        for (SeatAssignment sa : sas) {
            if (sa.getAssociatedCourse().equals(c)) {
                return true;
            }
        }
        return false;

    }
    // Sum credit hours across all seat assignments for total credits taken
    public int getTotalCreditsTaken(ArrayList<SeatAssignment> sas) {
        int total = 0;
        for (SeatAssignment sa : sas) {
            total += sa.getAssociatedCourse().getCredits();
        }
        return total;
        
    }

    public int getTotalElectiveCoursesTaken(ArrayList<SeatAssignment> sas) {
        int electivecount = 0;
        for (SeatAssignment sa : sas) {
            if (isElectiveSatisfied(sa)) {
                electivecount = electivecount + 1;
            }
        }
        return electivecount;

    }

    public boolean isElectiveSatisfied(SeatAssignment sa) {
        for (Course c : electives) {
            if (sa.getAssociatedCourse().equals(c)) {
                return true;
            }
        }
        return false;

    }
    // Added read-only accessors to get data

    public String getTitle() {
        return title;
    }

    public ArrayList<Course> getCorelist() {
        return corelist;
    }

    public ArrayList<Course> getElectives() {
        return electives;
    }

    public int getRequiredCredits() {
        return requiredCredits;
    }
    

}
