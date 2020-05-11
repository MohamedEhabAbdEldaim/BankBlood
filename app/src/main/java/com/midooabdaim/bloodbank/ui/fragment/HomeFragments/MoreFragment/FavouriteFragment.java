package com.midooabdaim.bloodbank.ui.fragment.HomeFragments.MoreFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.midooabdaim.bloodbank.Adapter.PostRecyclerViewAdpter;
import com.midooabdaim.bloodbank.Data.Model.User.registerAndLoginAndEdit.UserData;
import com.midooabdaim.bloodbank.Data.Model.posts.Posts;
import com.midooabdaim.bloodbank.Data.Model.posts.PostsData;
import com.midooabdaim.bloodbank.Helper.OnEndLess;
import com.midooabdaim.bloodbank.R;
import com.midooabdaim.bloodbank.ui.activity.HomeActivity;
import com.midooabdaim.bloodbank.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

import static com.midooabdaim.bloodbank.Data.Api.RetrofitClient.getClient;
import static com.midooabdaim.bloodbank.Data.Local.SharedPrefrance.loadData;
import static com.midooabdaim.bloodbank.Helper.Constant.favourites;
import static com.midooabdaim.bloodbank.Helper.Constant.max;
import static com.midooabdaim.bloodbank.Helper.GeneralReqestesMethodes.getDataPosts;


public class FavouriteFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.favourite_fragment_RecyclerView)
    RecyclerView favouriteFragmentRecyclerView;
    @BindView(R.id.favourite_fragment_tv_no_post)
    TextView favouriteFragmentTvNoPost;
    private OnEndLess onEndLess;
    private List<PostsData> list = new ArrayList<>();
    private PostRecyclerViewAdpter recyclerViewAdpter;
    private UserData userData;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intialFragment();
        HomeActivity home = (HomeActivity) getActivity();
        home.setHomeAppBarTextViewChange(getString(R.string.Favorite));
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        unbinder = ButterKnife.bind(this, view);
        userData = loadData(getActivity());
        favourites = true;
        initialrec();
        if (list.size() == 0) {

            getfavivort(1);
        }
        return view;
    }

    private void initialrec() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        favouriteFragmentRecyclerView.setLayoutManager(linearLayoutManager);
        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= max) {
                    if (max != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        getfavivort(current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }

            }
        };
        favouriteFragmentRecyclerView.addOnScrollListener(onEndLess);
        recyclerViewAdpter = new PostRecyclerViewAdpter(getActivity(), list, getActivity(), favourites, favouriteFragmentTvNoPost);
        favouriteFragmentRecyclerView.setAdapter(recyclerViewAdpter);
    }

    private void getfavivort(int current_page) {
        Call<Posts> call = getClient().getMyFavourites(userData.getApiToken(), current_page);
        list = new ArrayList<>();
        recyclerViewAdpter = new PostRecyclerViewAdpter(getActivity(), list, getActivity(), favourites, favouriteFragmentTvNoPost);
        favouriteFragmentRecyclerView.setAdapter(recyclerViewAdpter);
        getDataPosts(current_page, call, getActivity(), favouriteFragmentTvNoPost, list, recyclerViewAdpter, null, false);
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
}

