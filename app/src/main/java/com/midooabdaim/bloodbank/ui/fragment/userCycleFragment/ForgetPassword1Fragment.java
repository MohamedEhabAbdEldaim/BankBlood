package com.midooabdaim.bloodbank.ui.fragment.userCycleFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import com.midooabdaim.bloodbank.Data.Model.User.NewPassword.NewPass;
import com.midooabdaim.bloodbank.R;
import com.midooabdaim.bloodbank.ui.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.midooabdaim.bloodbank.Data.Api.RetrofitClient.getClient;
import static com.midooabdaim.bloodbank.Helper.HelperMethod.customToast;
import static com.midooabdaim.bloodbank.Helper.HelperMethod.replaceFragment;
import static com.midooabdaim.bloodbank.Helper.InternetState.isActive;


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
        replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_frame_fragment, new LoginFragment());
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
            customToast(getActivity(), getString(R.string.empty), true);
            return;
        }

        if (password.isEmpty()) {
            forgetPass1FragmentEtPassword.requestFocus();
            customToast(getActivity(), getString(R.string.empty), true);
            return;
        }


        if (password_confirmation.isEmpty()) {
            forgetPass1FragmentEtConfirmPassword.requestFocus();
            customToast(getActivity(), getString(R.string.empty), true);
            return;
        }
        if (!password_confirmation.equals(password)) {
            forgetPass1FragmentEtConfirmPassword.requestFocus();
            customToast(getActivity(), getString(R.string.password_not_equal), true);
            return;
        }
        if (isActive(getActivity())) {

            getClient().getNewPassword(password, password_confirmation, pin_code, phone).enqueue(new Callback<NewPass>() {
                @Override
                public void onResponse(Call<NewPass> call, Response<NewPass> response) {
                    try {
                        if (response.body().getStatus() == 1) {
                            customToast(getActivity(), response.body().getMsg(), false);
                            replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_frame_fragment, new LoginFragment());
                        } else {
                            customToast(getActivity(), response.body().getMsg(), true);
                        }
                    } catch (Exception e) {
                        customToast(getActivity(), e.getMessage(), true);
                    }
                }

                @Override
                public void onFailure(Call<NewPass> call, Throwable t) {
                    customToast(getActivity(), getString(R.string.failed), true);
                }
            });

        } else {
            customToast(getActivity(), getString(R.string.nointernt), true);

        }


    }


}
