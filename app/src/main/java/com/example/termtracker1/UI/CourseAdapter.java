package com.example.termtracker1.UI;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.*;
import com.example.termtracker1.R;
import com.example.termtracker1.entities.Course;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    class CourseViewHolder extends RecyclerView.ViewHolder{
        private final TextView courseItemView;
        private CourseViewHolder(View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.courseTextView);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    String myFormat = "yyyy-MM-dd";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    String startDateString = sdf.format(current.getCStart().getTime());
                    String endDateString = sdf.format(current.getCEnd().getTime());
                    String startAlertString = sdf.format(current.getCAlertStart().getTime());
                    String endAlertString = sdf.format(current.getCAlertEnd().getTime());
                    Intent intent = new Intent(context,CourseDetailActivity.class);
                    intent.putExtra("courseID", current.getCourseID());
                    intent.putExtra("termID", current.getTermID());
                    intent.putExtra("cTitle", current.getCTitle());
                    intent.putExtra("cStart", startDateString);
                    intent.putExtra("cEnd", endDateString);
                    intent.putExtra("status", current.getStatus());
                    intent.putExtra("note", current.getNote());
                    intent.putExtra("instructorName", current.getInstructorName());
                    intent.putExtra("instructorNumber", current.getInstructorNumber());
                    intent.putExtra("instructorEmail", current.getInstructorEmail());
                    intent.putExtra("cAlertStart", startAlertString);
                    intent.putExtra("cAlertEnd", endAlertString);
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    public CourseAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }
    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView=mInflater.inflate(R.layout.course_list_item,parent,false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position){
        if(mCourses!=null){
            Course current = mCourses.get(position);
            String name = current.getCTitle();
            holder.courseItemView.setText(name);
            holder.courseItemView.setTextSize(24);
        }
        else{
            holder.courseItemView.setText("No course name");
        }
    }
    public void setCourses(List<Course> courses){
        mCourses=courses;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount(){
        return mCourses.size();}
    }

