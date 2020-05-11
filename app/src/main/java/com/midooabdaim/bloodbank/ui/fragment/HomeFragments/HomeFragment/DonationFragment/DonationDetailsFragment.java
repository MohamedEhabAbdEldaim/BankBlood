package com.midooabdaim.bloodbank.ui.fragment.HomeFragments.HomeFragment.DonationFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.midooabdaim.bloodbank.Data.Model.donationDetails.DonationData;
import com.midooabdaim.bloodbank.R;
import com.midooabdaim.bloodbank.ui.activity.HomeActivity;
import com.midooabdaim.bloodbank.ui.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.midooabdaim.bloodbank.Helper.HelperMethod.onPermission;

public class DonationDetailsFragment extends BaseFragment {
    public DonationData donationData;
    Unbinder unbinder;
    @BindView(R.id.fragment_details_tv_name)
    TextView fragmentDetailsTvName;
    @BindView(R.id.fragment_details_tv_age)
    TextView fragmentDetailsTvAge;
    @BindView(R.id.fragment_details_tv_blood_type)
    TextView fragmentDetailsTvBloodType;
    @BindView(R.id.fragment_details_tv_number_bdget)
    TextView fragmentDetailsTvNumberBdget;
    @BindView(R.id.fragment_details_tv_hospital)
    TextView fragmentDetailsTvHospital;
    @BindView(R.id.fragment_details_tv_adress)
    TextView fragmentDetailsTvAdress;
    @BindView(R.id.fragment_details_tv_phone)
    TextView fragmentDetailsTvPhone;
    @BindView(R.id.fragment_details_tv_note)
    TextView fragmentDetailsTvNote;
    @BindView(R.id.fragment_details_mv_map)
    MapView fragmentDetailsMvMap;

    public DonationDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intialFragment();
        View view = inflater.inflate(R.layout.fragment_donation_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        setData();
        return view;
    }

    private void setData() {
        fragmentDetailsTvName.setText(getString(R.string.name) + " :- " + donationData.getPatientName());
        fragmentDetailsTvAge.setText(getString(R.string.age) + " :- " + donationData.getPatientAge());
        fragmentDetailsTvBloodType.setText(getString(R.string.bloodtype) + " :- " + donationData.getBloodType().getName());
        fragmentDetailsTvNumberBdget.setText(getString(R.string.bags_number) + " :- " + donationData.getBagsNum());
        fragmentDetailsTvHospital.setText(getString(R.string.hospital) + " :- " + donationData.getHospitalName());
        fragmentDetailsTvAdress.setText(getString(R.string.address) + " :- " + donationData.getHospitalAddress());
        fragmentDetailsTvPhone.setText(getString(R.string.phone) + " :- " + donationData.getPhone());
        fragmentDetailsTvNote.setText(donationData.getNotes());

        fragmentDetailsMvMap.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        fragmentDetailsMvMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                try {

                    MarkerOptions currentUserLocation = new MarkerOptions();
                    currentUserLocation.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_on_black_24dp));

                    LatLng currentUserLatLang = new LatLng(Double.parseDouble(donationData.getLatitude()), Double.parseDouble(donationData.getLongitude()));
                    currentUserLocation.position(currentUserLatLang);
                    googleMap.addMarker(currentUserLocation);
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentUserLatLang, 16f));

                    float zoom = 10;
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentUserLatLang, zoom));

                } catch (Exception e) {

                }
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
        HomeActivity homeActivity = (HomeActivity) getActivity();
        homeActivity.setHomeAppBarUnvisible();
        super.BackPressed();
    }


    @OnClick({R.id.donation_fragment_bt_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.donation_fragment_bt_send:
                onPermission(getActivity());
                getActivity().startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", donationData.getPhone(), null)));
                break;
        }
    }
}
