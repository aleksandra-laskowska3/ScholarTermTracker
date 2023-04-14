package com.example.termtracker1.UI;
import com.example.termtracker1.database.DatabaseBuilder;
import com.example.termtracker1.util.DateParser;
import static java.util.Date.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.termtracker1.R;
import com.example.termtracker1.database.Repository;
import com.example.termtracker1.entities.Term;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddTerm extends AppCompatActivity {
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
    Term term;
    private Button saveTermButton;
    Date termStartDate;
    Date termEndDate;
    TextView term_start;
    TextView term_end;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);
        repository = new Repository(getApplication());
        termTitle = findViewById(R.id.term_title);
        startDateButton = findViewById(R.id.addStartPickerButton);
        endDateButton = findViewById(R.id.addEndPickerButton);
        term_start = findViewById(R.id.term_start);
        term_end = findViewById(R.id.term_end);
        saveTermButton = findViewById(R.id.saveTerm);

        if (getIntent().getStringExtra("termTitle") != null) {
            termID = getIntent().getIntExtra("termID", -1);
            String startString = getIntent().getStringExtra("startDate");
            String endString = getIntent().getStringExtra("endDate");
            termTitle.setText(getIntent().getStringExtra("termTitle"));
            startDateButton.setText(startString);
            endDateButton.setText(endString);
        }

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
                new DatePickerDialog(AddTerm.this, startDate,
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
                new DatePickerDialog(AddTerm.this, endDate,
                        calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH),
                        calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        saveTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTerm(view);
                finish();

            }
        });
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
    private void saveTerm(View view) {
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
                repository.insert(term);
                Toast.makeText(AddTerm.this, "Term Added Successfully",
                        Toast.LENGTH_SHORT).show();


            }

        }

    }




