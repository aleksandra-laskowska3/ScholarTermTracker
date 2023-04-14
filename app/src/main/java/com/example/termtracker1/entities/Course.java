package com.example.termtracker1.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.time.LocalDate;
import java.util.Date;

@Entity(tableName = "courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private int termID;
    private String cTitle;
    private Date cStart;
    private Date cEnd;
    private String status;
    private String instructorName;
    private String instructorNumber;
    private String instructorEmail;
    private Date cAlertStart;
    private Date cAlertEnd;
    private String note;

    public Course(int termID, int courseID, String cTitle, Date cStart,
                  Date cEnd, String status, String note, String instructorName,
                  String instructorNumber, String instructorEmail, Date cAlertStart,
                  Date cAlertEnd) {
        this.termID = termID;
        this.courseID = courseID;
        this.cTitle = cTitle;
        this.cStart = cStart;
        this.cEnd = cEnd;
        this.status = status;
        this.note = note;
        this.instructorName = instructorName;
        this.instructorNumber = instructorNumber;
        this.instructorEmail = instructorEmail;
        this.cAlertStart = cAlertStart;
        this.cAlertEnd = cAlertEnd;
    }

    public Course() {
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCTitle() {
        return cTitle;
    }

    public void setCTitle(String cTitle) {
        this.cTitle = cTitle;
    }

    public Date getCStart() {
        return cStart;
    }

    public void setCStart(Date cStart) {
        this.cStart = cStart;
    }

    public Date getCEnd() {
        return cEnd;
    }

    public void setCEnd(Date cEnd) {
        this.cEnd = cEnd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {return note;}

    public void setNote(String note) {this.note = note; }


    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorNumber() {
        return instructorNumber;
    }

    public void setInstructorNumber(String instructorNumber) {
        this.instructorNumber = instructorNumber;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public Date getCAlertStart() {
        return cAlertStart;
    }

    public void setCAlertStart(Date cAlertStart) {
        this.cAlertStart = cAlertStart;
    }

    public Date getCAlertEnd() {
        return cAlertEnd;
    }

    public void setCAlertEnd(Date cAlertEnd) {
        this.cAlertEnd = cAlertEnd;
    }

    @Override
    public String toString(){
        return "Course{" +
                "courseID=" + courseID +
                ", termID=" + termID +
                ", cTitle=" + cTitle +
                ", cStart=" + cStart +
                ", cEnd=" + cEnd +
                ", status=" + status +
                ", note=" + note +
                ", instructorName=" + instructorName +
                ", instructorNumber=" + instructorNumber +
                ", instructorEmail=" + instructorEmail +
                ", cAlertStart=" + cAlertStart +
                ", cAlertEnd=" + cAlertEnd + '\'' +
                '}';
    }
}
