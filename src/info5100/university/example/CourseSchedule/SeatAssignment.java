/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example.CourseSchedule;

import info5100.university.example.CourseCatalog.Course;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author kal bugrara
 * @author Jiahao Zeng (added grade getter/setter)
 * @author Gavin Wen
 */
public class SeatAssignment {

    float grade; //(Letter grade mappings: A=4.0, A-=3.7, B+=3.3, B=3.0, )
    Seat seat;
    boolean like; //true means like and false means not like
    CourseLoad courseload;
    ArrayList<AssignmentSubmission> submissions; //Added arraylist of submissions to seatassignment

    public SeatAssignment(CourseLoad cl, Seat s) {
        seat = s;
        courseload = cl;
        submissions = new ArrayList();
    }

    public boolean getLike() {
        return like;
    }

    public void assignSeatToStudent(CourseLoad cl) {
        courseload = cl;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        if (grade < 0.0f || grade > 4.0f) { // GPA should range from 0.0 ~ 4.0
            return;                         // Return result to user interface
        }
            
        this.grade = grade;
    }

    public int getCreditHours() {
        return seat.getCourseCredits();

    }

    public Seat getSeat() {
        return seat;
    }

    public CourseOffer getCourseOffer() {

        return seat.getCourseOffer();
    }

    public Course getAssociatedCourse() {

        return getCourseOffer().getSubjectCourse();
    }

    public float GetCourseStudentScore() {
        return getCreditHours() * grade;
    }

    // No final grade yet, same rule the transcript uses to show "IP"
    public boolean isInProgress() {
        return grade <= 0f;
    }

    public AssignmentSubmission getSubmissionFor(CourseAssignment a) {
        for (AssignmentSubmission s : submissions) {
            if (s.getAssignment() == a) {
                return s;
            }
        }
        return null;
    }

    // Open while the course is in progress: late work is allowed, graded work is locked
    public boolean canSubmit(CourseAssignment a) {
        if (a == null || !isInProgress() || !getCourseOffer().getAssignments().contains(a)) {
            return false;
        }
        AssignmentSubmission s = getSubmissionFor(a);
        return s == null || !s.isGraded();
    }

    public AssignmentSubmission submitAssignment(CourseAssignment a, String content) {
        if (!canSubmit(a) || content == null || content.trim().isEmpty()) {
            return null;
        }
        return recordSubmission(a, content.trim(), LocalDate.now());
    }

    // Stores the work without the open/locked checks, used directly for historic records
    public AssignmentSubmission recordSubmission(CourseAssignment a, String content, LocalDate submittedOn) {
        if (!getCourseOffer().getAssignments().contains(a)) {
            return null;   // the assignment belongs to another course
        }
        AssignmentSubmission s = getSubmissionFor(a);
        if (s == null) {
            s = new AssignmentSubmission(a, content, submittedOn);
            submissions.add(s);
        } else {
            s.resubmit(content, submittedOn);
        }
        return s;
    }

    public String getAssignmentStatus(CourseAssignment a) {
        AssignmentSubmission s = getSubmissionFor(a);
        if (s == null) {
            return (isInProgress() && a.isPastDue()) ? "Overdue" : "Not submitted";
        }
        if (s.isGraded()) {
            return "Graded";
        }
        return s.isLate() ? "Submitted late" : "Submitted";
    }

    public int getSubmittedCount() {
        return submissions.size();
    }

    public float getPointsEarned() {
        float sum = 0;
        for (AssignmentSubmission s : submissions) {
            if (s.isGraded()) {
                sum = sum + s.getScore();
            }
        }
        return sum;
    }

    // Points available on the graded work only, so ungraded work does not pull the score down
    public int getPointsGraded() {
        int sum = 0;
        for (AssignmentSubmission s : submissions) {
            if (s.isGraded()) {
                sum = sum + s.getAssignment().getMaxPoints();
            }
        }
        return sum;
    }

}
