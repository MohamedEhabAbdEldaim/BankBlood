package com.midooabdaim.bloodbank.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.midooabdaim.bloodbank.R;
import com.midooabdaim.bloodbank.ui.fragment.userCycleFragment.LoginFragment;

import static com.midooabdaim.bloodbank.Helper.HelperMethod.replaceFragment;

public class UserRecycleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_recycle);
        replaceFragment(getSupportFragmentManager(), R.id.activity_user_frame_fragment, new LoginFragment());
    }
}
