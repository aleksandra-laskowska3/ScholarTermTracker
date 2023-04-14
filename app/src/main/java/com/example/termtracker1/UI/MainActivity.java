package com.example.termtracker1.UI;

import androidx.appcompat.app.AppCompatActivity;

import com.example.termtracker1.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.termtracker1.entities.Course;
import com.example.termtracker1.entities.Term;
import com.example.termtracker1.database.Repository;
import com.example.termtracker1.util.DateParser;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
public static int numAlert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, TermList.class);
                startActivity(intent);
            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addSampleData:
                Repository repository=new Repository(getApplication());
                Date startDate= DateParser.dateFromString("2023-01-10");
                Date endDate= DateParser.dateFromString("2023-05-15");
                Date startDateCourse= DateParser.dateFromString("2023-01-10");
                Date endDateCourse= DateParser.dateFromString("2023-05-15");
                Date alertStart = DateParser.dateFromString("2023-01-15");
                Date alertEnd = DateParser.dateFromString("2023-04-12");
                Course course = new Course(1,2, "COM 101", startDateCourse, endDateCourse,"Pending", "Course has the most homework", "John White",
                        "708-123-4567", "jWhite@yahoo.com", alertStart, alertEnd);
               repository.insert(course);
                Term term=new Term(0,"Term 2", startDate,endDate);
                repository.insert(term);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}