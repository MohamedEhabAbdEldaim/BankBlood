package com.midooabdaim.bloodbank.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.midooabdaim.bloodbank.Data.Model.User.registerAndLoginAndEdit.UserData;
import com.midooabdaim.bloodbank.Data.Model.donationDetails.DonationData;
import com.midooabdaim.bloodbank.Data.Model.donationDetails.DonationDetailsAndRequest;
import com.midooabdaim.bloodbank.R;
import com.midooabdaim.bloodbank.ui.activity.HomeActivity;
import com.midooabdaim.bloodbank.ui.fragment.HomeFragments.HomeFragment.DonationFragment.DonationDetailsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.midooabdaim.bloodbank.Data.Api.RetrofitClient.getClient;
import static com.midooabdaim.bloodbank.Data.Local.SharedPrefrance.loadData;
import static com.midooabdaim.bloodbank.Helper.HelperMethod.replaceFragment;
import static com.midooabdaim.bloodbank.Helper.InternetState.isActive;


public class DonationRecyclerViewAdpter extends RecyclerView.Adapter<DonationRecyclerViewAdpter.viewHolder> {


    private Context context;
    private Activity activity;
    private UserData userData;
    private List<DonationData> donationData = new ArrayList<>();
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private String lang;

    public DonationRecyclerViewAdpter(Context context, Activity activity, List<DonationData> donationData) {
        this.context = context;
        this.activity = activity;
        this.donationData = donationData;
        userData = loadData(activity);
        lang = context.getString(R.string.lang);
        viewBinderHelper.setOpenOnlyOne(true);
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_donation, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        holder.setIsRecyclable(false);
        setData(holder, position);
        setSwip(holder,position);
    }

    private void setData(viewHolder holder, int position) {
        try {
            holder.position = position;
            holder.donationItemTvBloodType.setText(donationData.get(position).getBloodType().getName());
            holder.donationItemTvPatientName.setText(context.getString(R.string.patientname) + " : " + donationData.get(position).getPatientName());
            holder.donationItemTvHospitalName.setText(context.getString(R.string.hospital) + " : " + donationData.get(position).getHospitalName());
            holder.donationItemTvCityName.setText(context.getString(R.string.city) + " : " + donationData.get(position).getCity().getName());

        } catch (Exception e) {

        }
    }
    private void setSwip(viewHolder holder, int position) {
        try {
            holder.swipeRevealLayout.computeScroll();
            if (lang.equals("ar")) {
                holder.swipeRevealLayout.setDragEdge(SwipeRevealLayout.DRAG_EDGE_LEFT);
            } else {
                holder.swipeRevealLayout.setDragEdge(SwipeRevealLayout.DRAG_EDGE_RIGHT);
            }
            String id = String.valueOf(donationData.get(position).getId());
            viewBinderHelper.bind(holder.swipeRevealLayout,id);
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewBinderHelper.openLayout(id);
                    holder.swipeRevealLayout.computeScroll();
                }
            });
        } catch (Exception e) {

        }
    }


    @Override
    public int getItemCount() {
        return donationData.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.donation_item_tv_blood_type)
        TextView donationItemTvBloodType;
        @BindView(R.id.donation_item_tv_patient_name)
        TextView donationItemTvPatientName;
        @BindView(R.id.donation_item_tv_hospital_name)
        TextView donationItemTvHospitalName;
        @BindView(R.id.donation_item_tv_city_name)
        TextView donationItemTvCityName;
        @BindView(R.id.swipe_reveal_layout)
        SwipeRevealLayout swipeRevealLayout;
        private View view;
        private int position;

        public viewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);

        }

        @OnClick({R.id.donation_item_rl_bt_info, R.id.donation_item_rl_bt_call})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.donation_item_rl_bt_info:
                    info();
                    break;
                case R.id.donation_item_rl_bt_call:
                    String number = "tel:" + donationData.get(position).getPhone();
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    context.startActivity(callIntent);
                    break;
            }
        }

        private void info() {
            if (isActive(context)) {
                getClient().getDonationDetails(userData.getApiToken(), String.valueOf(donationData.get(position).getId())).enqueue(new Callback<DonationDetailsAndRequest>() {
                    @Override
                    public void onResponse(Call<DonationDetailsAndRequest> call, Response<DonationDetailsAndRequest> response) {
                        try {

                            if (response.body().getStatus() == 1) {

                                HomeActivity home = (HomeActivity) activity;
                                home.setHomeAppBarTextViewChange(context.getString(R.string.request_donation) + " : " + response.body().getData().getPatientName());
                                DonationDetailsFragment donationDetails = new DonationDetailsFragment();
                                donationDetails.donationData = response.body().getData();
                                replaceFragment(home.getSupportFragmentManager(), R.id.home_frame_fragment, donationDetails);
                            }
                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onFailure(Call<DonationDetailsAndRequest> call, Throwable t) {

                    }
                });
            }


        }

    }
}
