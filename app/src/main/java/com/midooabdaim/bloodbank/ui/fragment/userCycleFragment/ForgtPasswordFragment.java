package com.midooabdaim.bloodbank.ui.fragment.userCycleFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import com.midooabdaim.bloodbank.Data.Model.User.ResetPassword.ResetPass;
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


public class ForgtPasswordFragment extends BaseFragment {


    @BindView(R.id.forget_fragment_pass_et_phone)
    EditText forgetFragmentPassEtPhone;

    Unbinder unbinder;

    public ForgtPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intialFragment();
        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);
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
        super.BackPressed();
    }

    @OnClick(R.id.forget_fragment_pass_bt_send)
    public void onViewClicked() {
        restPass();
    }

    private void restPass() {
        String Phone = forgetFragmentPassEtPhone.getText().toString().trim();
        if (Phone.isEmpty()) {
            forgetFragmentPassEtPhone.requestFocus();
            customToast(getActivity(), getString(R.string.empty), true);
            return;
        }

        if (isActive(getActivity())) {
            getClient().getResetPassword(Phone).enqueue(new Callback<ResetPass>() {
                @Override
                public void onResponse(Call<ResetPass> call, Response<ResetPass> response) {
                    try {
                        if (response.body().getStatus() == 1) {
                            customToast(getActivity(), getString(R.string.check_your_phone), false);
                            replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_frame_fragment, new ForgetPassword1Fragment());
                        } else {
                            customToast(getActivity(), response.body().getMsg(), true);
                        }
                    } catch (Exception e) {
                        customToast(getActivity(), e.getMessage(), true);
                    }
                }

                @Override
                public void onFailure(Call<ResetPass> call, Throwable t) {
                    customToast(getActivity(), getString(R.string.failed), true);
                }
            });

        } else {
            customToast(getActivity(), getString(R.string.nointernt), true);
        }

    }
}
