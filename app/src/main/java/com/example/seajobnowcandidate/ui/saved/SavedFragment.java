package com.example.seajobnowcandidate.ui.saved;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.seajobnowcandidate.Adapter.SavedJobAdapter;
import com.example.seajobnowcandidate.Model.SavedJob;
import com.example.seajobnowcandidate.R;

import java.util.ArrayList;
import java.util.List;

public class SavedFragment extends Fragment {

    private SavedViewModel mViewModel;
    List<SavedJob> savedJobList;
    RecyclerView recyclerView_SavedJob;
    SavedJobAdapter msavedJobAdapter;

    public static SavedFragment newInstance() {
        return new SavedFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.saved_fragment, container, false);
        recyclerView_SavedJob=view.findViewById(R.id.recyclerView_SavedJob);
        savedJobList=new ArrayList<>();
        addItemsToRecyclerViewArrayList();
        msavedJobAdapter = new SavedJobAdapter(savedJobList,getActivity());
        recyclerView_SavedJob.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView_SavedJob.setAdapter(msavedJobAdapter);
        return view;
    }

    public void addItemsToRecyclerViewArrayList()
    {
        SavedJob savedJob=new SavedJob("Senior Android Developer","amsys it services pvt ltd","Mumbai,Maharashtra","Saved today");
        savedJobList.add(savedJob);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SavedViewModel.class);
        // TODO: Use the ViewModel
    }

}