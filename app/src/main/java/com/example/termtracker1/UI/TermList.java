package com.example.termtracker1.UI;

import androidx.appcompat.app.AppCompatActivity;
import com.example.termtracker1.R;
import com.example.termtracker1.database.Repository;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.termtracker1.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class TermList extends AppCompatActivity {
    private Repository repository;
    RecyclerView recycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        recycleView = findViewById(R.id.termrecyclerview);
        final TermAdapter termAdapter = new TermAdapter(this);
        recycleView.setAdapter(termAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        repository = new Repository(getApplication());
        List<Term> allTerms = repository.getAllTerms();
        termAdapter.setTerms(allTerms);
        System.out.println("All terms has " + allTerms.size());

        FloatingActionButton fab = findViewById(R.id.addTerm);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermList.this, AddTerm.class);
                startActivity(intent);
            }
        });

    }
    @Override
        protected void onResume(){

        super.onResume();
        List<Term> allTerms=repository.getAllTerms();
        RecyclerView recyclerView= findViewById(R.id.termrecyclerview);
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);
        }


}

