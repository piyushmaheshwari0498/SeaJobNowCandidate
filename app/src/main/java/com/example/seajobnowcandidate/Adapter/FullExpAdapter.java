package com.example.seajobnowcandidate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seajobnowcandidate.Entity.request.FullExpRequest;
import com.example.seajobnowcandidate.Model.FullExperience;
import com.example.seajobnowcandidate.R;

import java.util.List;

public class FullExpAdapter extends RecyclerView.Adapter<FullExpAdapter.ViewHolder> {
    public List<FullExpRequest> fullExperienceList;
    Context context;

    public FullExpAdapter(List<FullExpRequest> fullExperienceList, Context context) {
        this.fullExperienceList = fullExperienceList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.full_exp_adpter_layout, viewGroup, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.tvshipname.setText(fullExperienceList.get(i).getShipName());
        viewHolder.tvvessel.setText(fullExperienceList.get(i).getVesselType());
        viewHolder.tvengine.setText(fullExperienceList.get(i).getEngineType());
        viewHolder.tvenginepower.setText(fullExperienceList.get(i).getEnginePower());
        viewHolder.tvfrom.setText(fullExperienceList.get(i).getFromdate());
        viewHolder.tvtodate.setText(fullExperienceList.get(i).getToDate());
        viewHolder.tvTime.setText(fullExperienceList.get(i).getTotalTime());
        viewHolder.tvreason_stick.setText(fullExperienceList.get(i).getReasonStick());
        viewHolder.tvreason_change.setText(fullExperienceList.get(i).getChangeReason());
        viewHolder.tvcircumstances.setText(fullExperienceList.get(i).getCircumtance());
        viewHolder.tvmedical.setText(fullExperienceList.get(i).getSignedOff());
        viewHolder.tvsurgery.setText(fullExperienceList.get(i).getSurgery());
        viewHolder.tvachivement.setText(fullExperienceList.get(i).getAchivement());
    }

    @Override
    public int getItemCount() {
        return fullExperienceList.size();
    }

    public void removeItem(int position) {
        fullExperienceList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(FullExpRequest fullExperience, int position) {
        fullExperienceList.add(position, fullExperience);
        notifyItemInserted(position);
    }

    public List<FullExpRequest> getData() {
        return fullExperienceList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvshipname,tvvessel,tvengine,tvenginepower,tvfrom,tvtodate,tvTime,tvreason_stick,tvreason_change,tvcircumstances,tvmedical;
        TextView tvsurgery,tvachivement;
        ViewHolder(View itemView) {
            super(itemView);
            tvshipname = itemView.findViewById(R.id.tvshipname);
            tvvessel = itemView.findViewById(R.id.tvvessel);
            tvengine = itemView.findViewById(R.id.tvengine);
            tvenginepower = itemView.findViewById(R.id.tvenginepower);
            tvfrom = itemView.findViewById(R.id.tvfrom);
            tvtodate = itemView.findViewById(R.id.tvtodate);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvreason_stick = itemView.findViewById(R.id.tvreason_stick);
            tvreason_change = itemView.findViewById(R.id.tvreason_change);
            tvcircumstances = itemView.findViewById(R.id.tvcircumstances);
            tvmedical = itemView.findViewById(R.id.tvmedical);
            tvsurgery = itemView.findViewById(R.id.tvsurgery);
            tvachivement = itemView.findViewById(R.id.tvachivement);

        }
    }
}
