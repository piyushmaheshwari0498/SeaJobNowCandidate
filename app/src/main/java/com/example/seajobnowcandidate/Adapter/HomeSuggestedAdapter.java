package com.example.seajobnowcandidate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seajobnowcandidate.Entity.request.HomeCategoryRequest;
import com.example.seajobnowcandidate.R;

import java.util.List;

public class HomeSuggestedAdapter extends RecyclerView.Adapter<HomeSuggestedAdapter.ViewHolder>{
    //  public static List<CategoryRequest> categoryRequestList;
    Context context;
    String image_URL = "http://forlimpopoli.in/beta_mobile/public/uploads/category/";
    String image_not_found_URL = "http://forlimpopoli.in/beta_mobile/public/assets/img/";
    String photopath;
    List<HomeCategoryRequest> categoryListFiltered;
    List<HomeCategoryRequest> originalList;

    public HomeSuggestedAdapter(List<HomeCategoryRequest> categoryRequestList, Context context) {
        //  this.categoryRequestList = categoryRequestList;
        this.context = context;
        this.categoryListFiltered = categoryRequestList;
        this.originalList = categoryRequestList;
    }

    @NonNull
    @Override
    public HomeSuggestedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_suggested_adpter_layout, viewGroup, false);
        return new HomeSuggestedAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeSuggestedAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.categoryName.setText(categoryListFiltered.get(i).getCatName());
//        photopath = categoryListFiltered.get(i).getCatPhoto();


        String url = image_URL + photopath;
        String not_found_url = image_not_found_URL + photopath;
        /*if (!url.contains("no-photo.jpg")) {
            Glide.with(context)
                    .load(url)
                    .centerCrop()
                    .placeholder(R.drawable.progress_animation)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_baseline_error_outline_24)
                    .into(viewHolder.imageView_icon);
        } else {
            Glide.with(context)
                    .load(not_found_url)
                    .centerCrop()
                    .placeholder(R.drawable.progress_animation)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_baseline_error_outline_24)
                    .into(viewHolder.imageView_icon);
        }*/
//        System.out.println("image_URL category" + image_URL + photopath);
    }

    @Override
    public int getItemCount() {
        return categoryListFiltered.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //ImageView imageView_icon;
        TextView categoryName;
        //CardView rr_category;

        ViewHolder(View itemView) {
            super(itemView);
            //imageView_icon = itemView.findViewById(R.id.imageView_icon);
            categoryName = itemView.findViewById(R.id.textview);
            //rr_category = itemView.findViewById(R.id.rr_category);
            //rr_category.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //case R.id.rr_category:
                   /* Intent intent = new Intent(context, SubCategoryActivity.class);
                    intent.putExtra(Constants.INTENT_KEYS.KEY_CATEGORY_ID, categoryListFiltered.get(getAdapterPosition()).getCatId());
                    intent.putExtra(Constants.INTENT_KEYS.KEY_CATEGORY_NAME, categoryListFiltered.get(getAdapterPosition()).getCatName());
                    context.startActivity(intent);*/
            }
        }
    }


}