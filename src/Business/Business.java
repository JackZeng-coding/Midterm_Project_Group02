/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Business.Person.PersonDirectory;
import Business.Profiles.EmployeeDirectory;
import Business.Profiles.StudentDirectory;
import Business.Profiles.FacultyDirectory;
import Business.UserAccounts.UserAccountDirectory;
import info5100.university.example.Department.Department;

/**
 *
 * @author kal bugrara
 * @author Jiahao Zeng(connected university model)。
 */
public class Business {

    // The semester that is currently running; earlier semesters are finished (read-only)
    public static final String CURRENT_SEMESTER = "Fall2026";
    String name;
    PersonDirectory persondirectory; //all people profiles regardless of the role
    private Department department;   // entry for university model
    EmployeeDirectory employeedirectory;
    UserAccountDirectory useraccountdirectory;
    StudentDirectory studentdirectory;
    FacultyDirectory facultydirectory;

    public Business(String n) {
        name = n;

        persondirectory = new PersonDirectory();
        employeedirectory = new EmployeeDirectory(this);
        useraccountdirectory = new UserAccountDirectory();
        department = new Department("Information Systems");  // ensure it will never be "null"
        studentdirectory = new StudentDirectory(department);
        facultydirectory = new FacultyDirectory(department);

    }

    public PersonDirectory getPersonDirectory() {
        return persondirectory;
    }

    public UserAccountDirectory getUserAccountDirectory() {
        return useraccountdirectory;
    }

    public EmployeeDirectory getEmployeeDirectory() {
        return employeedirectory;
    }

    public StudentDirectory getStudentDirectory() {
        return studentdirectory;
    }

    public FacultyDirectory getFacultyDirectory() {
        return facultydirectory;
    }

    public Department getDepartment() {
        return department;
    }

}
