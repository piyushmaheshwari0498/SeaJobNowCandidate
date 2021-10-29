package com.example.seajobnowcandidate.TestSpinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.seajobnowcandidate.R;
import java.util.List;

public class EmployementTypeAdapter extends BaseAdapter {
    Context context;
    List<EmployementType> employementTypeList;
    LayoutInflater inflter;
    public EmployementTypeAdapter(Context applicationContext, List<EmployementType> employementTypeList) {
        this.context = applicationContext;
        this.employementTypeList = employementTypeList;
        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return employementTypeList.size();
    }

    @Override
    public Object getItem(int position) {
        return employementTypeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        view = inflter.inflate(R.layout.custom_spinner_item, null);
        TextView name = (TextView) view.findViewById(R.id.text_spinner);
        name.setText(employementTypeList.get(i).getTypeName());
        return view;
    }
}/*extends ArrayAdapter<EmployementType>{
    private final Context mContext;
    List<EmployementType> mCityRequests;

    private final int mLayoutResourceId;


    public EmployementTypeAdapter(Context context, int resource, List<EmployementType> cityRequests) {
        super(context, 0, cityRequests);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mCityRequests = new ArrayList<>(cityRequests);

    }

    public int getCount() {
        return mCityRequests.size();
    }

    public EmployementType getItem(int position) {
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
            EmployementType cityRequest = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.text_spinner);
            if(cityRequest != null) {
                name.setText(cityRequest.getTypeName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }
}
*/