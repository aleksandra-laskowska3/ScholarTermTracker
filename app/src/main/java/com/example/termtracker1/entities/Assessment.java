package com.example.termtracker1.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.time.LocalDate;
import java.util.Date;
@Entity(tableName = "assessments")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    private int courseID;
    private String aTitle;
    private String type;
    private Date aStart;
    private Date aEnd;
    private Date aAlertStart;
    private Date aAlertEnd;


    public Assessment(int assessmentID, int courseID, String aTitle,
                      String type, Date aStart, Date aEnd, Date aAlertStart,
                      Date aAlertEnd) {
        this.assessmentID = assessmentID;
        this.courseID = courseID;
        this.aTitle = aTitle;
        this.type = type;
        this.aStart = aStart;
        this.aEnd = aEnd;
        this.aAlertStart = aAlertStart;
        this.aAlertEnd = aAlertEnd;
    }

    public Assessment() {
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getATitle() {
        return aTitle;
    }

    public void setATitle(String aTitle) {
        this.aTitle = aTitle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getAStart() {
        return aStart;
    }

    public void setAStart(Date aStart) {
        this.aStart = aStart;
    }

    public Date getAEnd() {
        return aEnd;
    }

    public void setAEnd(Date aEnd) {
        this.aEnd = aEnd;
    }

    public Date getAAlertStart() {
        return aAlertStart;
    }

    public void setAAlertStart(Date aAlertStart) {
        this.aAlertStart = aAlertStart;
    }
    public Date getAAlertEnd() {
        return aAlertEnd;
    }

    public void setAAlertEnd(Date aAlertEnd) {
        this.aAlertEnd = aAlertEnd;
    }

    @Override
    public String toString(){
        return "Assessment{" +
                "assessmentID=" + assessmentID +
                ", courseID=" + courseID +
                ", aTitle" + aTitle +
                ", type" + type +
                ", aStart" + aStart +
                ", aEnd" + aEnd +
                ", aAlertStart" + aAlertStart +
                ", aAlertEnd" + aAlertEnd + '\''
                + '}';
    }
}
