package com.example.seajobnowcandidate.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.seajobnowcandidate.Entity.request.CityRequest;
import com.example.seajobnowcandidate.R;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends ArrayAdapter<CityRequest> implements Filterable {
    private final Context mContext;
    private final List<CityRequest> mCityRequests;
    private final List<CityRequest> mCityRequestListAll;
    private final int mLayoutResourceId;

    public CityAdapter(Context context, int resource, List<CityRequest> cityRequests) {
        super(context, resource, cityRequests);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mCityRequests = new ArrayList<>(cityRequests);
        this.mCityRequestListAll = new ArrayList<>(cityRequests);
    }

    public int getCount() {
        return mCityRequests.size();
    }

    public CityRequest getItem(int position) {
        return mCityRequests.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                convertView = inflater.inflate(mLayoutResourceId, parent, false);
            }
            CityRequest cityRequest = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.text_spinner);
            name.setText(cityRequest.getCityName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            public String convertResultToString(Object resultValue) {
                return ((CityRequest) resultValue).getCityName();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<CityRequest> cityRequestSuggestion = new ArrayList<>();
                if (constraint != null) {
                    for (CityRequest cityRequest : mCityRequestListAll) {
                        if (cityRequest.getCityName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            cityRequestSuggestion.add(cityRequest);
                        }
                    }
                    filterResults.values = cityRequestSuggestion;
                    filterResults.count = cityRequestSuggestion.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mCityRequests.clear();
                if (results != null && results.count > 0) {
                    // avoids unchecked cast warning when using mDepartments.addAll((ArrayList<Department>) results.values);
                    for (Object object : (List<?>) results.values) {
                        if (object instanceof CityRequest) {
                            mCityRequests.add((CityRequest) object);
                        }
                    }
                    notifyDataSetChanged();
                } else if (constraint == null) {
                    // no filter, add entire original list back in
                    mCityRequests.addAll(mCityRequestListAll);
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}
