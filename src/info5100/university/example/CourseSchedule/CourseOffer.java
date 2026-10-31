/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example.CourseSchedule;

import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.Persona.Faculty.FacultyAssignment;
import info5100.university.example.Persona.Faculty.FacultyProfile;
import java.util.ArrayList;

/**
 *
 * @author kal bugrara
 * @author Gavin Wen
 * @author Jiahao Zeng
 */
public class CourseOffer {

    Course course;
    String semester;   // fixed when the offer is created (getter only, like a person ID)
    ArrayList<Seat> seatlist;
    FacultyAssignment facultyassignment;
    String meetingTime = "TBA";   // course details the teacher can update
    String description = "";

    public CourseOffer(Course c, String semester) {
        course = c;
        this.semester = semester;
        seatlist = new ArrayList();
    }
     
    public void AssignAsTeacher(FacultyProfile fp) {

        if (fp == null) {
        return;   // nothing to assign
    }
        facultyassignment = fp.AssignAsTeacher(this);
    }

     public String getSemester() {
        return semester;
    }

    public int getCapacity() {
        return seatlist.size();   // total number of seats
    }

    public int getEnrolledCount() {
        int count = 0;
        for (Seat s : seatlist) {
            if (s.isOccupied()) {   // is someone sitting in this seat?
                count++;            // yes → add 1 to the counter
            }
        }
        return count;
    }
    
    public String getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public FacultyProfile getFacultyProfile() {
        if (facultyassignment == null) return null; // Prevents crashing if no faculty assigned
        return facultyassignment.getFacultyProfile();
    }

    public String getCourseNumber() {
        return course.getCOurseNumber();
    }

    public void generatSeats(int n) {

        for (int i = 0; i < n; i++) {

            seatlist.add(new Seat(this, i));

        }

    }

    public Seat getEmptySeat() {

        for (Seat s : seatlist) {

            if (!s.isOccupied()) {
                return s;
            }
        }
        return null;
    }


    public SeatAssignment assignEmptySeat(CourseLoad cl) {

        Seat seat = getEmptySeat();
        if (seat == null) {
            return null;
        }
        SeatAssignment sa = seat.newSeatAssignment(cl); //seat is already linked to course offer
        cl.registerStudent(sa); //coures offer seat is now linked to student
        return sa;
    }

    public int getTotalCourseRevenues() {

        int sum = 0;

        for (Seat s : seatlist) {
            if (s.isOccupied() == true) {
                sum = sum + course.getCoursePrice();
            }

        }
        return sum;
    }
    public Course getSubjectCourse(){
        return course;
    }
    public int getCreditHours(){
        return course.getCredits();
    }
    // Method with for loop to look through seatlist for non occupied seats to get a int value
    public int getAvailableSeatCount() {
        int n = 0;
        for (Seat s : seatlist) if (!s.isOccupied()) n++;
        return n;
    }
    public boolean dropSeatFor(CourseLoad cl, String courseNumber) {
        for (SeatAssignment sa : new ArrayList<>(cl.getSeatAssignments())) {
            if (sa.getAssociatedCourse().getCOurseNumber().equals(courseNumber)) {
                return cl.removeSeatAssignment(sa);
            }
        }
        return false;
    }

}
