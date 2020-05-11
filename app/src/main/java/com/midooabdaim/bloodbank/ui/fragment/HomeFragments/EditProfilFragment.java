package com.midooabdaim.bloodbank.ui.fragment.HomeFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.midooabdaim.bloodbank.Adapter.SpinnerAdpter;
import com.midooabdaim.bloodbank.Data.Model.User.registerAndLoginAndEdit.RegisterAndLoginAndEdit;
import com.midooabdaim.bloodbank.Data.Model.User.registerAndLoginAndEdit.UserData;
import com.midooabdaim.bloodbank.Helper.DateTxt;
import com.midooabdaim.bloodbank.R;
import com.midooabdaim.bloodbank.ui.activity.HomeActivity;
import com.midooabdaim.bloodbank.ui.fragment.BaseFragment;
import com.midooabdaim.bloodbank.ui.fragment.HomeFragments.HomeFragment.HomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.midooabdaim.bloodbank.Data.Api.RetrofitClient.getClient;
import static com.midooabdaim.bloodbank.Data.Local.SharedPrefrance.USER_PASSWORD;
import static com.midooabdaim.bloodbank.Data.Local.SharedPrefrance.loadData;
import static com.midooabdaim.bloodbank.Data.Local.SharedPrefrance.savaData;
import static com.midooabdaim.bloodbank.Helper.GeneralReqestesMethodes.getDataSpinnersWithSelect;
import static com.midooabdaim.bloodbank.Helper.HelperMethod.convertStringToDateTxtModel;
import static com.midooabdaim.bloodbank.Helper.HelperMethod.customToast;
import static com.midooabdaim.bloodbank.Helper.HelperMethod.disappearKeypad;
import static com.midooabdaim.bloodbank.Helper.HelperMethod.replaceFragment;
import static com.midooabdaim.bloodbank.Helper.HelperMethod.showCalender;
import static com.midooabdaim.bloodbank.Helper.InternetState.isActive;


public class EditProfilFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.register_fragment_text_view_change)
    TextView registerFragmentTextViewChange;
    @BindView(R.id.register_fragment_et_name)
    EditText registerFragmentEtName;
    @BindView(R.id.register_fragment_et_email)
    EditText registerFragmentEtEmail;
    @BindView(R.id.register_fragment_et_birthday)
    TextView registerFragmentEtBirthday;
    @BindView(R.id.register_fragment_et_last_data_donate)
    TextView registerFragmentEtLastDataDonate;
    @BindView(R.id.register_fragment_et_blood_type)
    Spinner registerFragmentEtBloodType;
    @BindView(R.id.register_fragment_et_muhafza)
    Spinner registerFragmentEtMuhafza;
    @BindView(R.id.register_fragment_et_city)
    Spinner registerFragmentEtCity;
    @BindView(R.id.register_fragment_liner_city)
    LinearLayout registerFragmentLinerCity;
    @BindView(R.id.register_fragment_et_phone)
    EditText registerFragmentEtPhone;
    @BindView(R.id.register_fragment_et_password)
    EditText registerFragmentEtPassword;
    @BindView(R.id.register_fragment_et_confirm_password)
    EditText registerFragmentEtConfirmPassword;
    @BindView(R.id.register_fragment_bt_sing_up)
    Button registerFragmentBtSingUp;
    private DateTxt Bid;
    private DateTxt donationData;
    private UserData userData;
    private HomeActivity home;

    public EditProfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intialFragment();
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        unbinder = ButterKnife.bind(this, view);
        home = (HomeActivity) getActivity();
        home.setHomeAppBarTextViewChange(getString(R.string.edit_profile));
        registerFragmentTextViewChange.setVisibility(View.GONE);
        registerFragmentBtSingUp.setBackgroundResource(R.drawable.button_shaprede);
        registerFragmentBtSingUp.setText(getString(R.string.change));
        userData = loadData(getActivity());
        setData();
        return view;
    }

    private void setData() {
        registerFragmentEtName.setText(userData.getClient().getName());
        registerFragmentEtEmail.setText(userData.getClient().getEmail());
        registerFragmentEtBirthday.setText(userData.getClient().getBirthDate());
        registerFragmentEtLastDataDonate.setText(userData.getClient().getDonationLastDate());
        registerFragmentEtPhone.setText(userData.getClient().getPhone());
        registerFragmentEtPassword.setText(loadData(getActivity(), USER_PASSWORD));
        registerFragmentEtConfirmPassword.setText(loadData(getActivity(), USER_PASSWORD));
        Bid = convertStringToDateTxtModel(userData.getClient().getBirthDate());
        donationData = convertStringToDateTxtModel(userData.getClient().getDonationLastDate());
        SpinnerAdpter adpter = new SpinnerAdpter(getActivity());
        getDataSpinnersWithSelect(getClient().getBloodTypes(), adpter, getString(R.string.bloodtype), registerFragmentEtBloodType, userData.getClient().getBloodType().getId());
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
                    getDataSpinnersWithSelect(getClient().getCities(position), adpters, getString(R.string.city), registerFragmentEtCity,
                            userData.getClient().getCity().getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        getDataSpinnersWithSelect(getClient().getGovernorate(), spinnerAdpter, getString(R.string.governorat), registerFragmentEtMuhafza, listener
                , userData.getClient().getCity().getGovernorate().getId());

        SpinnerAdpter adpterss = new SpinnerAdpter(getActivity());
        getDataSpinnersWithSelect(getClient().getBloodTypes(), adpterss, getString(R.string.bloodtype), registerFragmentEtBloodType,
                userData.getClient().getBloodType().getId());
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void BackPressed() {
        home.setHomeAppBarUnvisible();
        replaceFragment(home.getSupportFragmentManager(), R.id.home_frame_fragment, new HomeFragment());
    }

    @OnClick({R.id.register_fragment_et_birthday, R.id.register_fragment_et_last_data_donate, R.id.register_fragment_bt_sing_up})
    public void onViewClicked(View view) {
        disappearKeypad(getActivity(), view);
        switch (view.getId()) {
            case R.id.register_fragment_et_birthday:
                showCalender(getActivity(), getString(R.string.birthday), registerFragmentEtBirthday, Bid);

                break;
            case R.id.register_fragment_et_last_data_donate:
                showCalender(getActivity(), getString(R.string.last_data_donation), registerFragmentEtLastDataDonate, donationData);
                break;
            case R.id.register_fragment_bt_sing_up:
                getProfial();
                break;
        }
    }

    private void getProfial() {
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
            customToast(getActivity(), getString(R.string.empty), true);
            return;
        }
        if (email.isEmpty()) {
            registerFragmentEtEmail.requestFocus();
            customToast(getActivity(), getString(R.string.empty), true);
            return;
        }
        if (birth_date.isEmpty()) {
            registerFragmentEtBirthday.requestFocus();
            customToast(getActivity(), getString(R.string.empty), true);
            return;
        }
        if (last_data_donation.isEmpty()) {
            registerFragmentEtLastDataDonate.requestFocus();
            customToast(getActivity(), getString(R.string.empty), true);
            return;
        }
        if (blood_type_id == 0) {
            customToast(getActivity(), getString(R.string.selectbloodtype), true);
            return;
        }
        if (governorate == 0) {
            customToast(getActivity(), getString(R.string.selectgover), true);
            return;
        }
        if (city_id == 0) {
            customToast(getActivity(), getString(R.string.selectcity), true);
            return;
        }
        if (phone.isEmpty()) {
            registerFragmentEtPhone.requestFocus();
            customToast(getActivity(), getString(R.string.empty), true);
            return;
        }
        if (password.isEmpty()) {
            registerFragmentEtPassword.requestFocus();
            customToast(getActivity(), getString(R.string.empty), true);
            return;
        }
        if (password_confirmation.isEmpty()) {
            registerFragmentEtConfirmPassword.requestFocus();
            customToast(getActivity(), getString(R.string.empty), true);
            return;
        }
        if (!password_confirmation.equals(password)) {
            registerFragmentEtConfirmPassword.requestFocus();
            customToast(getActivity(), getString(R.string.password_not_equal), true);
            return;
        }

        if (isActive(getActivity())) {
            getClient().getProfile(name, email, birth_date, city_id, phone, last_data_donation, blood_type_id, password, password_confirmation, userData.getApiToken()).enqueue(new Callback<RegisterAndLoginAndEdit>() {
                @Override
                public void onResponse(Call<RegisterAndLoginAndEdit> call, Response<RegisterAndLoginAndEdit> response) {

                    try {
                        if (response.body().getStatus() == 1) {
                            UserData newUserData = new UserData();
                            newUserData.setClient(response.body().getData().getClient());
                            newUserData.setApiToken(userData.getApiToken());
                            savaData(getActivity(), newUserData);
                            customToast(getActivity(),response.body().getMsg(), false);
                        } else {
                            customToast(getActivity(),response.body().getMsg(), true);
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
