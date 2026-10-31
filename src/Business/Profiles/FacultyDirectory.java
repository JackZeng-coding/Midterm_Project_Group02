/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Profiles;

import Business.Person.Person;
import info5100.university.example.Department.Department;
import java.util.ArrayList;

/**
 * Access-control side directory of faculty members. newFacultyProfile() creates
 * both sides in one call, so the IDs always match.
 *
 * @author Jiahao Zeng
 */
public class FacultyDirectory {

    private Department department;
    private ArrayList<FacultyProfile> facultylist;

    public FacultyDirectory(Department d) {
        department = d;
        facultylist = new ArrayList<>();   // <> lets the compiler infer the type: no "unchecked" note
    }

    public FacultyProfile newFacultyProfile(Person p) {
        // 1. university side: create a Person with the SAME id
        info5100.university.example.Persona.Person uniPerson
                = department.getPersonDirectory().newPerson(p.getPersonId());
        // 2. university side: create the faculty record
        info5100.university.example.Persona.Faculty.FacultyProfile uniRecord
                = department.getFacultyDirectory().newFacultyProfile(uniPerson);
        // 3. access-control side: new FacultyProfile(p, uniRecord), add it to the list, return it
        FacultyProfile fp = new FacultyProfile(p, uniRecord);
        facultylist.add(fp);
        return fp;

    }

    public FacultyProfile findFaculty(String id) {

        for (FacultyProfile fp : facultylist) {

            if (fp.isMatch(id)) {
                return fp;
            }
        }
        return null; //not found after going through the whole list
    }

    public ArrayList<FacultyProfile> getFacultyList() {
        return facultylist;
    }
    
    // added by Jiahao Zeng: delete a faculty member on BOTH sides (newFacultyProfile in reverse).
    // The caller must check first that the faculty member has no teaching assignments.
    public void removeFaculty(FacultyProfile fp) {
        if (fp == null) {
            return;
        }
        String id = fp.getPerson().getPersonId();
        department.getFacultyDirectory().removeFaculty(fp.getUniversityRecord());
        info5100.university.example.Persona.Person uniPerson
                = department.getPersonDirectory().findPerson(id);
        if (uniPerson != null) {
            department.getPersonDirectory().removePerson(uniPerson);
        }
        facultylist.remove(fp);
    }
    
}
