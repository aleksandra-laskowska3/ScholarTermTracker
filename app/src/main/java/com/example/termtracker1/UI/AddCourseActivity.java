package com.example.termtracker1.UI;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.MessagePattern;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class AddCourseActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private int termID;
    private int courseID;
    private Button saveCourseButton;
    private EditText cTitle;
    private EditText status;
    private EditText note;
    private EditText instructorName;
    private EditText instructorNumber;
    private EditText email;
    final Calendar calendarCStart = Calendar.getInstance();
    final Calendar calendarCEnd = Calendar.getInstance();
    final Calendar calendarAStart = Calendar.getInstance();
    final Calendar calendarAEnd = Calendar.getInstance();
    private Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    DatePickerDialog.OnDateSetListener aStartDate;
    DatePickerDialog.OnDateSetListener aEndDate;
    Date courseStartDate;
    Date courseEndDate;
    Date alertStartDate;
    Date alertEndDate;
    Button startPickerButton;
    Button endPickerButton;
    Button alertStartButton;
    Button alertEndButton;
    Course course;
    Button backButton;
    int some = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        repository = new Repository(getApplication());
        cTitle = findViewById(R.id.course_title);
        startPickerButton = findViewById(R.id.startPickerButton);
        endPickerButton = findViewById(R.id.endPickerButton);
        status = findViewById(R.id.course_status);
        note = findViewById(R.id.course_note);
        instructorName = findViewById(R.id.instructor_name);
        instructorNumber = findViewById(R.id.instructor_number);
        email = findViewById(R.id.email);
        alertEndButton = findViewById(R.id.alertEndButton);
        alertStartButton = findViewById(R.id.alertStartButton);
        saveCourseButton = findViewById(R.id.saveCourseButton);
        backButton = findViewById(R.id.backButton);

            termID = getIntent().getIntExtra("termID", -1);


        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendarCStart.set(Calendar.YEAR, year);
                calendarCStart.set(Calendar.MONTH, monthOfYear);
                calendarCStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                courseStartDate = calendarCStart.getTime();

                updateLabelStart();
            }
        };
        startPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddCourseActivity.this, startDate,
                        calendarCStart.get(Calendar.YEAR), calendarCStart.get(Calendar.MONTH),
                        calendarCStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendarCEnd.set(Calendar.YEAR, year);
                calendarCEnd.set(Calendar.MONTH, monthOfYear);
                calendarCEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                courseEndDate = calendarCEnd.getTime();
                updateLabelEnd();


            }
        };
        endPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddCourseActivity.this, endDate,
                        calendarCEnd.get(Calendar.YEAR), calendarCEnd.get(Calendar.MONTH),
                        calendarCEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        aStartDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendarAStart.set(Calendar.YEAR, year);
                calendarAStart.set(Calendar.MONTH, monthOfYear);
                calendarAStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                alertStartDate = calendarAStart.getTime();

                updateLabelStartAlert();
            }
        };
        alertStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddCourseActivity.this, aStartDate,
                        calendarAStart.get(Calendar.YEAR), calendarAStart.get(Calendar.MONTH),
                        calendarAStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        aEndDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendarAEnd.set(Calendar.YEAR, year);
                calendarAEnd.set(Calendar.MONTH, monthOfYear);
                calendarAEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                alertEndDate = calendarAEnd.getTime();
                updateLabelEndAlert();


            }
        };
        alertEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddCourseActivity.this, aEndDate,
                        calendarAEnd.get(Calendar.YEAR), calendarAEnd.get(Calendar.MONTH),
                        calendarAEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        saveCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCourse(view);
                if(some!=0){
                finish();}

            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

    }
    private void updateLabelStart() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        startPickerButton.setText(sdf.format(calendarCStart.getTime()));
    }
    private void updateLabelEnd() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        endPickerButton.setText(sdf.format(calendarCEnd.getTime()));
    }
    private void updateLabelStartAlert() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        alertStartButton.setText(sdf.format(calendarAStart.getTime()));
    }
    private void updateLabelEndAlert() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        alertEndButton.setText(sdf.format(calendarAEnd.getTime()));
    }
    private void saveCourse(View view) {

        if(cTitle.getText().toString().isEmpty() || status.getText().toString().isEmpty() ||
                note.getText().toString().isEmpty() || instructorNumber.getText().toString().isEmpty() ||
                instructorName.getText().toString().isEmpty() || email.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please fill out all the fields", Toast.LENGTH_SHORT).show();
        }
        else if(!courseStartDate.before(courseEndDate)){
            Toast.makeText(this, "The End Date cannot be before the Start Date", Toast.LENGTH_SHORT).show();

        }
        else if(!alertStartDate.before(alertEndDate)){
            Toast.makeText(this, "The Alert End cannot be before the Alert Start", Toast.LENGTH_SHORT).show();
        }
        else{
                if (repository.getAllCourses().size() == 0) {
                    courseID = 1;
                    course = new Course(termID, courseID, cTitle.getText().toString(),
                            DateParser.dateFromString(startPickerButton.getText().toString()),
                            DateParser.dateFromString(endPickerButton.getText().toString()),
                            status.getText().toString(), note.getText().toString(),
                            instructorName.getText().toString(), instructorNumber.getText().toString(),
                            email.getText().toString(), DateParser.dateFromString(alertStartButton.getText().toString()),
                            DateParser.dateFromString(alertEndButton.getText().toString()));
                    repository.insert(course);
                    Toast.makeText(AddCourseActivity.this, "Course Added Successfully",
                            Toast.LENGTH_SHORT).show();
                    some = 1;}
                else {
                    courseID = repository.getAllCourses().get(repository.getAllCourses().size() - 1).getCourseID() + 1;
                    course = new Course(termID, courseID, cTitle.getText().toString(),
                            DateParser.dateFromString(startPickerButton.getText().toString()),
                            DateParser.dateFromString(endPickerButton.getText().toString()),
                            status.getText().toString(), note.getText().toString(),
                            instructorName.getText().toString(), instructorNumber.getText().toString(),
                            email.getText().toString(), DateParser.dateFromString(alertStartButton.getText().toString()),
                            DateParser.dateFromString(alertEndButton.getText().toString()));
                    repository.insert(course);
                    Toast.makeText(AddCourseActivity.this, "Course Added Successfully",
                            Toast.LENGTH_SHORT).show();
                    some = 1;
                }
        }


    }

}
