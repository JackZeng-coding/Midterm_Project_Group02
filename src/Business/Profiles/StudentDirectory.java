/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Profiles;

import Business.Person.Person;

import java.util.ArrayList;
import info5100.university.example.Department.Department;

/**
 *
 * @author kal bugrara
 * @author Jiahao Zeng
 */
public class StudentDirectory {

    private Department department;

    public StudentDirectory(Department d) {
        department = d;
        studentlist = new ArrayList();
    }

    ArrayList<StudentProfile> studentlist;

    public StudentProfile newStudentProfile(Person p) {

        // 1. university model side：use the same ID from to create Person
        info5100.university.example.Persona.Person uniPerson
                = department.getPersonDirectory().newPerson(p.getPersonId());

        // 2. university model side：Create a student profile
        info5100.university.example.Persona.StudentProfile uniRecord
                = department.getStudentDirectory().newStudentProfile(uniPerson);

        // 3. access control package side：Create Person profile, use student profile
        StudentProfile sp = new StudentProfile(p, uniRecord);
        studentlist.add(sp);
        return sp;
    }

    public StudentProfile findStudent(String id) {

        for (StudentProfile sp : studentlist) {

            if (sp.isMatch(id)) {
                return sp;
            }
        }
        return null; //not found after going through the whole list
    }

    // added by Jiahao Zeng: Admin "Manage Students" needs the full list
    public ArrayList<StudentProfile> getStudentList() {
        return studentlist;
    }

    // added by Jiahao Zeng: delete a student on BOTH sides (newStudentProfile in reverse).
    // The caller must check first that the student has no course records.
    public void removeStudent(StudentProfile sp) {
        if (sp == null) {
            return;                                    // nothing to delete
        }
        // 1. the shared key: this student's ID
        String id = sp.getPerson().getPersonId();
        // 2. university side: remove the student record
        department.getStudentDirectory().removeStudent(sp.getUniversityRecord());
        // 3. university side: find the Person with the same ID, then remove it
        info5100.university.example.Persona.Person uniPerson
                = department.getPersonDirectory().findPerson(id);
        if (uniPerson != null) {
            department.getPersonDirectory().removePerson(uniPerson);
        }
        // 4. access-control side
        studentlist.remove(sp);
    }
    
}
