package com.midooabdaim.bloodbank.ui.fragment.userCycleFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.midooabdaim.bloodbank.Data.Model.User.registerAndLoginAndEdit.RegisterAndLoginAndEdit;
import com.midooabdaim.bloodbank.Data.Model.User.registerAndLoginAndEdit.UserData;
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
import static com.midooabdaim.bloodbank.Data.Local.SharedPrefrance.REMEMBER;
import static com.midooabdaim.bloodbank.Data.Local.SharedPrefrance.USER_PASSWORD;
import static com.midooabdaim.bloodbank.Data.Local.SharedPrefrance.savaData;
import static com.midooabdaim.bloodbank.Helper.HelperMethod.customToast;
import static com.midooabdaim.bloodbank.Helper.HelperMethod.disappearKeypad;
import static com.midooabdaim.bloodbank.Helper.HelperMethod.replaceFragment;
import static com.midooabdaim.bloodbank.Helper.InternetState.isActive;


public class LoginFragment extends BaseFragment {


    @BindView(R.id.login_fragment_et_phone)
    EditText loginFragmentEtPhone;
    @BindView(R.id.login_fragment_et_password)
    EditText loginFragmentEtPassword;
    @BindView(R.id.login_check_box)
    CheckBox loginCheckBox;

    Unbinder unbinder;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intialFragment();
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void BackPressed() {
        getActivity().finish();
    }

    @OnClick({R.id.login_txt_forget_password, R.id.login_bt_sing_in, R.id.login_txt_register})
    public void onViewClicked(View view) {
        disappearKeypad(getActivity(), view);
        switch (view.getId()) {
            case R.id.login_txt_forget_password:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_frame_fragment, new ForgtPasswordFragment());
                break;
            case R.id.login_bt_sing_in:
                login();
                break;
            case R.id.login_txt_register:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_frame_fragment, new RegisterFragment());
                break;
        }
    }

    private void login() {
        String Phone = loginFragmentEtPhone.getText().toString().trim();
        String Password = loginFragmentEtPassword.getText().toString().trim();
        if (Phone.isEmpty()) {
            loginFragmentEtPhone.requestFocus();
            customToast(getActivity(), getString(R.string.empty), true);
            return;
        }
        if (Password.isEmpty()) {
            loginFragmentEtPassword.requestFocus();
            customToast(getActivity(), getString(R.string.empty), true);
            return;
        }

        if (isActive(getActivity())) {
            getClient().getUser(Phone, Password).enqueue(new Callback<RegisterAndLoginAndEdit>() {
                @Override
                public void onResponse(Call<RegisterAndLoginAndEdit> call, Response<RegisterAndLoginAndEdit> response) {
                    try {
                        if (response.body().getStatus() == 1) {
                            UserData userData = new UserData();
                            userData.setClient(response.body().getData().getClient());
                            userData.setApiToken(response.body().getData().getApiToken());
                            savaData(getActivity(), userData);
                            savaData(getActivity(), REMEMBER, loginCheckBox.isChecked());
                            savaData(getActivity(), USER_PASSWORD, Password);
                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        } else {
                            customToast(getActivity(), response.body().getMsg(), true);
                        }
                    } catch (Exception e) {
                        customToast(getActivity(), e.getMessage(), true);
                    }
                }

                @Override
                public void onFailure(Call<RegisterAndLoginAndEdit> call, Throwable t) {
                    customToast(getActivity(), getString(R.string.failed), true);
                }
            });

        } else {
            customToast(getActivity(), getString(R.string.nointernt), true);
        }

    }


}
