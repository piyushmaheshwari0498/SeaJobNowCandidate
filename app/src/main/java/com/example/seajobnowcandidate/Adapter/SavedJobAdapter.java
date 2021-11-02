package com.example.seajobnowcandidate.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.seajobnowcandidate.Model.SavedJob;
import com.example.seajobnowcandidate.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class SavedJobAdapter extends RecyclerView.Adapter<SavedJobAdapter.ViewHolder> {
    Context context;
    List<SavedJob> savedJobList;
    SavedJob mRecentlyDeletedItem;
    int mRecentlyDeletedItemPosition;
    LinearLayout applied,interview,offer,hire,notSelected;
    TextView tvCancel;
    View itemView;

    public SavedJobAdapter(List<SavedJob> savedJobList, Context context)
    {
        this.savedJobList = savedJobList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
    {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_job_items, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
       viewHolder.tvDesignation.setText(savedJobList.get(position).getDesignation());
       viewHolder.tvCompanyName.setText(savedJobList.get(position).getCompanyName());
       viewHolder.tvAddress.setText(savedJobList.get(position).getAddress());
       viewHolder.tvStatus.setText(savedJobList.get(position).getDaySaved());

       viewHolder.imageViewRemove.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               deleteItem(position,viewHolder);
           }
       });
    }

    @Override
    public int getItemCount()
    {
        return savedJobList.size();
    }

    public void deleteItem(int position,ViewHolder view) {
        mRecentlyDeletedItem = savedJobList.get(position);
        mRecentlyDeletedItemPosition = position;
        savedJobList.remove(position);
        notifyItemRemoved(position);
        showUndoSnackbar(view);
    }

    private void showUndoSnackbar(ViewHolder view) {
        Snackbar snackbar = Snackbar.make(view.itemView, "Undo Item",
                Snackbar.LENGTH_LONG);
        snackbar.setAction("Undo", v -> undoDelete());
        snackbar.show();
    }

    private void undoDelete() {
        savedJobList.add(mRecentlyDeletedItemPosition,
                mRecentlyDeletedItem);
        notifyItemInserted(mRecentlyDeletedItemPosition);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvDesignation,tvCompanyName,tvAddress,tvStatus,btnUpdateStatus;
        LinearLayout ll_saved_job;
        ImageView imageViewRemove;
        MaterialButton btnApply;
        public ViewHolder(View view)
        {
          super(view);
            btnApply = view.findViewById(R.id.btnApply);
            imageViewRemove = view.findViewById(R.id.imageViewRemove);

            tvDesignation = view.findViewById(R.id.tvDesignation);
            tvCompanyName = view.findViewById(R.id.tvCompanyName);
            tvAddress = view.findViewById(R.id.tvAddress);
            tvStatus = view.findViewById(R.id.tvStatus);
//            btnUpdateStatus = view.findViewById(R.id.btnUpdateStatus);
            ll_saved_job = view.findViewById(R.id.ll_saved_job);
            ll_saved_job.setOnClickListener(this);
           /* btnUpdateStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                showBottomSheetDialog();
                }
            });*/
        }

        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
//                case R.id.imageViewRemove:

              //  break;
            }
        }
    }
   /* private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_layout);

        applied = bottomSheetDialog.findViewById(R.id.applied);
        interview = bottomSheetDialog.findViewById(R.id.interview);
        offer = bottomSheetDialog.findViewById(R.id.offer);
        hire = bottomSheetDialog.findViewById(R.id.hire);
        notSelected = bottomSheetDialog.findViewById(R.id.notSelected);
        tvCancel = bottomSheetDialog.findViewById(R.id.tvCancel);
        bottomSheetDialog.show();
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetDialog.dismiss();
            }
        });
    }*/

}
