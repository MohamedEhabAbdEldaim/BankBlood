package com.midooabdaim.bloodbank.ui.fragment.HomeFragments.MoreFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.midooabdaim.bloodbank.Helper.CustomDailog;
import com.midooabdaim.bloodbank.R;
import com.midooabdaim.bloodbank.ui.activity.HomeActivity;
import com.midooabdaim.bloodbank.ui.fragment.BaseFragment;
import com.midooabdaim.bloodbank.ui.fragment.HomeFragments.HomeFragment.HomeFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.midooabdaim.bloodbank.Helper.HelperMethod.replaceFragment;


public class MoreFragment extends BaseFragment {
    Unbinder unbinder;
private HomeActivity home;
    public MoreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intialFragment();
        home = (HomeActivity) getActivity();
        home.setHomeAppBarTextViewChange(getString(R.string.more));
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void BackPressed() {
        home.setHomeAppBarUnvisible();
        replaceFragment(home.getSupportFragmentManager(), R.id.home_frame_fragment, new HomeFragment());
    }

    @OnClick({R.id.fragment_more_faviourt_ll, R.id.fragment_more_connect_us_ll, R.id.fragment_more_about_us_ll/*, R.id.fragment_more_app_in_google_ll*/, R.id.fragment_more_setting_notification_ll, R.id.fragment_more_sign_out_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_more_faviourt_ll:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_frame_fragment, new FavouriteFragment());
                break;
            case R.id.fragment_more_connect_us_ll:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_frame_fragment, new ConnectUsFragment());
                break;
            case R.id.fragment_more_about_us_ll:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_frame_fragment, new AboutAppFragment());
                break;
            //case R.id.fragment_more_app_in_google_ll:
              //  break;
            case R.id.fragment_more_setting_notification_ll:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_frame_fragment, new SettingNotificationFragment());
                break;
            case R.id.fragment_more_sign_out_ll:
                CustomDailog dailog = new CustomDailog(getActivity());
                dailog.show();
                break;
        }
    }
}
