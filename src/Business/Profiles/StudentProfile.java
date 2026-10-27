/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Profiles;

import Business.Person.Person;

/**
 *
 * @author kal bugrara
 * @author Jiahao Zeng (fixed hidden person field)
 */
public class StudentProfile extends Profile {

    
//    Transcript transcript;
    //   EmploymentHistroy employmenthistory;
    // person is stored in the parent class Profile via super(p); do not redeclare it here

    public StudentProfile(Person p) {
        super(p);

//        transcript = new Transcript(this);
//        employmenthistory = new EmploymentHistroy();
    }

    @Override
    public String getRole() {
        return "Student";
    }


}
