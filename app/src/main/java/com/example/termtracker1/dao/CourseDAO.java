package com.example.termtracker1.dao;

import com.example.termtracker1.entities.Course;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;
import java.util.List;
@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);
    @Update
    void update(Course course);
    @Delete
    void delete(Course course);
    @Query("SELECT * FROM COURSES ORDER BY courseID ASC")
    List<Course> getAllCourses();

    @Query("SELECT * FROM COURSES WHERE termID = :termID ORDER BY courseID ASC")
    List<Course> getAllAssociatedCourses(int termID);

}
