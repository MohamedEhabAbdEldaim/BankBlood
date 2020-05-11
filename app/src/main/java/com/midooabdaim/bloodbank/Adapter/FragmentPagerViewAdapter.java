package com.midooabdaim.bloodbank.Adapter;


import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.midooabdaim.bloodbank.R;
import com.midooabdaim.bloodbank.ui.fragment.HomeFragments.HomeFragment.DonationFragment.DonationReqestesFragment;
import com.midooabdaim.bloodbank.ui.fragment.HomeFragments.HomeFragment.PostsFragment.PostRecyclerFragment;

public class FragmentPagerViewAdapter extends FragmentPagerAdapter {
    Activity activity;

    public FragmentPagerViewAdapter(FragmentManager fm, int behavior, Activity activity) {
        super(fm, behavior);
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PostRecyclerFragment();
            case 1:
                return new DonationReqestesFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return activity.getString(R.string.articles);
            case 1:
                return activity.getString(R.string.donation_request);
            default:
                return null;
        }
    }
}
