package com.example.termtracker1.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termtracker1.R;
import com.example.termtracker1.entities.Assessment;
import com.example.termtracker1.entities.Course;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    class AssessmentViewHolder extends RecyclerView.ViewHolder{
        private final TextView assessmentItemView;
        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.assessmentTextView);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssessments.get(position);
                    String myFormat = "yyyy-MM-dd";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    String startDateString = sdf.format(current.getAStart().getTime());
                    String endDateString = sdf.format(current.getAEnd().getTime());
                    String startAlertString = sdf.format(current.getAAlertStart().getTime());
                    String endAlertString = sdf.format(current.getAAlertEnd().getTime());
                    Intent intent = new Intent(context, AssessmentDetailActivity.class);
                    intent.putExtra("assessmentID", current.getAssessmentID());
                    intent.putExtra("courseID", current.getCourseID());
                    intent.putExtra("aTitle", current.getATitle());
                    intent.putExtra("type", current.getType());
                    intent.putExtra("aStart", startDateString);
                    intent.putExtra("aEnd", endDateString);
                    intent.putExtra("aAlertStart", startAlertString);
                    intent.putExtra("aAlertEnd", endAlertString);
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;

    public AssessmentAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }
    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView=mInflater.inflate(R.layout.assessment_list_item,parent,false);
        return new AssessmentAdapter.AssessmentViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position){
        if(mAssessments!=null){
            Assessment current = mAssessments.get(position);
            String name = current.getATitle();
            holder.assessmentItemView.setText(name);
            holder.assessmentItemView.setTextSize(24);
        }
        else{
            holder.assessmentItemView.setText("No assessment name");
        }
    }
    public void setAssessments(List<Assessment> assessments){
        mAssessments=assessments;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount(){
        return mAssessments.size();}
}
