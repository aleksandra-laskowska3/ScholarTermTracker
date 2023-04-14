package com.example.termtracker1.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.termtracker1.R;
import com.example.termtracker1.database.Repository;
import com.example.termtracker1.entities.Assessment;
import com.example.termtracker1.entities.Course;
import com.example.termtracker1.util.DateParser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AssessmentDetailActivity extends AppCompatActivity {
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
    Assessment currentAssessment;
    Button backButton;
    int some = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        repository = new Repository(getApplication());
        aTitle = findViewById(R.id.assessment_title);
        type = findViewById(R.id.assessment_type);
        startPickerButton = findViewById(R.id.StartPickerButton);
        endPickerButton = findViewById(R.id.EndPickerButton);
        alertStartButton = findViewById(R.id.alertStartButton);
        alertEndButton = findViewById(R.id.alertEndButton);
        backButton = findViewById(R.id.backButton);


        assessmentID = getIntent().getIntExtra("assessmentID", 0);
        courseID = getIntent().getIntExtra("courseID", 0);
        aTitle.setText(getIntent().getStringExtra("aTitle"));
        type.setText(getIntent().getStringExtra("type"));
        String startString = getIntent().getStringExtra("aStart");
        String endString = getIntent().getStringExtra("aEnd");
        String aStartString = getIntent().getStringExtra("aAlertStart");
        String aEndString = getIntent().getStringExtra("aAlertEnd");
        startPickerButton.setText(startString);
        endPickerButton.setText(endString);
        alertStartButton.setText(aStartString);
        alertEndButton.setText(aEndString);
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        alertStartDate = DateParser.dateFromString(aStartString);
        alertEndDate = DateParser.dateFromString(aEndString);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                new DatePickerDialog(AssessmentDetailActivity.this, startDate,
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
                assessmentEnd = calendarEnd.getTime();
                updateLabelEnd();


            }
        };
        endPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AssessmentDetailActivity.this, endDate,
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
                new DatePickerDialog(AssessmentDetailActivity.this, aStartDate,
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
                new DatePickerDialog(AssessmentDetailActivity.this, aEndDate,
                        calendarAEnd.get(Calendar.YEAR), calendarAEnd.get(Calendar.MONTH),
                        calendarAEnd.get(Calendar.DAY_OF_MONTH)).show();
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessmentdetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                this.finish();
                Intent intent = new Intent(AssessmentDetailActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.save_assessment:

                if (aTitle.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please enter the Course Title", Toast.LENGTH_SHORT).show();
                } else {
                    assessment = new Assessment(assessmentID, courseID, aTitle.getText().toString(),
                            type.getText().toString(),
                            DateParser.dateFromString(startPickerButton.getText().toString()),
                            DateParser.dateFromString(endPickerButton.getText().toString()),
                            DateParser.dateFromString(alertStartButton.getText().toString()),
                            DateParser.dateFromString(alertEndButton.getText().toString()));
                    repository.update(assessment);
                    Toast.makeText(AssessmentDetailActivity.this, "Assessment Updated Successfully",
                            Toast.LENGTH_SHORT).show();

                }
                return true;
            case R.id.alert:

                Long trigger = alertStartDate.getTime();
                Intent alertIntent = new Intent(AssessmentDetailActivity.this, MyReceiver.class);
                alertIntent.putExtra("key", "An assessment is starting");
                PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetailActivity.this, ++MainActivity.numAlert, alertIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                Toast.makeText(AssessmentDetailActivity.this, "Alert is set", Toast.LENGTH_LONG).show();
                return true;
            case R.id.alertend:
                Long trigger1 = alertEndDate.getTime();
                Intent alertendIntent = new Intent(AssessmentDetailActivity.this, MyReceiver.class);
                alertendIntent.putExtra("key", "An assessment is due");
                PendingIntent endSender = PendingIntent.getBroadcast(AssessmentDetailActivity.this, ++MainActivity.numAlert, alertendIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManagerend = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManagerend.set(AlarmManager.RTC_WAKEUP, trigger1, endSender);
                Toast.makeText(AssessmentDetailActivity.this, "Alert is set", Toast.LENGTH_LONG).show();
                return true;
            case R.id.delete_assessment:
                for (Assessment delAssessment : repository.getAllAssessments()) {
                    if (delAssessment.getAssessmentID() == assessmentID) {
                        currentAssessment = delAssessment;
                        repository.delete(currentAssessment);
                        //     finish();
                        Toast.makeText(AssessmentDetailActivity.this, currentAssessment.getATitle() +
                                " was deleted", Toast.LENGTH_LONG).show();
                        this.finish();
                        return true;
                    }
                }
        }
        return super.onOptionsItemSelected(item);
    }
}