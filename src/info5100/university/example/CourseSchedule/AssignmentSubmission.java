/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example.CourseSchedule;

import java.time.LocalDate;

/**
 * @author Gavin Wen
 */
public class AssignmentSubmission {

    CourseAssignment assignment;
    String content;
    LocalDate submittedOn;
    Float score;   // null until graded

    public AssignmentSubmission(CourseAssignment assignment, String content, LocalDate submittedOn) {
        this.assignment = assignment;
        this.content = content;
        this.submittedOn = submittedOn;
    }

    public void resubmit(String content, LocalDate submittedOn) {
        this.content = content;
        this.submittedOn = submittedOn;
    }

    public CourseAssignment getAssignment() {
        return assignment;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getSubmittedOn() {
        return submittedOn;
    }

    public boolean isLate() {
        return submittedOn.isAfter(assignment.getDueDate());
    }

    public boolean isGraded() {
        return score != null;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(float score) {
        if (score < 0f || score > assignment.getMaxPoints()) { // same rule as SeatAssignment.setGrade
            return;
        }

        this.score = score;
    }

}
