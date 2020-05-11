package com.midooabdaim.bloodbank.ui.fragment.HomeFragments.MoreFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.midooabdaim.bloodbank.Data.Model.User.registerAndLoginAndEdit.UserData;
import com.midooabdaim.bloodbank.Data.Model.setting.Setting;
import com.midooabdaim.bloodbank.R;
import com.midooabdaim.bloodbank.ui.activity.HomeActivity;
import com.midooabdaim.bloodbank.ui.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.midooabdaim.bloodbank.Data.Api.RetrofitClient.getClient;
import static com.midooabdaim.bloodbank.Data.Local.SharedPrefrance.loadData;
import static com.midooabdaim.bloodbank.Helper.HelperMethod.customToast;
import static com.midooabdaim.bloodbank.Helper.InternetState.isActive;


public class AboutAppFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.about_fragment_tv_about_app)
    TextView aboutFragmentTvAboutApp;
    private UserData userData;

    public AboutAppFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intialFragment();
        View view = inflater.inflate(R.layout.fragment_about_app, container, false);
        unbinder = ButterKnife.bind(this, view);
        HomeActivity home = (HomeActivity) getActivity();
        home.setHomeAppBarTextViewChange(getString(R.string.about_app));
        userData = loadData(getActivity());
        getAbout();
        return view;

    }

    private void getAbout() {
        if (isActive(getActivity())) {
            getClient().getSettings(userData.getApiToken()).enqueue(new Callback<Setting>() {
                @Override
                public void onResponse(Call<Setting> call, Response<Setting> response) {
                    try {
                        if (response.body().getStatus() == 1) {
                            aboutFragmentTvAboutApp.setText(response.body().getData().getAboutApp());
                        } else {
                            customToast(getActivity(), response.body().getMsg(), true);
                        }

                    } catch (Exception e) {
                        customToast(getActivity(), e.getMessage(), true);

                    }
                }


                @Override
                public void onFailure(Call<Setting> call, Throwable t) {
                    customToast(getActivity(), getString(R.string.failed), true);

                }
            });

        } else {
            customToast(getActivity(), getString(R.string.nointernt), true);
        }

    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void BackPressed() {
        super.BackPressed();
    }
}
