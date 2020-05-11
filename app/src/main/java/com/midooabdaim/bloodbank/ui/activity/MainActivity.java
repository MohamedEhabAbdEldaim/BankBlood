package com.midooabdaim.bloodbank.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.midooabdaim.bloodbank.R;
import com.midooabdaim.bloodbank.ui.fragment.splashCycleFragment.SplashFragment;
import com.midooabdaim.bloodbank.ui.fragment.userCycleFragment.LoginFragment;

import static com.midooabdaim.bloodbank.Helper.HelperMethod.replaceFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFragment(getSupportFragmentManager(), R.id.activity_splash_frame_layout_id, new SplashFragment());

    }
}
