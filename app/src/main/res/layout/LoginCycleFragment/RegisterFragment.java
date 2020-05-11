package com.example.boldesystem.View.Fragment.LoginCycleFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boldesystem.Adapter.SpinnerAdpter;
import com.example.boldesystem.Data.Model.User.registerAndLoginAndEdit.RegisterAndLoginAndEdit;
import com.example.boldesystem.Helper.DateTxt;
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
import static com.example.boldesystem.Helper.GeneralReqestesMethodes.getDataSpinners;
import static com.example.boldesystem.Helper.HelperMethod.showCalender;
import static com.example.boldesystem.Helper.InternetState.isActive;
import static com.example.boldesystem.Helper.MethodHelper.replaceFragment;


public class RegisterFragment extends BaseFragment {

    @BindView(R.id.register_fragment_et_name)
    EditText registerFragmentEtName;
    @BindView(R.id.register_fragment_et_email)
    EditText registerFragmentEtEmail;
    @BindView(R.id.register_fragment_et_blood_type)
    Spinner registerFragmentEtBloodType;
    @BindView(R.id.register_fragment_et_muhafza)
    Spinner registerFragmentEtMuhafza;
    @BindView(R.id.register_fragment_et_city)
    Spinner registerFragmentEtCity;
    @BindView(R.id.register_fragment_et_phone)
    EditText registerFragmentEtPhone;
    @BindView(R.id.register_fragment_et_password)
    EditText registerFragmentEtPassword;
    @BindView(R.id.register_fragment_et_confirm_password)
    EditText registerFragmentEtConfirmPassword;
    Unbinder unbinder;
    @BindView(R.id.register_fragment_liner_city)
    LinearLayout registerFragmentLinerCity;
    @BindView(R.id.register_fragment_et_birthday)
    TextView registerFragmentEtBirthday;
    @BindView(R.id.register_fragment_et_last_data_donate)
    TextView registerFragmentEtLastDataDonate;
    @BindView(R.id.register_fragment_ll_back_ground)
    LinearLayout registerFragmentLlBackGround;

    private DateTxt Bid;
    private DateTxt donationData;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intialFragment();
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        unbinder = ButterKnife.bind(this, view);
        registerFragmentLlBackGround.setBackgroundResource(R.drawable.login);
        SpinnerAdpter adpter = new SpinnerAdpter(getActivity());
        getDataSpinners(getClient().getBloodTypes(), adpter, "Boold Types", registerFragmentEtBloodType);
        SpinnerAdpter spinnerAdpter = new SpinnerAdpter(getActivity());
        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    registerFragmentEtCity.setVisibility(View.GONE);
                } else {
                    registerFragmentLinerCity.setVisibility(View.VISIBLE);
                    registerFragmentEtCity.setVisibility(View.VISIBLE);
                    SpinnerAdpter adpters = new SpinnerAdpter(getActivity());
                    getDataSpinners(getClient().getCities(position), adpters, "City", registerFragmentEtCity);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        getDataSpinners(getClient().getGovernorate(), spinnerAdpter, "Governorates", registerFragmentEtMuhafza, listener);
        donationData = new DateTxt("01", "01", "1990", "01-01-1990");
        Bid = new DateTxt("01", "01", "1990", "01-01-1990");

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

    @OnClick(R.id.register_fragment_bt_sing_up)
    public void onViewClicked() {
        register();
    }

    private void register() {
        String name = registerFragmentEtName.getText().toString().trim();
        String email = registerFragmentEtEmail.getText().toString().trim();
        String birth_date = registerFragmentEtBirthday.getText().toString().trim();
        String last_data_donation = registerFragmentEtLastDataDonate.getText().toString().trim();
        int blood_type_id = registerFragmentEtBloodType.getSelectedItemPosition();
        int governorate = registerFragmentEtMuhafza.getSelectedItemPosition();
        int city_id = registerFragmentEtCity.getSelectedItemPosition();
        String phone = registerFragmentEtPhone.getText().toString().trim();
        String password = registerFragmentEtPassword.getText().toString().trim();
        String password_confirmation = registerFragmentEtConfirmPassword.getText().toString().trim();
        if (name.isEmpty()) {
            registerFragmentEtName.requestFocus();
            Toast.makeText(getActivity(), "please enter your name", Toast.LENGTH_LONG).show();
            return;
        }
        if (email.isEmpty()) {
            registerFragmentEtEmail.requestFocus();
            Toast.makeText(getActivity(), "please enter your email", Toast.LENGTH_LONG).show();
            return;
        }
        if (birth_date.isEmpty()) {
            registerFragmentEtBirthday.requestFocus();
            Toast.makeText(getActivity(), "please enter your birthday", Toast.LENGTH_LONG).show();
            return;
        }
        if (last_data_donation.isEmpty()) {
            registerFragmentEtLastDataDonate.requestFocus();
            Toast.makeText(getActivity(), "please enter your last data your donate", Toast.LENGTH_LONG).show();
            return;
        }
        if (blood_type_id == 0) {
            Toast.makeText(getActivity(), "please select your blood type", Toast.LENGTH_LONG).show();
            return;
        }
        if (governorate == 0) {
            Toast.makeText(getActivity(), "please select your governorat", Toast.LENGTH_LONG).show();
            return;
        }

        if (city_id == 0) {
            Toast.makeText(getActivity(), "please select your city", Toast.LENGTH_LONG).show();
            return;
        }
        if (phone.isEmpty()) {
            registerFragmentEtPhone.requestFocus();
            Toast.makeText(getActivity(), "please enter your phone number", Toast.LENGTH_LONG).show();
            return;
        }
        if (password.isEmpty()) {
            registerFragmentEtPassword.requestFocus();
            Toast.makeText(getActivity(), "please enter your password", Toast.LENGTH_LONG).show();
            return;
        }
        if (password_confirmation.isEmpty()) {
            registerFragmentEtConfirmPassword.requestFocus();
            Toast.makeText(getActivity(), "please enter your confirm password", Toast.LENGTH_LONG).show();
            return;
        }
        if (!password_confirmation.equals(password)) {
            registerFragmentEtConfirmPassword.requestFocus();
            Toast.makeText(getActivity(), "password not equal", Toast.LENGTH_LONG).show();
            return;
        }
        if (isActive(getActivity())) {

            getClient().getRegister(name, email, birth_date, city_id, phone, last_data_donation, password, password_confirmation, blood_type_id).enqueue(new Callback<RegisterAndLoginAndEdit>() {
                @Override
                public void onResponse(Call<RegisterAndLoginAndEdit> call, Response<RegisterAndLoginAndEdit> response) {
                    try {
                        if (response.body().getStatus() == 1) {
                            Toast.makeText(getActivity(), "created new account", Toast.LENGTH_LONG).show();
                            replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_frame_fragment, new LoginFragment());
                        } else {
                            Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<RegisterAndLoginAndEdit> call, Throwable t) {
                    Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();

                }
            });

        } else {
            Toast.makeText(getActivity(), "please connect the internet", Toast.LENGTH_LONG).show();
        }

    }


    @OnClick({R.id.register_fragment_et_birthday, R.id.register_fragment_et_last_data_donate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_fragment_et_birthday:
                showCalender(getActivity(), "birthday", registerFragmentEtBirthday, Bid);
                break;
            case R.id.register_fragment_et_last_data_donate:
                showCalender(getActivity(),"last data donation", registerFragmentEtLastDataDonate, donationData);
                break;
        }
    }
}
