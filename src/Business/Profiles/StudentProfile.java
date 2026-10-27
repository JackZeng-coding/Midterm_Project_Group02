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

    private info5100.university.example.Persona.StudentProfile universityRecord;
//    Transcript transcript;
    //   EmploymentHistroy employmenthistory;
    // person is stored in the parent class Profile via super(p); do not redeclare it here

    public StudentProfile(Person p, info5100.university.example.Persona.StudentProfile universityRecord) {
        super(p);
        this.universityRecord = universityRecord;

//        transcript = new Transcript(this);
//        employmenthistory = new EmploymentHistroy();
    }

    public info5100.university.example.Persona.StudentProfile getUniversityRecord() {
        return universityRecord;
    }

    @Override
    public String getRole() {
        return "Student";
    }

}
