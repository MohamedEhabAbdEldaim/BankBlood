package com.example.boldesystem.View.Fragment.LoginCycleFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.boldesystem.Data.Model.User.NewPassword.NewPass;
import com.example.boldesystem.R;
import com.example.boldesystem.View.Fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.boldesystem.Data.Api.RetrofitClient.getClient;
import static com.example.boldesystem.Helper.InternetState.isActive;
import static com.example.boldesystem.Helper.MethodHelper.replaceFragment;


public class ForgetPassword1Fragment extends BaseFragment {


    @BindView(R.id.forget_pass1_fragment_et_code)
    EditText forgetPass1FragmentEtCode;
    @BindView(R.id.forget_pass1_fragment_et_password)
    EditText forgetPass1FragmentEtPassword;
    @BindView(R.id.forget_pass1_fragment_et_confirm_password)
    EditText forgetPass1FragmentEtConfirmPassword;

    Unbinder unbinder;
    public String phone;

    public ForgetPassword1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intialFragment();
        View view = inflater.inflate(R.layout.fragment_forget_password1, container, false);
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
        replaceFragment(getActivity().getSupportFragmentManager(),R.id.activity_user_frame_fragment,new LoginFragment());
    }

    @OnClick(R.id.forget_pass1_bt_ok)
    public void onViewClicked() {
        changePassword();
    }




    private void changePassword() {

        String pin_code = forgetPass1FragmentEtCode.getText().toString().trim();
        String password = forgetPass1FragmentEtPassword.getText().toString().trim();
        String password_confirmation = forgetPass1FragmentEtConfirmPassword.getText().toString().trim();

        if (pin_code.isEmpty()) {
            forgetPass1FragmentEtCode.requestFocus();
            Toast.makeText(getActivity(), "check pin code", Toast.LENGTH_LONG).show();

            return;
        }

        if (password.isEmpty()) {
            forgetPass1FragmentEtPassword.requestFocus();
            Toast.makeText(getActivity(), "enter new password", Toast.LENGTH_LONG).show();
            return;
        }


        if (password_confirmation.isEmpty()) {
            forgetPass1FragmentEtConfirmPassword.requestFocus();
            Toast.makeText(getActivity(), "enter confirmation password", Toast.LENGTH_LONG).show();

            return;
        }
        if (!password_confirmation.equals(password)) {
            forgetPass1FragmentEtConfirmPassword.requestFocus();
            Toast.makeText(getActivity(), "password not equal", Toast.LENGTH_LONG).show();
            return;
        }
        if (isActive(getActivity())) {

            getClient().getNewPassword(password,password_confirmation,pin_code,phone).enqueue(new Callback<NewPass>() {
                @Override
                public void onResponse(Call<NewPass> call, Response<NewPass> response) {
                    try {
                        if (response.body().getStatus() == 1) {

                            Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                            replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_frame_fragment, new LoginFragment());
                        } else {
                            Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<NewPass> call, Throwable t) {
                    Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();

                }
            });

        } else {
            Toast.makeText(getActivity(), "please connect the internet", Toast.LENGTH_LONG).show();
        }




    }




}
