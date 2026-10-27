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

    public StudentDirectory() {

        studentlist = new ArrayList();

    }

    public StudentProfile newStudentProfile(Person p) {

        // 1. university model side：use the same ID from to create Person
        info5100.university.example.Persona.Person uniPerson =
                department.getPersonDirectory().newPerson(p.getPersonId());

        // 2. university model side：Create a student profile
        info5100.university.example.Persona.StudentProfile uniRecord =
                department.getStudentDirectory().newStudentProfile(uniPerson);

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

}
