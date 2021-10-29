package com.example.seajobnowcandidate.ui.RecentSearch;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.seajobnowcandidate.R;
import com.example.seajobnowcandidate.databinding.FragmentRecentSearchBinding;


public class RecentSearchFragment extends Fragment {
    FragmentRecentSearchBinding recentSearchBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        recentSearchBinding=FragmentRecentSearchBinding.inflate(getLayoutInflater(), container, false);





        return recentSearchBinding.getRoot();
    }
}