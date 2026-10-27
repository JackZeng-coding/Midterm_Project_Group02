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

        return business;

    }

}
