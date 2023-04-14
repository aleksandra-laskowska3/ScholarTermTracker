package com.example.termtracker1.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "terms")
public class Term{
    @PrimaryKey(autoGenerate = true)
    private int termID;
    private String termTitle;
    private Date startDate;
    private Date endDate;


    public Term(Integer termID, String termTitle, Date startDate, Date endDate){
        this.termID = termID;
        this.termTitle = termTitle;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Term() {
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
    }


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString(){
        return "Term{" +
                "termID=" + termID +
                ", termTitle=" + termTitle +
                ", startDate=" + startDate +
                ", endDate=" + endDate + '\'' +
                '}';
    }
}
