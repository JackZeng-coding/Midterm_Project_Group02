/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 */
package Business;

import Business.Person.Person;
import Business.Person.PersonDirectory;
import Business.Profiles.EmployeeDirectory;
import Business.Profiles.EmployeeProfile;
import Business.Profiles.StudentDirectory;
import Business.Profiles.StudentProfile;
import Business.Profiles.FacultyDirectory;
import Business.Profiles.FacultyProfile;
import Business.UserAccounts.UserAccount;
import Business.UserAccounts.UserAccountDirectory;
import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.CourseSchedule.CourseAssignment;
import info5100.university.example.CourseSchedule.CourseLoad;
import info5100.university.example.CourseSchedule.CourseOffer;
import info5100.university.example.CourseSchedule.CourseSchedule;
import info5100.university.example.CourseSchedule.SeatAssignment;
import info5100.university.example.Department.Department;
import java.time.LocalDate;

/**
 *
 * @author kal bugrara
 * @author Jiahao Zeng
 */
class ConfigureABusiness {

    static Business initialize() {
        Business business = new Business("Information Systems");

// Create Persons
        PersonDirectory persondirectory = business.getPersonDirectory();
// person representing sales organization        
        Person person001 = persondirectory.newPerson("John", "Smith", "john.smith@university.edu");
        Person person002 = persondirectory.newPerson("Gina", "Montana", "gina.montana@university.edu");
        Person person003 = persondirectory.newPerson("Adam", "Rollen", "adam.rollen@university.edu");
        Person person005 = persondirectory.newPerson("Jim", "Dellon", "jim.dellon@university.edu");
        Person person006 = persondirectory.newPerson("Anna", "Shnider", "anna.shnider@university.edu");
        Person person007 = persondirectory.newPerson("Laura", "Brown", "laura.brown@university.edu");
        Person person008 = persondirectory.newPerson("Jack", "While", "jack.while@university.edu");

// Create Admins to manage the business
        EmployeeDirectory employeedirectory = business.getEmployeeDirectory();
        EmployeeProfile employeeprofile0 = employeedirectory.newEmployeeProfile(person001);

        StudentDirectory studentdirectory = business.getStudentDirectory();
        StudentProfile studentprofile0 = studentdirectory.newStudentProfile(person003);

// Create User accounts that link to specific profiles
        UserAccountDirectory uadirectory = business.getUserAccountDirectory();
        UserAccount ua3 = uadirectory.newUserAccount(employeeprofile0, "admin", "****");
        /// order products for one of the customers and performed by a sales person
        UserAccount ua4 = uadirectory.newUserAccount(studentprofile0, "adam", "****");
        /// order products for one of the customers and performed by a sales person

// ===== ADMIN SECTION (Jiahao Zeng): university setup and baseline records =====
        Department department = business.getDepartment();

        // 1. Course catalog: newCourse(name, number, credits) - the name comes first
        Course info5100 = department.newCourse("Application Engineering and Development", "INFO 5100", 4);
        Course damg6210 = department.newCourse("Data Management and Database Design", "DAMG 6210", 4);
        Course info6150 = department.newCourse("Web Design and User Experience", "INFO 6150", 4);
        Course info6205 = department.newCourse("Program Structure and Algorithms", "INFO 6205", 4);

        // 2. Degree requirements: core courses (used by the Student graduation audit)
        department.addCoreCourse(info5100);
        department.addCoreCourse(damg6210);
        department.addElectiveCourse(info6150);
        department.addElectiveCourse(info6205);

        // 3. Semester schedule: offer the course and create its seats
        CourseSchedule spring2026 = department.newCourseSchedule("Spring2026");
        CourseOffer info5100Spring = spring2026.newCourseOffer("INFO 5100"); // must match the number in step 1 exactly, otherwise will show null
        info5100Spring.generatSeats(10);                                     // "generat" is how the original model spells this method, if "generate", will build fail
        CourseOffer damg6210Spring = spring2026.newCourseOffer("DAMG 6210");
        damg6210Spring.generatSeats(10);

        CourseSchedule fall2026 = department.newCourseSchedule("Fall2026");
        CourseOffer damg6210Fall = fall2026.newCourseOffer("DAMG 6210");
        damg6210Fall.generatSeats(10);
        CourseOffer info6150Fall = fall2026.newCourseOffer("INFO 6150");
        info6150Fall.generatSeats(10);
        CourseOffer info6205Fall = fall2026.newCourseOffer("INFO 6205");
        info6205Fall.generatSeats(10);

        // 4. Register two more students. newStudentProfile() also creates the linked
        //    university record with the same ID (see StudentDirectory)
        StudentProfile studentprofile1 = studentdirectory.newStudentProfile(person005);   // Jim Dellon
        StudentProfile studentprofile2 = studentdirectory.newStudentProfile(person006);   // Anna Shnider

        info5100.university.example.Persona.StudentProfile adamRecord = studentprofile0.getUniversityRecord();
        info5100.university.example.Persona.StudentProfile jimRecord = studentprofile1.getUniversityRecord();
        info5100.university.example.Persona.StudentProfile annaRecord = studentprofile2.getUniversityRecord();

        // 5. Spring2026 (completed): one course load per student -> take seats -> record grades
        CourseLoad adamSpring = adamRecord.newCourseLoad("Spring2026");
        SeatAssignment adamSa1 = adamSpring.newSeatAssignment(info5100Spring);
        adamSa1.setGrade(4.0f);                                              // A  = 4.0
        SeatAssignment adamSa2 = adamSpring.newSeatAssignment(damg6210Spring);
        adamSa2.setGrade(3.3f);                                              // B+ = 3.3

        CourseLoad jimSpring = jimRecord.newCourseLoad("Spring2026");
        SeatAssignment jimSa1 = jimSpring.newSeatAssignment(info5100Spring);
        jimSa1.setGrade(3.7f);                                               // A- = 3.7

        CourseLoad annaSpring = annaRecord.newCourseLoad("Spring2026");
        SeatAssignment annaSa1 = annaSpring.newSeatAssignment(info5100Spring);
        annaSa1.setGrade(3.0f);                                              // B  = 3.0
        SeatAssignment annaSa2 = annaSpring.newSeatAssignment(damg6210Spring);
        annaSa2.setGrade(4.0f);                                              // A  = 4.0

        // 6. Fall2026 (current semester, no grades yet). Created last, so it becomes
        //    each student's current course load
        CourseLoad adamFall = adamRecord.newCourseLoad("Fall2026");
        adamFall.newSeatAssignment(info6150Fall);

        CourseLoad jimFall = jimRecord.newCourseLoad("Fall2026");
        jimFall.newSeatAssignment(damg6210Fall);

        CourseLoad annaFall = annaRecord.newCourseLoad("Fall2026");
        annaFall.newSeatAssignment(info6205Fall);
// ===== END ADMIN SECTION =====

// ===== STUDENT SECTION (Gavin Wen): please add student demo data below =====
//    -- Gavin, You can work here
        // Coursework. Fall due dates are relative to today so the demo always has
        // overdue and upcoming work, whichever day it is run
        LocalDate today = LocalDate.now();

        CourseAssignment layoutPage = info6150Fall.newAssignment("Responsive Layout Page",
                "Build a single responsive page.", today.minusDays(14), 20);
        info6150Fall.newAssignment("Accessibility",
                "Test your page accessibility.", today.minusDays(2), 20);
        info6150Fall.newAssignment("Portfolio Project",
                "Publish a multi-page portfolio project and share the link.", today.plusDays(10), 40);

        CourseAssignment erDiagram = damg6210Fall.newAssignment("ER Diagram",
                "Model the university registration domain as an ER diagram.", today.minusDays(10), 20);
        damg6210Fall.newAssignment("Worksheet",
                "Complete worksheet.", today.plusDays(5), 30);

        info6205Fall.newAssignment("Sorting Benchmark",
                "Compare merge sort and quicksort on arrays of up to 1M items.", today.plusDays(3), 25);
        info6205Fall.newAssignment("Graph Report",
                "Implement algorithm and analyse its running time.", today.plusDays(12), 25);

        CourseAssignment classDesign = info5100Spring.newAssignment("Class Design Exercise",
                "Design the object model for a small business application.", LocalDate.of(2026, 2, 15), 50);
        CourseAssignment swingApp = info5100Spring.newAssignment("Swing Application",
                "Build a desktop application with Java Swing.", LocalDate.of(2026, 4, 12), 50);
        CourseAssignment sqlLab = damg6210Spring.newAssignment("SQL Queries Lab",
                "Write the join and aggregate queries listed in the handout.", LocalDate.of(2026, 3, 8), 40);

        // Spring2026 is finished: every submission is on record and graded
        seedGradedSubmission(adamSa1, classDesign, LocalDate.of(2026, 2, 14), 48f);
        seedGradedSubmission(adamSa1, swingApp, LocalDate.of(2026, 4, 11), 47f);
        seedGradedSubmission(adamSa2, sqlLab, LocalDate.of(2026, 3, 8), 33f);
        seedGradedSubmission(jimSa1, classDesign, LocalDate.of(2026, 2, 15), 45f);
        seedGradedSubmission(jimSa1, swingApp, LocalDate.of(2026, 4, 12), 44f);
        seedGradedSubmission(annaSa1, classDesign, LocalDate.of(2026, 2, 13), 40f);
        seedGradedSubmission(annaSa1, swingApp, LocalDate.of(2026, 4, 12), 41f);
        seedGradedSubmission(annaSa2, sqlLab, LocalDate.of(2026, 3, 7), 39f);

        // Fall2026 in progress: Adam has one graded and one overdue, Jim handed in late, Anna has not started
        seedGradedSubmission(adamFall.getSeatAssignments().get(0), layoutPage, today.minusDays(15), 18f);
        jimFall.getSeatAssignments().get(0).recordSubmission(erDiagram,
                "ER diagram of students, courses and enrollments.", today.minusDays(9));
// ===== END STUDENT SECTION =====

// ===== FACULTY SECTION (Jiahao Zeng): faculty profiles and their login accounts =====     
        // newFacultyProfile() also creates the linked university record with the same ID (see FacultyDirectory)
        FacultyDirectory facultydirectory = business.getFacultyDirectory();
        FacultyProfile facultyprofile0 = facultydirectory.newFacultyProfile(person002);   // Gina Montana (1001)
        FacultyProfile facultyprofile1 = facultydirectory.newFacultyProfile(person007);   // Laura Brown (1005)

        UserAccount ua5 = uadirectory.newUserAccount(facultyprofile0, "gina", "****");
        UserAccount ua6 = uadirectory.newUserAccount(facultyprofile1, "laura", "****");

        // Teaching assignments: always go through CourseOffer.AssignAsTeacher() so both sides stay in sync
        info5100Spring.AssignAsTeacher(facultyprofile0.getUniversityRecord());   // Gina, graded class
        info6150Fall.AssignAsTeacher(facultyprofile0.getUniversityRecord());     // Gina, in progress
        info6205Fall.AssignAsTeacher(facultyprofile0.getUniversityRecord());     // Gina, in progress
        damg6210Spring.AssignAsTeacher(facultyprofile1.getUniversityRecord());   // Laura, graded class
        damg6210Fall.AssignAsTeacher(facultyprofile1.getUniversityRecord());     // Laura, in progress

// ===== END FACULTY SECTION =====
        return business;

    }

    private static void seedGradedSubmission(SeatAssignment sa, CourseAssignment a, LocalDate submittedOn, float score) {
        sa.recordSubmission(a, "Submission for " + a.getTitle() + ".", submittedOn).setScore(score);
    }

}
