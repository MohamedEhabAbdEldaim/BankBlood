package com.midooabdaim.bloodbank.Helper;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.midooabdaim.bloodbank.Adapter.PostRecyclerViewAdpter;
import com.midooabdaim.bloodbank.Adapter.SpinnerAdpter;
import com.midooabdaim.bloodbank.Data.Model.GenralReqest.GenralReqest;
import com.midooabdaim.bloodbank.Data.Model.posts.Posts;
import com.midooabdaim.bloodbank.Data.Model.posts.PostsData;
import com.midooabdaim.bloodbank.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.midooabdaim.bloodbank.Helper.Constant.favourites;
import static com.midooabdaim.bloodbank.Helper.HelperMethod.customToast;
import static com.midooabdaim.bloodbank.Helper.InternetState.isActive;
import static com.midooabdaim.bloodbank.Helper.Constant.max;


public class GeneralReqestesMethodes {

    public static void getDataSpinners(Call<GenralReqest> call, SpinnerAdpter adpter, String hint, Spinner spinner) {
        call.enqueue(new Callback<GenralReqest>() {
            @Override
            public void onResponse(Call<GenralReqest> call, Response<GenralReqest> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        adpter.setData(response.body().getData(), hint);
                        spinner.setAdapter(adpter);
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<GenralReqest> call, Throwable t) {

            }
        });

    }

    public static void getDataSpinners(Call<GenralReqest> call, SpinnerAdpter adpter, String hint, Spinner spinner, AdapterView.OnItemSelectedListener listener) {
        call.enqueue(new Callback<GenralReqest>() {
            @Override
            public void onResponse(Call<GenralReqest> call, Response<GenralReqest> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        adpter.setData(response.body().getData(), hint);
                        spinner.setAdapter(adpter);
                        spinner.setOnItemSelectedListener(listener);
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<GenralReqest> call, Throwable t) {

            }
        });

    }

    public static void getDataSpinnersWithSelect(Call<GenralReqest> call, SpinnerAdpter adpter, String hint, Spinner spinner, int selected) {
        call.enqueue(new Callback<GenralReqest>() {
            @Override
            public void onResponse(Call<GenralReqest> call, Response<GenralReqest> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        adpter.setData(response.body().getData(), hint);
                        spinner.setAdapter(adpter);
                        spinner.setSelection(selected);
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<GenralReqest> call, Throwable t) {

            }
        });

    }

    public static void getDataSpinnersWithSelect(Call<GenralReqest> call, SpinnerAdpter adpter, String hint, Spinner spinner, AdapterView.OnItemSelectedListener listener, int selected) {
        call.enqueue(new Callback<GenralReqest>() {
            @Override
            public void onResponse(Call<GenralReqest> call, Response<GenralReqest> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        adpter.setData(response.body().getData(), hint);
                        spinner.setAdapter(adpter);
                        spinner.setSelection(selected);
                        spinner.setOnItemSelectedListener(listener);

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<GenralReqest> call, Throwable t) {

            }
        });

    }

    public static void getDataPosts(int page, Call<Posts> call, Activity activity, TextView textView, List<PostsData> postsList, PostRecyclerViewAdpter recyclerViewAdpter, SwipeRefreshLayout postFragmentSrlArticlesListRefresh, boolean filter) {
        if (isActive(activity)) {
            call.enqueue(new Callback<Posts>() {
                @Override
                public void onResponse(Call<Posts> call, Response<Posts> response) {
                    try {
                        if (postFragmentSrlArticlesListRefresh != null) {
                            postFragmentSrlArticlesListRefresh.setRefreshing(false);
                        }

                        if (response.body().getStatus() == 1) {
                            if (page == 1) {
                                if (response.body().getData().getTotal() > 0) {
                                    textView.setVisibility(View.GONE);
                                } else {
                                    textView.setVisibility(View.VISIBLE);
                                    if (favourites) {
                                        textView.setText(activity.getString(R.string.No_Item));
                                    } else {
                                        if (filter) {
                                            textView.setText(activity.getString(R.string.NO_Result));
                                        } else {
                                            textView.setText(activity.getString(R.string.No_Item));

                                        }
                                    }
                                }

                            }

                            max = response.body().getData().getLastPage();
                            postsList.addAll(response.body().getData().getData());
                            recyclerViewAdpter.notifyDataSetChanged();
                        } else {
                            customToast(activity, response.body().getMsg(), true);
                        }

                    } catch (Exception e) {
                        customToast(activity, e.getMessage(), true);

                    }
                }

                @Override
                public void onFailure(Call<Posts> call, Throwable t) {

                    customToast(activity, activity.getString(R.string.failed), true);
                    try {
                        if (postFragmentSrlArticlesListRefresh != null) {
                            postFragmentSrlArticlesListRefresh.setRefreshing(false);
                        }
                    } catch (Exception e) {

                    }
                }
            });

        } else {
            customToast(activity, activity.getString(R.string.nointernt), true);
            try {
                if (postFragmentSrlArticlesListRefresh != null) {
                    postFragmentSrlArticlesListRefresh.setRefreshing(false);
                }
            } catch (Exception e) {

            }
        }
    }


}
