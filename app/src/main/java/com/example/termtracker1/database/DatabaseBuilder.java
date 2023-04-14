package com.example.termtracker1.database;

import com.example.termtracker1.entities.*;
import com.example.termtracker1.dao.*;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import android.content.Context;

@Database(entities = {Term.class, Course.class, Assessment.class}, version = 3,
        exportSchema = false)
@TypeConverters(DateConverter.class)

public abstract class DatabaseBuilder extends RoomDatabase{
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();


    private static volatile DatabaseBuilder INSTANCE;

    static DatabaseBuilder getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (DatabaseBuilder.class){
                if(INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    DatabaseBuilder.class, "TermTrackerDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }

        }
        return INSTANCE;
    }
}
