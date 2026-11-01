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
public class CourseAssignment {

    CourseOffer courseoffer;
    String title;
    String description;
    LocalDate dueDate;
    int maxPoints;

    public CourseAssignment(CourseOffer co, String title, String description, LocalDate dueDate, int maxPoints) {
        courseoffer = co;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.maxPoints = maxPoints;
    }

    public CourseOffer getCourseOffer() {
        return courseoffer;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public boolean isPastDue() {
        return LocalDate.now().isAfter(dueDate);
    }

}
