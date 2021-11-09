package com.example.seajobnowcandidate.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seajobnowcandidate.Activity.PostDetailsActivity;
import com.example.seajobnowcandidate.Entity.request.PostJobDetailsRequest;
import com.example.seajobnowcandidate.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class PostJobsAdapter extends RecyclerView.Adapter<PostJobsAdapter.MyView> {

    private List<PostJobDetailsRequest> list;
    Context context;
    DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

    // Constructor for adapter class
    // which takes a list of String type
    int resourceId;
    public PostJobsAdapter(Context context, List<PostJobDetailsRequest> horizontalList,int resourceId) {
        this.context = context;
        this.list = horizontalList;
        this.resourceId = resourceId;
    }

    // Override onCreateViewHolder which deals
    // with the inflation of the card layout
    // as an item for the RecyclerView.
    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate item.xml using LayoutInflator
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(resourceId,
                        parent,
                        false);

        // return itemView
        return new MyView(itemView);
    }

    // Override onBindViewHolder which deals
    // with the setting of different data
    // and methods related to clicks on
    // particular items of the RecyclerView.
    @SuppressLint("RecyclerView")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(MyView holder, int position) {

        // Set the text of each item of
        // Recycler view with the list items
//        holder.cardView.setBackgroundColor(context.getColor(list.get(position).getColor()));
        String title = list.get(position).getCjmRank() +" - "+list.get(position).getCjmPostName();
        String postedDate = "Posted : "+ list.get(position).getCjmStartDate();
        String exp = list.get(position).getCjmExperienceInMonths()+" Months";
        holder.textView.setText(title);
        holder.textViewlocation.setText(list.get(position).getCjmJobLocation());
        holder.textViewrank.setText(list.get(position).getCjmRank());
        holder.textViewdepartment.setText(list.get(position).getCjmDepartment());
        holder.textViewship.setText(list.get(position).getCjmVesselType());
        holder.textViewexpirydate.setText(list.get(position).getCjmExpiryDate());
        holder.textViewdate.setText(postedDate);
        holder.textViewexpirence.setText(exp);

        if(list.get(position).getCjmSalary() != null)
            holder.textViewslary.setText(list.get(position).getCjmSalary());
        else
            holder.textViewslary.setVisibility(View.GONE);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // new ShowSnackbar().shortSnackbar(view,list.get(position).getJob_title());
                Intent intent= new Intent(context, PostDetailsActivity.class);
                intent.putExtra("post_title",list.get(position).getCjmPostName());
                intent.putExtra("post_id",list.get(position).getCjmId());
                context.startActivity(intent);

            }
        });



    }


    // Override getItemCount which Returns
    // the length of the RecyclerView.
    @Override
    public int getItemCount() {
        return list.size();
    }

    // View Holder class which
    // extends RecyclerView.ViewHolder
    public class MyView extends RecyclerView.ViewHolder {

        // Text View
        TextView textView;
        TextView textViewrank;
        TextView textViewdepartment;
        TextView textViewship;
        TextView textViewlocation;
        TextView textViewslary;
        TextView textViewdate;
        TextView textViewexpirydate;
        TextView textViewexpirence;
        CardView cardView;

        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view) {
            super(view);

            // initialise TextView with id
            textView = (TextView) view.findViewById(R.id.job_title);
            textViewrank = (TextView) view.findViewById(R.id.textview_rank);
            textViewdepartment = (TextView) view.findViewById(R.id.textview_department);
            textViewship = (TextView) view.findViewById(R.id.textview_ship_type);
            textViewlocation = (TextView) view.findViewById(R.id.textview_location);
            textViewslary = (TextView) view.findViewById(R.id.job_salary);
            textViewdate = (TextView) view.findViewById(R.id.textview_postedDate);
            textViewexpirydate = (TextView) view.findViewById(R.id.textview_expiry_date);
            textViewexpirence = (TextView) view.findViewById(R.id.textview_experience);
            cardView = (CardView) view.findViewById(R.id.cardview);
        }
    }
}

