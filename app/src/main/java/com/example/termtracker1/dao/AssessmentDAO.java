package com.example.termtracker1.dao;

import com.example.termtracker1.entities.Assessment;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;
import java.util.List;
@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);
    @Update
    void update(Assessment assessment);
    @Delete
    void delete(Assessment assessment);
    @Query("SELECT * FROM ASSESSMENTS ORDER BY assessmentID ASC")
    List<Assessment> getAllAssessments();

    @Query("SELECT * FROM ASSESSMENTS WHERE courseID = :courseID ORDER BY assessmentID ASC")
    List<Assessment> getAllAssociatedAssessments(int courseID);

}
