package com.example.termtracker1.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.app.DatePickerDialog;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


import com.example.termtracker1.R;
import com.example.termtracker1.database.Repository;
import com.example.termtracker1.entities.Course;
import com.example.termtracker1.entities.Term;
import com.example.termtracker1.util.DateParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TermDetailActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button startDateButton;
    private Button endDateButton;
    final Calendar calendarStart = Calendar.getInstance();
    final Calendar calendarEnd = Calendar.getInstance();
    private EditText termTitle;
    private int termID;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    private Repository repository;
    Term currentTerm;
    int numCourses;
    Date termStartDate;
    Date termEndDate;
    RecyclerView recyclerView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        termTitle = findViewById(R.id.term_title);
        startDateButton = findViewById(R.id.startPickerButton);
        endDateButton = findViewById(R.id.endPickerButton);
        termID = getIntent().getIntExtra("termID", -1);
        String startString = getIntent().getStringExtra("startDate");
        String endString = getIntent().getStringExtra("endDate");
        termTitle.setText(getIntent().getStringExtra("termTitle"));
        termStartDate = DateParser.dateFromString(startString);
        termEndDate = DateParser.dateFromString(endString);
        startDateButton.setText(startString);
        endDateButton.setText(endString);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.courserecyclerview);
        repository = new Repository(getApplication());
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> filteredCourses = new ArrayList<>();
        for (Course c : repository.getAllCourses()) {
            if (c.getTermID() == termID) filteredCourses.add(c);
        }
        courseAdapter.setCourses(filteredCourses);


        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendarStart.set(Calendar.YEAR, year);
                calendarStart.set(Calendar.MONTH, monthOfYear);
                calendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                termStartDate = calendarStart.getTime();
                updateLabelStart();
            }
        };
        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(TermDetailActivity.this, startDate,
                        calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH),
                        calendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendarEnd.set(Calendar.YEAR, year);
                calendarEnd.set(Calendar.MONTH, monthOfYear);
                calendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                termEndDate = calendarEnd.getTime();
                updateLabelEnd();

            }
        };
        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(TermDetailActivity.this, endDate,
                        calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH),
                        calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_termdetails,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.home:
                this.finish();
                Intent intent = new Intent(TermDetailActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.save_term:
                Term term;
                if(termTitle.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please enter the Term Name", Toast.LENGTH_SHORT).show();
                }
               else if(!termStartDate.before(termEndDate)){
                   Toast.makeText(this, "The End Date cannot be before the Start Date", Toast.LENGTH_SHORT).show();
               }
                else{
                    term = new Term(termID, termTitle.getText().toString(),
                            DateParser.dateFromString(startDateButton.getText().toString()),
                            DateParser.dateFromString(endDateButton.getText().toString()));
                    repository.update(term);
                    Toast.makeText(TermDetailActivity.this, "Term Updated Successfully",
                            Toast.LENGTH_SHORT).show();
                    this.finish();
                }
                 return true;
            case R.id.delete_term:
                for(Term delTerm : repository.getAllTerms()){
                    if(delTerm.getTermID() == termID) currentTerm = delTerm;
                }
                numCourses = 0;
                for(Course course : repository.getAllCourses()){
                    if(course.getTermID() == termID) ++numCourses;
                }
                if (numCourses == 0){
                    repository.delete(currentTerm);
                    Toast.makeText(TermDetailActivity.this, currentTerm.getTermTitle() +
                            " was deleted", Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(TermDetailActivity.this, "Can't delete a term with courses",
                            Toast.LENGTH_LONG).show();
                }
                this.finish();
                return true;


            case R.id.add_course:
               Intent newIntent = new Intent(TermDetailActivity.this, AddCourseActivity.class);
               newIntent.putExtra("termID", termID);
               startActivity(newIntent);
                   finish();
                    return true;

                }
        return super.onOptionsItemSelected(item);
        }



    private void updateLabelStart() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        startDateButton.setText(sdf.format(calendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        endDateButton.setText(sdf.format(calendarEnd.getTime()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> filteredCourses = new ArrayList<>();
        for (Course c : repository.getAllCourses()){
            if (c.getTermID() == termID) filteredCourses.add(c);
        }
        courseAdapter.setCourses(filteredCourses);


    }
}
