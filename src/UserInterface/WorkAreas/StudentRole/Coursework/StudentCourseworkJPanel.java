/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.WorkAreas.StudentRole.Coursework;

import Business.Business;
import Business.Person.Person;
import Business.Profiles.StudentProfile;
import info5100.university.example.CourseSchedule.AssignmentSubmission;
import info5100.university.example.CourseSchedule.CourseAssignment;
import info5100.university.example.CourseSchedule.CourseLoad;
import info5100.university.example.CourseSchedule.SeatAssignment;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 * @author Gavin Wen
 */
public class StudentCourseworkJPanel extends javax.swing.JPanel {

    Business business;
    StudentProfile student;
    JPanel CardSequencePanel;

    // Parallel to the table rows: so we can get the object behind each row
    private final ArrayList<SeatAssignment> rowCourses = new ArrayList<>();
    private final ArrayList<CourseAssignment> rowAssignments = new ArrayList<>();

    /**
     * Creates new form StudentCourseworkJPanel
     */
    public StudentCourseworkJPanel(Business business, StudentProfile student, JPanel CardSequencePanel) {
        this.business = business;
        this.student = student;
        this.CardSequencePanel = CardSequencePanel;
        initComponents();

        // Course names are long, so size the columns instead of splitting the width evenly
        int[] courseColumnWidths = {80, 80, 240, 75, 70, 75};
        for (int i = 0; i < courseColumnWidths.length; i++) {
            tblCourses.getColumnModel().getColumn(i).setPreferredWidth(courseColumnWidths[i]);
        }
        tblCourses.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblAssignments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblCourses.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadAssignments();
            }
        });
        tblAssignments.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showAssignment();
            }
        });

        Person p = student.getPerson();
        lblStudent.setText("Student: " + (p != null ? p.getFullName() : "-"));
        loadCourses();
        if (!rowCourses.isEmpty()) {
            tblCourses.setRowSelectionInterval(0, 0);
        }
    }

    private void loadCourses() {
        rowCourses.clear();
        DefaultTableModel model = (DefaultTableModel) tblCourses.getModel();
        model.setRowCount(0);

        info5100.university.example.Persona.StudentProfile record = student.getUniversityRecord();
        if (record == null) {
            lblStudent.setText(lblStudent.getText() + " (no university record found)");
            return;
        }
        // Current semester first, it is the one with work still to hand in
        CourseLoad current = record.getCurrentCourseLoad();
        if (current != null) {
            rowCourses.addAll(current.getSeatAssignments());
        }
        for (SeatAssignment sa : record.getCourseList()) {
            if (!rowCourses.contains(sa)) {
                rowCourses.add(sa);
            }
        }
        for (SeatAssignment sa : rowCourses) {
            Object[] row = new Object[6];
            row[0] = record.getSemesterFor(sa);
            row[1] = sa.getAssociatedCourse().getCOurseNumber();
            row[2] = sa.getAssociatedCourse().getName();
            row[3] = sa.isInProgress() ? "In progress" : "Completed";
            row[4] = sa.getSubmittedCount() + " / " + sa.getCourseOffer().getAssignments().size();
            row[5] = sa.getPointsGraded() > 0
                    ? String.format("%.1f / %d", sa.getPointsEarned(), sa.getPointsGraded()) : "-";
            model.addRow(row);
        }
        loadAssignments();
    }

    private void loadAssignments() {
        rowAssignments.clear();
        DefaultTableModel model = (DefaultTableModel) tblAssignments.getModel();
        model.setRowCount(0);

        SeatAssignment course = getSelectedCourse();
        if (course != null) {
            rowAssignments.addAll(course.getCourseOffer().getAssignments());
            for (CourseAssignment a : rowAssignments) {
                AssignmentSubmission submission = course.getSubmissionFor(a);
                Object[] row = new Object[4];
                row[0] = a.getTitle();
                row[1] = a.getDueDate();
                row[2] = course.getAssignmentStatus(a);
                row[3] = (submission != null && submission.isGraded())
                        ? String.format("%.1f / %d", submission.getScore(), a.getMaxPoints())
                        : "- / " + a.getMaxPoints();
                model.addRow(row);
            }
        }
        showAssignment();
    }

    private void showAssignment() {
        SeatAssignment course = getSelectedCourse();
        CourseAssignment assignment = getSelectedAssignment();
        if (course == null || assignment == null) {
            lblAssignment.setText("Select an assignment");
            txtDescription.setText("");
            txtSubmission.setText("");
            txtSubmission.setEditable(false);
            lblSubmissionStatus.setText("");
            btnSubmit.setText("Submit");
            btnSubmit.setEnabled(false);
            return;
        }
        AssignmentSubmission submission = course.getSubmissionFor(assignment);
        boolean canSubmit = course.canSubmit(assignment);

        lblAssignment.setText(assignment.getTitle() + " (due " + assignment.getDueDate() + ")");
        txtDescription.setText(assignment.getDescription());
        txtSubmission.setText(submission != null ? submission.getContent() : "");
        txtSubmission.setEditable(canSubmit);
        btnSubmit.setText(submission != null ? "Resubmit" : "Submit");
        btnSubmit.setEnabled(canSubmit);
        lblSubmissionStatus.setText(describeSubmission(course, assignment, submission));
    }

    private String describeSubmission(SeatAssignment course, CourseAssignment assignment, AssignmentSubmission submission) {
        String status = (submission != null)
                ? "Submitted on " + submission.getSubmittedOn()
                : course.getAssignmentStatus(assignment);
        if (!course.isInProgress()) {
            return status + ". Course completed, now locked.";
        }
        if (submission != null && submission.isGraded()) {
            return status + ". Graded, now locked.";
        }
        return status + ".";
    }

    private SeatAssignment getSelectedCourse() {
        int row = tblCourses.getSelectedRow();
        return (row < 0 || row >= rowCourses.size()) ? null : rowCourses.get(row);
    }

    private CourseAssignment getSelectedAssignment() {
        int row = tblAssignments.getSelectedRow();
        return (row < 0 || row >= rowAssignments.size()) ? null : rowAssignments.get(row);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lblStudent = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCourses = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAssignments = new javax.swing.JTable();
        lblAssignment = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtSubmission = new javax.swing.JTextArea();
        lblSubmissionStatus = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        btnSubmit = new javax.swing.JButton();

        setLayout(null);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel1.setText("Manage Coursework");
        add(jLabel1);
        jLabel1.setBounds(30, 20, 300, 30);

        lblStudent.setText("Student:");
        add(lblStudent);
        lblStudent.setBounds(30, 50, 500, 20);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("MY COURSES");
        add(jLabel2);
        jLabel2.setBounds(30, 80, 200, 16);

        tblCourses.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Semester", "Number", "Name", "Status", "Submitted", "Score"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblCourses);

        add(jScrollPane1);
        jScrollPane1.setBounds(30, 100, 620, 130);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("ASSIGNMENTS");
        add(jLabel3);
        jLabel3.setBounds(30, 240, 200, 16);

        tblAssignments.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title", "Due", "Status", "Score"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblAssignments);

        add(jScrollPane2);
        jScrollPane2.setBounds(30, 260, 620, 130);

        lblAssignment.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblAssignment.setText("Select an assignment");
        add(lblAssignment);
        lblAssignment.setBounds(680, 110, 400, 16);

        txtDescription.setEditable(false);
        txtDescription.setColumns(20);
        txtDescription.setLineWrap(true);
        txtDescription.setRows(3);
        txtDescription.setWrapStyleWord(true);
        jScrollPane3.setViewportView(txtDescription);

        add(jScrollPane3);
        jScrollPane3.setBounds(680, 130, 400, 90);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("MY SUBMISSION");
        add(jLabel4);
        jLabel4.setBounds(680, 230, 200, 16);

        txtSubmission.setColumns(20);
        txtSubmission.setLineWrap(true);
        txtSubmission.setRows(5);
        txtSubmission.setWrapStyleWord(true);
        jScrollPane4.setViewportView(txtSubmission);

        add(jScrollPane4);
        jScrollPane4.setBounds(680, 260, 400, 140);

        lblSubmissionStatus.setText(" ");
        add(lblSubmissionStatus);
        lblSubmissionStatus.setBounds(680, 370, 400, 20);

        btnBack.setText("<< Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        add(btnBack);
        btnBack.setBounds(30, 410, 90, 23);

        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });
        add(btnSubmit);
        btnSubmit.setBounds(980, 410, 100, 23);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        SeatAssignment course = getSelectedCourse();
        CourseAssignment assignment = getSelectedAssignment();
        if (course == null || assignment == null) {
            JOptionPane.showMessageDialog(this, "Please select an assignment first.",
                    "No assignment selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txtSubmission.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your work before submitting.",
                    "Empty submission", JOptionPane.WARNING_MESSAGE);
            return;
        }
        AssignmentSubmission submission = course.submitAssignment(assignment, txtSubmission.getText());
        if (submission == null) {
            JOptionPane.showMessageDialog(this, "This assignment is closed for submissions.",
                    "Submission failed", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Reloading clears the selection, so put the student back where they were
        int courseRow = tblCourses.getSelectedRow();
        int assignmentRow = tblAssignments.getSelectedRow();
        loadCourses();
        tblCourses.setRowSelectionInterval(courseRow, courseRow);
        tblAssignments.setRowSelectionInterval(assignmentRow, assignmentRow);

        String message = submission.isLate()
                ? "Submitted after the due date, so it is marked as late."
                : "Your work has been submitted.";
        JOptionPane.showMessageDialog(this, message, "Submitted", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnSubmitActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblAssignment;
    private javax.swing.JLabel lblStudent;
    private javax.swing.JLabel lblSubmissionStatus;
    private javax.swing.JTable tblAssignments;
    private javax.swing.JTable tblCourses;
    private javax.swing.JTextArea txtDescription;
    private javax.swing.JTextArea txtSubmission;
    // End of variables declaration//GEN-END:variables
}
