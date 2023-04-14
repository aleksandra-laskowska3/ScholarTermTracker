package com.example.termtracker1.UI;

import static com.example.termtracker1.R.id.startPickerButton;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.termtracker1.R;
import com.example.termtracker1.database.Repository;
import com.example.termtracker1.entities.Assessment;
import com.example.termtracker1.util.DateParser;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddAssessmentActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private int assessmentID;
    private int courseID;
    private EditText aTitle;
    private EditText type;
    final Calendar calendarStart = Calendar.getInstance();
    final Calendar calendarEnd = Calendar.getInstance();
    final Calendar calendarAStart = Calendar.getInstance();
    final Calendar calendarAEnd = Calendar.getInstance();
    private Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    DatePickerDialog.OnDateSetListener aStartDate;
    DatePickerDialog.OnDateSetListener aEndDate;
    Date assessmentStart;
    Date assessmentEnd;
    Date alertStartDate;
    Date alertEndDate;
    Button startPickerButton;
    Button endPickerButton;
    Button alertStartButton;
    Button alertEndButton;
    Assessment assessment;
    Button saveAButton;
    Button backButton;
    int some = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
        repository = new Repository(getApplication());
        aTitle = findViewById(R.id.assessment_title);
        type = findViewById(R.id.assessment_type);
        startPickerButton = findViewById(R.id.StartPickerButton);
        endPickerButton = findViewById(R.id.EndPickerButton);
        alertStartButton = findViewById(R.id.alertStartButton);
        alertEndButton = findViewById(R.id.alertEndButton);
        saveAButton = findViewById(R.id.saveAssessment);
        backButton = findViewById(R.id.backButton);

        courseID = getIntent().getIntExtra("courseID", -1);

        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendarStart.set(Calendar.YEAR, year);
                calendarStart.set(Calendar.MONTH, monthOfYear);
                calendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                assessmentStart = calendarStart.getTime();

                updateLabelStart();
            }
        };
        startPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddAssessmentActivity.this, startDate,
                        calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH),
                        calendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendarEnd.set(Calendar.YEAR, year);
                calendarEnd.set(Calendar.MONTH, monthOfYear);
                calendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                assessmentEnd = calendarEnd.getTime();

                updateLabelEnd();
            }
        };
        endPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddAssessmentActivity.this, endDate,
                        calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH),
                        calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
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
                new DatePickerDialog(AddAssessmentActivity.this, aStartDate,
                        calendarAStart.get(Calendar.YEAR), calendarAStart.get(Calendar.MONTH),
                        calendarAStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        aEndDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear,
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
                new DatePickerDialog(AddAssessmentActivity.this, aEndDate,
                        calendarAEnd.get(Calendar.YEAR), calendarAEnd.get(Calendar.MONTH),
                        calendarAEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        saveAButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAssessment(view);
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

        startPickerButton.setText(sdf.format(calendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        endPickerButton.setText(sdf.format(calendarEnd.getTime()));
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

    private void saveAssessment(View view) {
        if (aTitle.getText().toString().isEmpty() || type.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please fill out all the fields", Toast.LENGTH_SHORT).show();
        }
        else if(!assessmentStart.before(assessmentEnd)){
            Toast.makeText(this, "The End Date cannot be before the Start Date", Toast.LENGTH_SHORT).show();
        }
        else if(!alertStartDate.before(alertEndDate)){
            Toast.makeText(this, "The Alert End cannot be before the Alert Start", Toast.LENGTH_SHORT).show();
        }
        else {
            if (repository.getAllAssessments().size() == 0) {
                assessmentID = 1;
                assessment = new Assessment(assessmentID, courseID, aTitle.getText().toString(),
                        type.getText().toString(), DateParser.dateFromString(startPickerButton.getText().toString()),
                        DateParser.dateFromString(endPickerButton.getText().toString()),
                        DateParser.dateFromString(alertStartButton.getText().toString()),
                        DateParser.dateFromString(alertEndButton.getText().toString()));
                repository.insert(assessment);
                Toast.makeText(AddAssessmentActivity.this, "Assessment Added Successfully",
                        Toast.LENGTH_SHORT).show();
                some = 1;}
            else {
                assessmentID = repository.getAllAssessments().get(repository.getAllAssessments().size() - 1).getAssessmentID() + 1;
                assessment = new Assessment(assessmentID, courseID, aTitle.getText().toString(),
                        type.getText().toString(), DateParser.dateFromString(startPickerButton.getText().toString()),
                        DateParser.dateFromString(endPickerButton.getText().toString()),
                        DateParser.dateFromString(alertStartButton.getText().toString()),
                        DateParser.dateFromString(alertEndButton.getText().toString()));
                repository.insert(assessment);
                Toast.makeText(AddAssessmentActivity.this, "Assessment Added Successfully",
                        Toast.LENGTH_SHORT).show();
                some = 1;
            }
        }
    }
}
