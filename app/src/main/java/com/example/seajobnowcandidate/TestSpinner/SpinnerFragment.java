package com.example.seajobnowcandidate.TestSpinner;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.seajobnowcandidate.R;
import com.example.seajobnowcandidate.databinding.FragmentSpinnerBinding;

import java.util.ArrayList;
import java.util.List;

public class SpinnerFragment extends Fragment {
    List<EmployementType> employementTypeRequestList;
    EmployementTypeAdapter employementTypeAdapter;
    FragmentSpinnerBinding fragmentSpinnerBinding;
    String selectedEmployementTypeId,selectedEmployementTypeName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       //  inflater.inflate(R.layout.fragment_spinner, container, false);
        fragmentSpinnerBinding = FragmentSpinnerBinding.inflate(inflater, container, false);

        employementTypeRequestList = new ArrayList<>();
        addEmployementTypeSpinner();
        employementTypeAdapter = new EmployementTypeAdapter(getContext(),employementTypeRequestList);

        fragmentSpinnerBinding.spinner.setAdapter(employementTypeAdapter);
        fragmentSpinnerBinding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {

                //  selectedEmployementTypeId = String.valueOf(employementTypeRequestList.get(i).getTypeId());
                selectedEmployementTypeName = employementTypeRequestList.get(i).getTypeName();

                Log.d("selectedTypeMame",selectedEmployementTypeName);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return fragmentSpinnerBinding.getRoot();
    }
    public void addEmployementTypeSpinner()
    {
        // Adding items to ArrayList

        employementTypeRequestList.add(new EmployementType(1,"Part Time"));
        employementTypeRequestList.add(new EmployementType(2,"Full Time"));
        employementTypeRequestList.add(new EmployementType(3,"Contract"));

    }

}