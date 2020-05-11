package com.midooabdaim.bloodbank.ui.fragment.splashCycleFragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.midooabdaim.bloodbank.R;
import com.midooabdaim.bloodbank.ui.activity.HomeActivity;
import com.midooabdaim.bloodbank.ui.fragment.BaseFragment;
import com.midooabdaim.bloodbank.ui.fragment.userCycleFragment.LoginFragment;

import static com.midooabdaim.bloodbank.Data.Local.SharedPrefrance.REMEMBER;
import static com.midooabdaim.bloodbank.Data.Local.SharedPrefrance.loadData;
import static com.midooabdaim.bloodbank.Data.Local.SharedPrefrance.loadaData;
import static com.midooabdaim.bloodbank.Helper.HelperMethod.replaceFragment;


public class SplashFragment extends BaseFragment {


    private static final long SPLASH_DISPLAY_LENGTH = 3000;

    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intialFragment();
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                if (loadaData(getActivity(), REMEMBER) && loadData(getActivity()) != null) {
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                } else {
                    replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_splash_frame_layout_id, new SliderFragment());
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
        return view;
    }


    @Override
    public void BackPressed() {
        getActivity().finish();
    }
}
