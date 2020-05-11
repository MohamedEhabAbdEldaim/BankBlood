package com.example.boldesystem.View.Fragment.LoginCycleFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.boldesystem.Data.Model.User.registerAndLoginAndEdit.RegisterAndLoginAndEdit;
import com.example.boldesystem.Data.Model.User.registerAndLoginAndEdit.UserData;
import com.example.boldesystem.R;
import com.example.boldesystem.View.Activity.HomeActivity;
import com.example.boldesystem.View.Fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.boldesystem.Data.Api.RetrofitClient.getClient;
import static com.example.boldesystem.Data.Local.SharedPrefrance.REMEMBER;
import static com.example.boldesystem.Data.Local.SharedPrefrance.USER_PASSWORD;
import static com.example.boldesystem.Data.Local.SharedPrefrance.savaData;
import static com.example.boldesystem.Helper.InternetState.isActive;
import static com.example.boldesystem.Helper.MethodHelper.replaceFragment;

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
        switch (view.getId()) {
            case R.id.login_txt_forget_password:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_frame_fragment, new ForgtPasswordFragment());
                Toast.makeText(getActivity().getApplicationContext(), "you pressed Successful", Toast.LENGTH_LONG).show();
                break;
            case R.id.login_bt_sing_in:
                login();
                Toast.makeText(getActivity().getApplicationContext(), "you pressedlogin Successful", Toast.LENGTH_LONG).show();
                break;
            case R.id.login_txt_register:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_frame_fragment, new RegisterFragment());
                Toast.makeText(getActivity(), "you pressed register Successful", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void login() {
        String Phone = loginFragmentEtPhone.getText().toString().trim();
        String Password = loginFragmentEtPassword.getText().toString().trim();
        if (Phone.isEmpty()) {
            loginFragmentEtPhone.requestFocus();
            Toast.makeText(getActivity(), "please enter your phone number", Toast.LENGTH_LONG).show();
            return;
        }
        if (Password.isEmpty()) {
            loginFragmentEtPassword.requestFocus();
            Toast.makeText(getActivity(), "please enter your password", Toast.LENGTH_LONG).show();
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
                            savaData(getActivity(),userData);
                            savaData(getActivity(),REMEMBER,loginCheckBox.isChecked());
                            savaData(getActivity(),USER_PASSWORD, Password);
                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        } else {
                            Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "please enter your password", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<RegisterAndLoginAndEdit> call, Throwable t) {
                    Toast.makeText(getActivity(), "please enter your password", Toast.LENGTH_LONG).show();

                }
            });

        } else {
            Toast.makeText(getActivity(), "please connect the internet", Toast.LENGTH_LONG).show();

        }

    }


}
