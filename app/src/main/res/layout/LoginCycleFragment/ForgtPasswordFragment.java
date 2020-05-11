package com.example.boldesystem.View.Fragment.LoginCycleFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.boldesystem.Data.Model.User.ResetPassword.ResetPass;
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
            Toast.makeText(getActivity(), "please enter your phone number", Toast.LENGTH_LONG).show();
            return;
        }

        if (isActive(getActivity())) {
            getClient().getResetPassword(Phone).enqueue(new Callback<ResetPass>() {
                @Override
                public void onResponse(Call<ResetPass> call, Response<ResetPass> response) {
                    try {
                        if (response.body().getStatus() == 1) {

                            Toast.makeText(getActivity(), "check your phone", Toast.LENGTH_LONG).show();

                            replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_frame_fragment, new ForgetPassword1Fragment());
                        } else {
                            Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResetPass> call, Throwable t) {
                    Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();

                }
            });

        } else {
            Toast.makeText(getActivity(), "please connect the internet", Toast.LENGTH_LONG).show();
        }

    }
}
