package com.example.seajobnowcandidate.ui.FindJobs;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.seajobnowcandidate.databinding.FragmentFindJobsBinding;
import com.example.seajobnowcandidate.ui.PostJob.PostJobFragment;
import com.example.seajobnowcandidate.ui.RecentSearch.RecentSearchFragment;

import java.util.ArrayList;
import java.util.List;


public class FindJobsFragment extends Fragment {

    FragmentFindJobsBinding fragmentFindJobsBinding;

    FindJobViewModel findJobViewModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentFindJobsBinding= FragmentFindJobsBinding.inflate(getLayoutInflater(), container, false);
        findJobViewModel = new ViewModelProvider(this).get(FindJobViewModel.class);
        setupViewPager(fragmentFindJobsBinding.viewpager);

        fragmentFindJobsBinding.tabs.setupWithViewPager(fragmentFindJobsBinding.viewpager);

        return fragmentFindJobsBinding.getRoot();
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new PostJobFragment(), "Job feed");
        adapter.addFragment(new RecentSearchFragment(), "Recent searches");
        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //  System.out.println("landscape");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            //  System.out.println("portrait");
        }
    }

}