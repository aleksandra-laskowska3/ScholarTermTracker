package com.example.termtracker1.UI;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.example.termtracker1.R;
import com.example.termtracker1.entities.Term;
import android.content.*;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.List;
import android.view.ViewGroup;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;

        private TermViewHolder(View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.termTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Term current = mTerms.get(position);
                    String myFormat = "yyyy-MM-dd";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    String startDateString = sdf.format(current.getStartDate().getTime());
                    String endDateString = sdf.format(current.getEndDate().getTime());
                    Intent intent = new Intent(context, TermDetailActivity.class);
                    intent.putExtra("termTitle", current.getTermTitle());
                    intent.putExtra("termID", current.getTermID());
                    intent.putExtra("startDate", startDateString);
                    intent.putExtra("endDate", endDateString);
                    context.startActivity(intent);
                }
            });


        }


    }
    private List<Term> mTerms;
    private final LayoutInflater mInflater;
    private final Context context;

    public TermAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }
    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(itemView);

    }



    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position){
        if(mTerms != null){
            Term current = mTerms.get(position);
            String name=current.getTermTitle();
            holder.termItemView.setText(name);
        }
        else{
            holder.termItemView.setText("No Term Title");
        }
    }
    public void setTerms(List<Term> terms) {
        mTerms = terms;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(mTerms != null)
            return mTerms.size();
        else return 0;
    }
}
