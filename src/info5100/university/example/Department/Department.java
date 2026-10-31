/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example.Department;

import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.CourseCatalog.CourseCatalog;
import info5100.university.example.CourseSchedule.CourseLoad;
import info5100.university.example.CourseSchedule.CourseOffer;
import info5100.university.example.CourseSchedule.CourseSchedule;
import info5100.university.example.Degree.Degree;
import info5100.university.example.Employer.EmployerDirectory;
import info5100.university.example.Persona.Faculty.FacultyDirectory;
import info5100.university.example.Persona.PersonDirectory;
import info5100.university.example.Persona.StudentDirectory;
import info5100.university.example.Persona.StudentProfile;
import info5100.university.example.CourseSchedule.SeatAssignment;
import java.util.HashMap;

/**
 *
 * @author kal bugrara
 * @author Jiahao Zeng (fix bug)
 * @author Gavin Wen
 */
public class Department {

    String name;
    CourseCatalog coursecatalog;
    PersonDirectory persondirectory;
    StudentDirectory studentdirectory;
    FacultyDirectory facultydirectory;
    EmployerDirectory employerdirectory;
    Degree degree;

    HashMap<String, CourseSchedule> mastercoursecatalog;
    
    private String currentSemester = "Fall2026";  // suggested semester

    public Department(String n) {
        name = n;
        mastercoursecatalog = new HashMap<>();
        coursecatalog = new CourseCatalog(this);
        studentdirectory = new StudentDirectory(this); //pass the department object so it stays linked to it
        facultydirectory = new FacultyDirectory(this);
        persondirectory = new PersonDirectory();
        degree = new Degree("MSIS");

    }

    public void addCoreCourse(Course c) {
        degree.addCoreCourse(c);

    }

    public void addElectiveCourse(Course c) {
        degree.addElectiveCourse(c);

    }

    public PersonDirectory getPersonDirectory() {

        return persondirectory;

    }

    public StudentDirectory getStudentDirectory() {

        return studentdirectory;
    }

    public FacultyDirectory getFacultyDirectory() {
        
        return facultydirectory;
    }

    public CourseSchedule newCourseSchedule(String semester) {

        CourseSchedule cs = new CourseSchedule(semester, coursecatalog);
        mastercoursecatalog.put(semester, cs);
        return cs;
    }

    public CourseSchedule getCourseSchedule(String semester) {

        return mastercoursecatalog.get(semester);

    }

    public CourseCatalog getCourseCatalog() {

        return coursecatalog;

    }

    public Course newCourse(String n, String nm, int cr) {

        Course c = coursecatalog.newCourse(n, nm, cr);
        return c;
    }

    public int calculateRevenuesBySemester(String semester) {

        CourseSchedule css = mastercoursecatalog.get(semester);

        return css.calculateTotalRevenues();

    }

    public boolean RegisterForAClass(String studentid, String cn, String semester) {

        StudentProfile sp = studentdirectory.findStudent(studentid);
        if (sp == null) return false;
        CourseLoad cl = sp.getCourseLoadBySemester(semester);
        if (cl == null) cl = sp.newCourseLoad(semester);
        CourseSchedule cs = mastercoursecatalog.get(semester);
        if (cs == null) return false;
        CourseOffer co = cs.getCourseOfferByNumber(cn);
        if (co == null) return false;
        for (SeatAssignment sa: cl.getSeatAssignments()) {
            if (sa.getAssociatedCourse().getCOurseNumber().equals(cn)) return false;
        }

        return co.assignEmptySeat(cl) != null; 

    }

    public String getCurrentSemester() {
        return currentSemester;
    }
    public boolean DropAClass(String studentid, String cn, String semester) {
        StudentProfile sp = studentdirectory.findStudent(studentid);
        if(sp == null) return false;
        CourseLoad cl = sp.getCourseLoadBySemester(semester);
        if (cl == null) return false;
        CourseSchedule cs = mastercoursecatalog.get(semester);
        if (cs == null) return false;
        CourseOffer co = cs.getCourseOfferByNumber(cn);
        if (co == null) return false;
        return co.dropSeatFor(cl, cn);
    }
    
}
