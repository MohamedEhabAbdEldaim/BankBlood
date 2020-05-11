package com.midooabdaim.bloodbank.ui.fragment.HomeFragments.MoreFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.midooabdaim.bloodbank.Data.Model.User.registerAndLoginAndEdit.UserData;
import com.midooabdaim.bloodbank.Data.Model.contactUs.ContactUs;
import com.midooabdaim.bloodbank.Data.Model.setting.Setting;
import com.midooabdaim.bloodbank.R;
import com.midooabdaim.bloodbank.ui.activity.HomeActivity;
import com.midooabdaim.bloodbank.ui.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.midooabdaim.bloodbank.Data.Api.RetrofitClient.getClient;
import static com.midooabdaim.bloodbank.Data.Local.SharedPrefrance.loadData;
import static com.midooabdaim.bloodbank.Helper.HelperMethod.customToast;
import static com.midooabdaim.bloodbank.Helper.InternetState.isActive;


public class ConnectUsFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.connect_us_fragment_phone)
    TextView connectUsFragmentPhone;
    @BindView(R.id.connect_us_fragment_email)
    TextView connectUsFragmentEmail;
    @BindView(R.id.connect_us_et_title)
    EditText connectUsEtTitle;
    @BindView(R.id.connect_us_et_massage)
    EditText connectUsEtMassage;
    private UserData userData;

    public ConnectUsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intialFragment();
        View view = inflater.inflate(R.layout.fragment_connect_us, container, false);
        unbinder = ButterKnife.bind(this, view);
        HomeActivity home = (HomeActivity) getActivity();
        home.setHomeAppBarTextViewChange(getString(R.string.connect_us));
        userData = loadData(getActivity());
        getData();
        return view;
    }

    private void getData() {
        if (isActive(getActivity())) {
            getClient().getSettings(userData.getApiToken()).enqueue(new Callback<Setting>() {
                @Override
                public void onResponse(Call<Setting> call, Response<Setting> response) {
                    try {
                        if (response.body().getStatus() == 1) {
                            connectUsFragmentPhone.setText(response.body().getData().getPhone());
                            connectUsFragmentEmail.setText(response.body().getData().getEmail());
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

    @OnClick({R.id.connect_us_fragment_img_facebook, R.id.connect_us_fragment_img_instgram, R.id.connect_us_fragment_img_twitter, R.id.connect_us_fragment_img_youtube, R.id.donation_fragment_bt_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.connect_us_fragment_img_facebook:
                break;
            case R.id.connect_us_fragment_img_instgram:
                break;
            case R.id.connect_us_fragment_img_twitter:
                break;
            case R.id.connect_us_fragment_img_youtube:
                break;
            case R.id.donation_fragment_bt_send:
                send();
                break;
        }
    }

    private void send() {
        String title = connectUsEtTitle.getText().toString().trim();
        String message = connectUsEtMassage.getText().toString().trim();
        if (title.isEmpty()) {
            customToast(getActivity(), getString(R.string.empty), true);
            connectUsEtTitle.requestFocus();
            return;
        }
        if (message.isEmpty()) {
            customToast(getActivity(), getString(R.string.empty), true);
            connectUsEtMassage.requestFocus();
            return;
        }
        if (isActive(getActivity())) {
            getClient().getContactUs(title, message, userData.getApiToken()).enqueue(new Callback<ContactUs>() {
                @Override
                public void onResponse(Call<ContactUs> call, Response<ContactUs> response) {
                    try {
                        if (response.body().getStatus() == 1) {
                            customToast(getActivity(), response.body().getMsg(), false);
                        } else {
                            customToast(getActivity(), response.body().getMsg(), true);
                        }
                    } catch (Exception e) {

                        customToast(getActivity(), e.getMessage(), true);
                    }

                }

                @Override
                public void onFailure(Call<ContactUs> call, Throwable t) {
                    customToast(getActivity(), getString(R.string.failed), true);
                }
            });
        } else {
            customToast(getActivity(), getString(R.string.nointernt), true);
        }

    }

}
