package com.midooabdaim.bloodbank.ui.fragment.HomeFragments.HomeFragment.DonationFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.midooabdaim.bloodbank.Adapter.DonationRecyclerViewAdpter;
import com.midooabdaim.bloodbank.Adapter.SpinnerAdpter;
import com.midooabdaim.bloodbank.Data.Model.User.registerAndLoginAndEdit.UserData;
import com.midooabdaim.bloodbank.Data.Model.donationDetails.DonationData;
import com.midooabdaim.bloodbank.Data.Model.donationRequests.AllDonation;
import com.midooabdaim.bloodbank.Helper.OnEndLess;
import com.midooabdaim.bloodbank.R;
import com.midooabdaim.bloodbank.ui.fragment.BaseFragment;

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
import static com.midooabdaim.bloodbank.Helper.GeneralReqestesMethodes.getDataSpinners;
import static com.midooabdaim.bloodbank.Helper.HelperMethod.customToast;
import static com.midooabdaim.bloodbank.Helper.InternetState.isActive;


public class DonationReqestesFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.fragment_donation_spinner_blood)
    Spinner fragmentDonationSpinnerBlood;
    @BindView(R.id.fragment_donation_spinner_city)
    Spinner fragmentDonationSpinnerCity;
    @BindView(R.id.fragment_donation_recycler_fragment_rcv)
    RecyclerView fragmentDonationRecyclerFragmentRcv;
    @BindView(R.id.fragment_donation_srl_donation_list_refresh)
    SwipeRefreshLayout fragmentDonationSrlDonationListRefresh;
    @BindView(R.id.fragment_donation_tv_no_results_or_item)
    TextView fragmentDonationTvNoResultsOrItem;
    private List<DonationData> list = new ArrayList<>();
    private DonationRecyclerViewAdpter recyclerViewAdpter;
    private boolean Filter = false;
    private OnEndLess onEndLess;
    private LinearLayoutManager linearLayoutManager;
    private int governorateId = 0;
    private int bloodTypeId = 0;
    private UserData userData;
    private int max = 0;

    public DonationReqestesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intialFragment();
        View view = inflater.inflate(R.layout.fragment_donation_reqestes, container, false);
        unbinder = ButterKnife.bind(this, view);
        userData = loadData(getActivity());
        SpinnerAdpter spinnerAdpter = new SpinnerAdpter(getActivity());
        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    governorateId = fragmentDonationSpinnerCity.getSelectedItemPosition();
                    if (governorateId == 0) {
                        //getdonation
                        Filter = false;
                        getDonation(1);
                    } else {
                        //getfilter
                        Filter = true;
                        getFliter(1);
                    }
                } else {
                    //getfilter
                    Filter = true;
                    getFliter(1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        getDataSpinners(getClient().getBloodTypes(), spinnerAdpter, getString(R.string.bloodtype), fragmentDonationSpinnerBlood, listener);

        SpinnerAdpter adpter = new SpinnerAdpter(getActivity());
        AdapterView.OnItemSelectedListener listeners = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    bloodTypeId = fragmentDonationSpinnerBlood.getSelectedItemPosition();
                    if (bloodTypeId == 0) {
                        //getdonation
                        Filter = false;
                        getDonation(1);
                    } else {
                        //getfilter
                        Filter = true;
                        getFliter(1);
                    }
                } else {
                    //getfilter
                    Filter = true;
                    getFliter(1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        getDataSpinners(getClient().getGovernorate(), adpter, getString(R.string.governorat), fragmentDonationSpinnerCity, listeners);
        intialRecycle();

        fragmentDonationSrlDonationListRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onEndLess.current_page = 1;
                onEndLess.previousTotal = 0;
                onEndLess.previous_page = 1;
                max = 0;
                list = new ArrayList<>();
                recyclerViewAdpter = new DonationRecyclerViewAdpter(getActivity(), getActivity(), list);
                fragmentDonationRecyclerFragmentRcv.setAdapter(recyclerViewAdpter);
                if (Filter) {
                    getFliter(1);
                } else {
                    getDonation(1);
                }
            }
        });
        if (list.size() == 0) {
            getDonation(1);
        }
        return view;
    }

    private void intialRecycle() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        fragmentDonationRecyclerFragmentRcv.setLayoutManager(linearLayoutManager);
        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= max) {
                    if (max != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;

                        if (Filter) {
                            getFliter(current_page);
                        } else {
                            getDonation(current_page);
                        }

                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }

            }
        };
        fragmentDonationRecyclerFragmentRcv.addOnScrollListener(onEndLess);
        list = new ArrayList<>();
        recyclerViewAdpter = new DonationRecyclerViewAdpter(getActivity(), getActivity(), list);
        fragmentDonationRecyclerFragmentRcv.setAdapter(recyclerViewAdpter);
    }

    private void getDonation(int current_page) {
        list = new ArrayList<>();
        recyclerViewAdpter = new DonationRecyclerViewAdpter(getActivity(), getActivity(), list);
        fragmentDonationRecyclerFragmentRcv.setAdapter(recyclerViewAdpter);
        getDataDonation(current_page, getClient().getDonationRequests(userData.getApiToken(), current_page));
    }

    private void getFliter(int current_page) {
        bloodTypeId = fragmentDonationSpinnerBlood.getSelectedItemPosition();
        governorateId = fragmentDonationSpinnerCity.getSelectedItemPosition();
        list = new ArrayList<>();
        recyclerViewAdpter = new DonationRecyclerViewAdpter(getActivity(), getActivity(), list);
        fragmentDonationRecyclerFragmentRcv.setAdapter(recyclerViewAdpter);
        getDataDonation(current_page, getClient().getDonationRequestsFilter(userData.getApiToken(), bloodTypeId, governorateId, current_page));
    }

    private void getDataDonation(int page, Call<AllDonation> call) {
        if (isActive(getActivity())) {
            call.enqueue(new Callback<AllDonation>() {
                @Override
                public void onResponse(Call<AllDonation> call, Response<AllDonation> response) {
                    try {
                        fragmentDonationSrlDonationListRefresh.setRefreshing(false);
                        if (response.body().getStatus() == 1) {
                            if (page == 1) {
                                if (response.body().getData().getTotal() > 0) {
                                    fragmentDonationTvNoResultsOrItem.setVisibility(View.GONE);
                                } else {
                                    fragmentDonationTvNoResultsOrItem.setVisibility(View.VISIBLE);
                                    if (Filter) {
                                        fragmentDonationTvNoResultsOrItem.setText(getString(R.string.NO_Result));
                                    } else {
                                        fragmentDonationTvNoResultsOrItem.setText(getString(R.string.No_Item));
                                    }
                                }
                            }
                            max = response.body().getData().getLastPage();
                            list.addAll(response.body().getData().getData());
                            recyclerViewAdpter.notifyDataSetChanged();

                        } else {
                            customToast(getActivity(), response.body().getMsg(), true);
                        }

                    } catch (Exception e) {
                        customToast(getActivity(), e.getMessage(), true);

                    }
                }

                @Override
                public void onFailure(Call<AllDonation> call, Throwable t) {
                    customToast(getActivity(), getString(R.string.failed), true);
                    try {
                        fragmentDonationSrlDonationListRefresh.setRefreshing(false);
                    } catch (Exception e) {

                    }
                }
            });

        } else {
            customToast(getActivity(), getString(R.string.nointernt), true);
            try {
                fragmentDonationSrlDonationListRefresh.setRefreshing(false);
            } catch (Exception e) {

            }
        }
    }


    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void BackPressed() {
        getActivity().finish();
    }
}
