package com.example.seajobnowcandidate.ui.myjobs;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.seajobnowcandidate.R;
import com.example.seajobnowcandidate.ui.applied.AppliedFragment;
import com.example.seajobnowcandidate.ui.archived.ArchivedFragment;
import com.example.seajobnowcandidate.ui.saved.SavedFragment;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

public class MyJobFragment extends Fragment {

    private MyJobViewModel mViewModel;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager2 viewpager;
    // tab titles
    private String[] titles = new String[]{"Saved", "Applied", "Archived"};

    public static MyJobFragment newInstance() {
        return new MyJobFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.my_job_fragment, container, false);


        viewpager = (ViewPager2) view.findViewById(R.id.viewpager);
        viewpager.setAdapter(new ViewPagerFragmentAdapter(getActivity()));

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MyJobViewModel.class);
        // TODO: Use the ViewModel

        TabLayoutMediator tabLayoutMediator=new TabLayoutMediator(
                tabLayout, viewpager, new TabLayoutMediator.TabConfigurationStrategy() {
            @SuppressLint("ResourceAsColor")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onConfigureTab(@NonNull @NotNull TabLayout.Tab tab, int position) {
                switch (position)
                {
                    case 0: {
                        tab.setText(titles[position]);
                        BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                        badgeDrawable.setBadgeTextColor(R.color.black);
                        badgeDrawable.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_grey));
                        badgeDrawable.setNumber(1);
                        badgeDrawable.setVisible(true);
                        break;
                    }
                    case 1: {
                        BadgeDrawable badgeDrawable1 = tab.getOrCreateBadge();
                        tab.setText(titles[position]);
                        badgeDrawable1.setBadgeTextColor(R.color.black);
                        badgeDrawable1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_grey));
                        badgeDrawable1.setNumber(1);
                        badgeDrawable1.setVisible(true);
                        break;
                    }
                    case 2: {
                        BadgeDrawable badgeDrawable2 = tab.getOrCreateBadge();
                        tab.setText(titles[position]);
                        badgeDrawable2.setBadgeTextColor(R.color.black);
                        badgeDrawable2.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_grey));
                        badgeDrawable2.setNumber(0);
                        badgeDrawable2.setVisible(true);
                        break;
                    }
                }
            }
        }
        );
        tabLayoutMediator.attach();

        viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
//                BadgeDrawable badgeDrawable=tabLayout.getTabAt(position).getOrCreateBadge();
//                badgeDrawable.setVisible(false);
            }
        });
    }

    private class ViewPagerFragmentAdapter extends FragmentStateAdapter {
        public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new SavedFragment();
                case 1:
                    return new AppliedFragment();
                case 2:
                    return new ArchivedFragment();
            }
            return new SavedFragment();
        }


        @Override
        public int getItemCount() {
            return titles.length;
        }
    }

}