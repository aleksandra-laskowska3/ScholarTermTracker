package com.example.termtracker1.dao;

import com.example.termtracker1.entities.Term;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;
import java.util.List;
@Dao
public interface TermDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Term term);
    @Update
    void update(Term term);
    @Delete
    void delete(Term term);
    @Query("SELECT * FROM TERMS ORDER BY termID ASC")
    List<Term> getAllTerms();

}
