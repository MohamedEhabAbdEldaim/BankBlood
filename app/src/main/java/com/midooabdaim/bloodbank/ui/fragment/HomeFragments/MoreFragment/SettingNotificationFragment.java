package com.midooabdaim.bloodbank.ui.fragment.HomeFragments.MoreFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.midooabdaim.bloodbank.Adapter.SettingNotificationRecyclerAdapter;
import com.midooabdaim.bloodbank.Data.Model.GenralReqest.GenralReqest;
import com.midooabdaim.bloodbank.Data.Model.NotificationStting.NotificationStting;
import com.midooabdaim.bloodbank.Data.Model.User.registerAndLoginAndEdit.UserData;
import com.midooabdaim.bloodbank.R;
import com.midooabdaim.bloodbank.ui.activity.HomeActivity;
import com.midooabdaim.bloodbank.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

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


public class SettingNotificationFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.fragment_setting_notification_tv)
    TextView fragmentSettingNotificationTv;
    @BindView(R.id.fragment_setting_notification_im_add)
    ImageView fragmentSettingNotificationImAdd;
    @BindView(R.id.fragment_setting_notification_rv_blood_type)
    RecyclerView fragmentSettingNotificationRvBloodType;
    @BindView(R.id.fragment_setting_notification_im_add_gover)
    ImageView fragmentSettingNotificationImAddGover;
    @BindView(R.id.fragment_setting_notification_rv_gover)
    RecyclerView fragmentSettingNotificationRvGover;
    private UserData userData;
    private List<String> listBlood = new ArrayList<>();
    private List<String> listgover = new ArrayList<>();
    private GridLayoutManager gridLayoutManager;
    private SettingNotificationRecyclerAdapter adapterBlood;
    private SettingNotificationRecyclerAdapter adapterGover;
    private GridLayoutManager gridLayoutManager1;


    public SettingNotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intialFragment();
        View view = inflater.inflate(R.layout.fragment_setting_notification, container, false);
        unbinder = ButterKnife.bind(this, view);
        HomeActivity home = (HomeActivity) getActivity();
        home.setHomeAppBarTextViewChange(getString(R.string.Notification_Setting));
        userData = loadData(getActivity());
        initialRcView();
        getSettingNotification();

        return view;
    }

    private void initialRcView() {
        gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        gridLayoutManager1 = new GridLayoutManager(getActivity(), 3);
        fragmentSettingNotificationRvBloodType.setLayoutManager(gridLayoutManager);
        fragmentSettingNotificationRvBloodType.setHasFixedSize(true);
        fragmentSettingNotificationRvGover.setLayoutManager(gridLayoutManager1);
        fragmentSettingNotificationRvGover.setHasFixedSize(true);
    }

    private void getSettingNotification() {
        getClient().getNotificationSetting(userData.getApiToken()).enqueue(new Callback<NotificationStting>() {
            @Override
            public void onResponse(Call<NotificationStting> call, Response<NotificationStting> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        listBlood = response.body().getData().getBloodTypes();
                        listgover = response.body().getData().getGovernorates();
                        getBlood();
                        getGover();
                    }

                } catch (Exception e) {


                }
            }

            @Override
            public void onFailure(Call<NotificationStting> call, Throwable t) {

            }
        });
    }

    private void getBlood() {
        getClient().getBloodTypes().enqueue(new Callback<GenralReqest>() {
            @Override
            public void onResponse(Call<GenralReqest> call, Response<GenralReqest> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        adapterBlood = new SettingNotificationRecyclerAdapter(getActivity(), getActivity(), response.body().getData(), listBlood);
                        fragmentSettingNotificationRvBloodType.setAdapter(adapterBlood);
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<GenralReqest> call, Throwable t) {

            }
        });
    }

    private void getGover() {
        getClient().getGovernorate().enqueue(new Callback<GenralReqest>() {
            @Override
            public void onResponse(Call<GenralReqest> call, Response<GenralReqest> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        adapterGover = new SettingNotificationRecyclerAdapter(getActivity(), getActivity(), response.body().getData(), listgover);
                        fragmentSettingNotificationRvGover.setAdapter(adapterGover);
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<GenralReqest> call, Throwable t) {

            }
        });

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

    @OnClick({R.id.fragment_setting_notification_ll_blood, R.id.fragment_setting_notification_ll_gover, R.id.register_fragment_bt_sing_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_setting_notification_ll_blood:
                Visible(fragmentSettingNotificationRvBloodType);
                break;
            case R.id.fragment_setting_notification_ll_gover:
                Visible(fragmentSettingNotificationRvGover);
                break;
            case R.id.register_fragment_bt_sing_up:
                send();
                break;
        }
    }

    private void send() {

        getClient().changeNotificationSetting(userData.getApiToken(), adapterGover.id, adapterBlood.id).enqueue(new Callback<NotificationStting>() {
            @Override
            public void onResponse(Call<NotificationStting> call, Response<NotificationStting> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        customToast(getActivity(),response.body().getMsg(),false);
                    } else {
                        customToast(getActivity(),response.body().getMsg(),true);
                    }

                } catch (Exception e) {
                    customToast(getActivity(),e.getMessage(),true);


                }
            }

            @Override
            public void onFailure(Call<NotificationStting> call, Throwable t) {
                customToast(getActivity(),getString(R.string.failed),false);

            }
        });


    }

    private void Visible(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }
}
