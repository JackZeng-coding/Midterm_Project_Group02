/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Profiles;

import Business.Person.Person;

/**
 * Access-control side profile for a faculty member.
 * Links the login layer (name, email) to the university model's
 * faculty record (teaching assignments). Both sides share the same person ID.
 *
 * @author Jiahao Zeng
 */
public class FacultyProfile extends Profile {

    // university-side record; full name because both classes are called FacultyProfile
    private info5100.university.example.Persona.Faculty.FacultyProfile universityRecord;

    public FacultyProfile(Person p, info5100.university.example.Persona.Faculty.FacultyProfile universityRecord) {
        super(p);   // person is stored in the parent class Profile
        this.universityRecord = universityRecord;
    }

    public info5100.university.example.Persona.Faculty.FacultyProfile getUniversityRecord() {
        return universityRecord;
    }

    @Override
    public String getRole() {
        return "Faculty";
    }
}
