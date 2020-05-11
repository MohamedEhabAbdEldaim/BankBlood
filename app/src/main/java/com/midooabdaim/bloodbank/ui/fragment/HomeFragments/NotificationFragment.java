package com.midooabdaim.bloodbank.ui.fragment.HomeFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.midooabdaim.bloodbank.Adapter.NotificationRecyclerAdapter;
import com.midooabdaim.bloodbank.Data.Model.Notification.Notification;
import com.midooabdaim.bloodbank.Data.Model.Notification.NotificationData;
import com.midooabdaim.bloodbank.Data.Model.User.registerAndLoginAndEdit.UserData;
import com.midooabdaim.bloodbank.Helper.OnEndLess;
import com.midooabdaim.bloodbank.R;
import com.midooabdaim.bloodbank.ui.activity.HomeActivity;
import com.midooabdaim.bloodbank.ui.fragment.BaseFragment;
import com.midooabdaim.bloodbank.ui.fragment.HomeFragments.HomeFragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.midooabdaim.bloodbank.Data.Api.RetrofitClient.getClient;
import static com.midooabdaim.bloodbank.Data.Local.SharedPrefrance.loadData;
import static com.midooabdaim.bloodbank.Helper.HelperMethod.customToast;
import static com.midooabdaim.bloodbank.Helper.HelperMethod.replaceFragment;
import static com.midooabdaim.bloodbank.Helper.InternetState.isActive;


public class NotificationFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.fragment_notification_rv)
    RecyclerView fragmentNotificationRv;
    @BindView(R.id.fragment_notification_tv_no_item)
    TextView fragmentNotificationTvNoItem;
    private LinearLayoutManager liner;
    private OnEndLess onEndLess;
    private NotificationRecyclerAdapter recyclerAdapter;
    private List<NotificationData> notficationData = new ArrayList<>();
    private UserData userData;
    private int max = 0;
    private HomeActivity home;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intialFragment();
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        unbinder = ButterKnife.bind(this, view);
        home = (HomeActivity) getActivity();
        home.setHomeAppBarTextViewChange(getString(R.string.notification));
        userData = loadData(getActivity());
        intialRecycle();
        if (notficationData.size() == 0) {
            getNotification(1);
        }
        return view;
    }

    private void intialRecycle() {
        liner = new LinearLayoutManager(getActivity());
        fragmentNotificationRv.setLayoutManager(liner);
        onEndLess = new OnEndLess(liner, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= max) {
                    if (max != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        getNotification(current_page);

                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }

            }
        };
        fragmentNotificationRv.addOnScrollListener(onEndLess);
        recyclerAdapter = new NotificationRecyclerAdapter(getActivity(), getActivity(), notficationData);
        fragmentNotificationRv.setAdapter(recyclerAdapter);
    }

    private void getNotification(int current_page) {
        if (isActive(getActivity())) {
            getClient().getListNotification(userData.getApiToken(), current_page).enqueue(new Callback<Notification>() {
                @Override
                public void onResponse(Call<Notification> call, Response<Notification> response) {
                    try {


                        if (response.body().getStatus() == 1) {
                            if (current_page == 1) {
                                if (response.body().getData().getTotal() > 0) {
                                    fragmentNotificationTvNoItem.setVisibility(View.GONE);
                                } else {
                                    fragmentNotificationTvNoItem.setVisibility(View.VISIBLE);
                                    fragmentNotificationTvNoItem.setText(getString(R.string.No_Item));
                                }

                            }

                            max = response.body().getData().getLastPage();
                            notficationData.addAll(response.body().getData().getData());
                            recyclerAdapter.notifyDataSetChanged();

                        } else {
                            customToast(getActivity(),response.body().getMsg(),true);

                        }

                    } catch (Exception e) {
                        customToast(getActivity(),e.getMessage(),true);
                    }

                }

                @Override
                public void onFailure(Call<Notification> call, Throwable t) {
                    customToast(getActivity(),getString(R.string.failed),true);
                }
            });

        } else {
            customToast(getActivity(),getString(R.string.nointernt),true);
        }

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
}
