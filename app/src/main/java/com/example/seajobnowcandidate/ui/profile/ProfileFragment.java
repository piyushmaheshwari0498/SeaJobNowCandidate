package com.example.seajobnowcandidate.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.seajobnowcandidate.R;
import com.example.seajobnowcandidate.databinding.HomeFragmentBinding;
import com.example.seajobnowcandidate.databinding.ProfileFragmentBinding;
import com.example.seajobnowcandidate.ui.home.HomeViewModel;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    ProfileFragmentBinding binding;
    boolean isInfo = false;
    boolean isPassport = false;
    boolean isFamily = false;
    boolean isPreAdd = false;
    boolean isPermanentAdd = false;
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ProfileFragmentBinding.inflate(inflater, container, false);

        binding.llInfoData.setVisibility(View.VISIBLE);
        isInfo = true;
        binding.imgInfo.animate().rotationBy(180).setDuration(300).start();
        binding.llPassportData.setVisibility(View.GONE);

        binding.llInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.imgInfo.animate().rotationBy(180).setDuration(300).start();
                if(isInfo) {
                    isInfo = false;
                    binding.llInfoData.setVisibility(View.GONE);
                } else {
                   // binding.imgInfo.animate().rotationBy(180).setDuration(500).start();
                    isInfo = true;
                    binding.llInfoData.setVisibility(View.VISIBLE);
                }

            }
        });

        binding.llFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.imgFamily.animate().rotationBy(180).setDuration(300).start();
                if(isFamily) {
                    isFamily = false;
                    binding.llFamilyData.setVisibility(View.GONE);
                } else {
                    // binding.imgInfo.animate().rotationBy(180).setDuration(500).start();
                    isFamily = true;
                    binding.llFamilyData.setVisibility(View.VISIBLE);
                }

            }
        });

        binding.llPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.imgPreAddress.animate().rotationBy(180).setDuration(300).start();
                if(isPreAdd) {
                    isPreAdd = false;
                    binding.llPresentData.setVisibility(View.GONE);
                } else {
                    // binding.imgInfo.animate().rotationBy(180).setDuration(500).start();
                    isPreAdd = true;
                    binding.llPresentData.setVisibility(View.VISIBLE);
                }

            }
        });

        binding.llPermanent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.imgPermanentAddress.animate().rotationBy(180).setDuration(300).start();
                if(isPreAdd) {
                    isPreAdd = false;
                    binding.llPermanentData.setVisibility(View.GONE);
                } else {
                    // binding.imgInfo.animate().rotationBy(180).setDuration(500).start();
                    isPreAdd = true;
                    binding.llPermanentData.setVisibility(View.VISIBLE);
                }

            }
        });

        binding.llPassport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.imgPassport.animate().rotationBy(180).setDuration(300).start();
                if(isPassport) {
                    isPassport = false;
                    binding.llPassportData.setVisibility(View.GONE);
                } else {
                    // binding.imgInfo.animate().rotationBy(180).setDuration(500).start();
                    isPassport = true;
                    binding.llPassportData.setVisibility(View.VISIBLE);
                }

            }
        });

        return binding.getRoot();
    }

    public void closeLayout(LinearLayout linearLayout){

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }

}