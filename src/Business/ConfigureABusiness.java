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

import Business.UserAccounts.UserAccount;
import Business.UserAccounts.UserAccountDirectory;
import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.CourseSchedule.CourseLoad;
import info5100.university.example.CourseSchedule.CourseOffer;
import info5100.university.example.CourseSchedule.CourseSchedule;
import info5100.university.example.CourseSchedule.SeatAssignment;
import info5100.university.example.Department.Department;

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

        // 1. Course Catalog: course name first, then course 
        Course info5100 = department.newCourse("Application Engineering and Development", "INFO 5100", 4);

        // 2. Degree requirements: core courses (used by the Student graduation audit)
        department.addCoreCourse(info5100);

        // 3. Semester schedule: offer the course and create its seats
        CourseSchedule spring2026 = department.newCourseSchedule("Spring2026");
        CourseOffer info5100Spring = spring2026.newCourseOffer("INFO 5100"); // must match the number in step 1 exactly, otherwise will show null
        info5100Spring.generatSeats(10);                                     // "generat" is how the original model spells this method, if "generate", will build fail

        // 4. Student's semester load -> take a seat -> record the grade
        info5100.university.example.Persona.StudentProfile adamRecord = studentprofile0.getUniversityRecord();
        CourseLoad adamSpring = adamRecord.newCourseLoad("Spring2026");
        SeatAssignment adamSa1 = adamSpring.newSeatAssignment(info5100Spring);
        adamSa1.setGrade(4.0f);                                             // A = 4.0
// ===== END ADMIN SECTION =====

// ===== STUDENT SECTION (Gavin Wen): please add student demo data below =====
//    -- Gavin, You can work here
// ===== END STUDENT SECTION =====

// ===== FACULTY SECTION (Jiahao Zeng): teachers will be added in the Faculty PR =====      
// ===== END STUDENT SECTION =====
        
        return business;

    }

}
