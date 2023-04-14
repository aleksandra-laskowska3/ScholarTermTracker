package com.example.termtracker1.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.termtracker1.entities.Assessment;
import com.example.termtracker1.entities.Course;
import com.example.termtracker1.entities.Term;
import com.example.termtracker1.util.DateParser;

import java.security.KeyStore;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseDetailActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private int termID;
    private int courseID;
  //  private Button saveCourseButton;
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
    private Button startPickerButton;
    private Button endPickerButton;
    private Button alertStartButton;
    private Button alertEndButton;
    Course course;
    Course currentCourse;
    RecyclerView recyclerView;
    Button backButton;
    int some = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        cTitle = findViewById(R.id.course_title);
        startPickerButton = findViewById(R.id.startPickerButton);
        endPickerButton = findViewById(R.id.endPickerButton);
        status = findViewById(R.id.course_status);
        note = findViewById(R.id.course_note);
        instructorName = findViewById(R.id.instructor_name);
        instructorNumber = findViewById(R.id.instructor_number);
        email = findViewById(R.id.email);
        alertStartButton = findViewById(R.id.alertStartButton);
        alertEndButton = findViewById(R.id.alertEndButton);
        backButton = findViewById(R.id.backButton);

        termID = getIntent().getIntExtra("termID", 0);
        courseID = getIntent().getIntExtra("courseID", 0);
        String startString = getIntent().getStringExtra("cStart");
        String endString = getIntent().getStringExtra("cEnd");
        String aStartString = getIntent().getStringExtra("cAlertStart");
        String aEndString = getIntent().getStringExtra("cAlertEnd");
        cTitle.setText(getIntent().getStringExtra("cTitle"));
        startPickerButton.setText(startString);
        endPickerButton.setText(endString);
        status.setText(getIntent().getStringExtra("status"));
        note.setText(getIntent().getStringExtra("note"));
        instructorName.setText(getIntent().getStringExtra("instructorName"));
        instructorNumber.setText(getIntent().getStringExtra("instructorNumber"));
        email.setText(getIntent().getStringExtra("instructorEmail"));
        alertStartButton.setText(aStartString);
        alertEndButton.setText(aEndString);
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        alertStartDate = DateParser.dateFromString(aStartString);
        alertEndDate = DateParser.dateFromString(aEndString);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.assessmentrecyclerview);
        repository = new Repository(getApplication());
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> filteredAssessments = new ArrayList<>();
        for (Assessment a : repository.getAllAssessments()) {
            if (a.getCourseID() == courseID) filteredAssessments.add(a);
        }
        assessmentAdapter.setAssessments(filteredAssessments);

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
                new DatePickerDialog(CourseDetailActivity.this, startDate,
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
                new DatePickerDialog(CourseDetailActivity.this, endDate,
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
             //   alertStartDate = calendarAStart.getTime();

                updateLabelStartAlert();
            }
        };
        alertStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CourseDetailActivity.this, aStartDate,
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
              //  alertEndDate = calendarAEnd.getTime();
                updateLabelEndAlert();


            }
        };
        alertEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CourseDetailActivity.this, aEndDate,
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
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_coursedetails,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.home:
                this.finish();
                Intent intent = new Intent(CourseDetailActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.add_assessment:
                Intent newIntent = new Intent(CourseDetailActivity.this, AddAssessmentActivity.class);
                newIntent.putExtra("courseID", courseID);
                startActivity(newIntent);
                return true;
            case R.id.save_course:
               // Course course;
                if(cTitle.getText().toString().isEmpty() || status.getText().toString().isEmpty() ||
                        note.getText().toString().isEmpty() || instructorNumber.getText().toString().isEmpty() ||
                        instructorName.getText().toString().isEmpty() || email.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please fill out all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    course = new Course(termID, courseID, cTitle.getText().toString(),
                            DateParser.dateFromString(startPickerButton.getText().toString()),
                            DateParser.dateFromString(endPickerButton.getText().toString()),
                            status.getText().toString(), note.getText().toString(),
                            instructorName.getText().toString(), instructorNumber.getText().toString(),
                            email.getText().toString(), DateParser.dateFromString(alertStartButton.getText().toString()),
                            DateParser.dateFromString(alertEndButton.getText().toString()));
                    repository.update(course);
                    Toast.makeText(CourseDetailActivity.this, "Course Updated Successfully",
                            Toast.LENGTH_SHORT).show();


                }
                    return true;
            case R.id.send_note:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, note.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Sharing Note");
                sendIntent.setType("text/plain");
                Intent shareIntent=Intent.createChooser(sendIntent,null);
                startActivity(shareIntent);
                return true;
            case R.id.alert:

                Long trigger = alertStartDate.getTime();
                Intent alertIntent = new Intent(CourseDetailActivity.this, MyReceiver.class);
                alertIntent.putExtra("key", "A course is starting");
                PendingIntent sender = PendingIntent.getBroadcast(CourseDetailActivity.this, ++MainActivity.numAlert, alertIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                Toast.makeText(CourseDetailActivity.this, "Alert is set", Toast.LENGTH_LONG).show();
                return true;
            case R.id.alertend:
                Long trigger1 = alertEndDate.getTime();
                Intent alertendIntent = new Intent(CourseDetailActivity.this, MyReceiver.class);
                alertendIntent.putExtra("key", "A course is ending");
                PendingIntent endSender = PendingIntent.getBroadcast(CourseDetailActivity.this, ++MainActivity.numAlert, alertendIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManagerend = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManagerend.set(AlarmManager.RTC_WAKEUP, trigger1, endSender);
                Toast.makeText(CourseDetailActivity.this, "Alert is set", Toast.LENGTH_LONG).show();
                return true;

                    case R.id.delete_course:
                        for (Course delCourse : repository.getAllCourses()) {
                            if (delCourse.getCourseID() == courseID) {
                                currentCourse = delCourse;
                                repository.delete(currentCourse);
                                Toast.makeText(CourseDetailActivity.this, currentCourse.getCTitle() +
                                        " was deleted", Toast.LENGTH_LONG).show();
                                this.finish();
                                return true;
                            }
                        }
                }
                return super.onOptionsItemSelected(item);
        }
    @Override
    protected void onResume() {
        super.onResume();
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> filteredAssessments = new ArrayList<>();
        for (Assessment a : repository.getAllAssessments()){
            if (a.getCourseID() == courseID) filteredAssessments.add(a);
        }
        assessmentAdapter.setAssessments(filteredAssessments);

    }
}
