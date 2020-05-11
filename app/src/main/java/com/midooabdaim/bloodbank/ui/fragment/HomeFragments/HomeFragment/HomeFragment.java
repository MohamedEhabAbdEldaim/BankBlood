package com.midooabdaim.bloodbank.ui.fragment.HomeFragments.HomeFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.midooabdaim.bloodbank.Adapter.FragmentPagerViewAdapter;
import com.midooabdaim.bloodbank.R;
import com.midooabdaim.bloodbank.ui.activity.HomeActivity;
import com.midooabdaim.bloodbank.ui.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.midooabdaim.bloodbank.Helper.HelperMethod.replaceFragment;


public class HomeFragment extends BaseFragment {
    @BindView(R.id.home_tab_layout_id)
    TabLayout homeTabLayoutId;
    @BindView(R.id.home_view_pager)
    ViewPager homeViewPager;

    Unbinder unbinder;
    private HomeActivity home;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intialFragment();
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        home = (HomeActivity) getActivity();
        home.setHomeAppBarUnvisible();
        FragmentPagerViewAdapter adapter = new FragmentPagerViewAdapter(getChildFragmentManager(), 1,getActivity());
        homeViewPager.setAdapter(adapter);
        homeTabLayoutId.setupWithViewPager(homeViewPager);
        return view;
    }

    @Override
    public void BackPressed() {
        getActivity().finish();
    }

    @OnClick(R.id.home_float_bt_add_reqest)
    public void onViewClicked() {
        home.setHomeAppBarTextViewChange("donation  request");
        replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_frame_fragment, new CreateRequestDonationFragment());
    }
}
