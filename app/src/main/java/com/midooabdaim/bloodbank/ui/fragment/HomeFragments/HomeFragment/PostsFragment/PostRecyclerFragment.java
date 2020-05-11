package com.midooabdaim.bloodbank.ui.fragment.HomeFragments.HomeFragment.PostsFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.midooabdaim.bloodbank.Adapter.PostRecyclerViewAdpter;
import com.midooabdaim.bloodbank.Adapter.SpinnerAdpter;
import com.midooabdaim.bloodbank.Data.Model.User.registerAndLoginAndEdit.UserData;
import com.midooabdaim.bloodbank.Data.Model.posts.Posts;
import com.midooabdaim.bloodbank.Data.Model.posts.PostsData;
import com.midooabdaim.bloodbank.Helper.OnEndLess;
import com.midooabdaim.bloodbank.R;
import com.midooabdaim.bloodbank.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

import static com.midooabdaim.bloodbank.Data.Api.RetrofitClient.getClient;
import static com.midooabdaim.bloodbank.Data.Local.SharedPrefrance.loadData;
import static com.midooabdaim.bloodbank.Helper.Constant.favourites;
import static com.midooabdaim.bloodbank.Helper.Constant.max;
import static com.midooabdaim.bloodbank.Helper.GeneralReqestesMethodes.getDataPosts;
import static com.midooabdaim.bloodbank.Helper.GeneralReqestesMethodes.getDataSpinners;


public class PostRecyclerFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.post_fragment_sp_categories)
    Spinner postFragmentSpCategories;
    @BindView(R.id.post_fragment_et_keyword_search)
    EditText postFragmentEtKeywordSearch;
    @BindView(R.id.post_recycler_fragment_rcv)
    RecyclerView postRecyclerFragmentRcv;

    @BindView(R.id.post_fragment_srl_articles_list_refresh)
    SwipeRefreshLayout postFragmentSrlArticlesListRefresh;
    @BindView(R.id.post_fragment_tv_no_results_or_item)
    TextView postFragmentTvNoResultsOrItem;
    private List<PostsData> list=new ArrayList<>();
    private UserData userData;
    private LinearLayoutManager linearLayoutManager;
    private OnEndLess onEndLess;
    private PostRecyclerViewAdpter recyclerViewAdpter;
    private boolean Filter = false;
    private int categoryId = 0;
    private String keyword = "";

    public PostRecyclerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intialFragment();
        View view = inflater.inflate(R.layout.fragment_post_recycler_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        SpinnerAdpter adpter = new SpinnerAdpter(getActivity());
        getDataSpinners(getClient().getCategories(), adpter, getString(R.string.categories), postFragmentSpCategories);
        userData = loadData(getActivity());
        initRecyclerView();
        postFragmentSrlArticlesListRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onEndLess.current_page = 1;
                onEndLess.previousTotal = 0;
                onEndLess.previous_page = 1;
                max = 0;
                list = new ArrayList<>();
                recyclerViewAdpter = new PostRecyclerViewAdpter(getActivity(), list, getActivity(), favourites, postFragmentTvNoResultsOrItem);
                postRecyclerFragmentRcv.setAdapter(recyclerViewAdpter);
                if (Filter) {
                    getPostsFilter(1);
                } else {
                    getPosts(1);
                }
            }
        });
        if (list.size() == 0) {
            getPosts(1);
        }
        return view;
    }

    private void initRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        postRecyclerFragmentRcv.setLayoutManager(linearLayoutManager);
        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= max) {
                    if (max != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;

                        if (Filter) {
                            getPostsFilter(current_page);
                        } else {
                            getPosts(current_page);
                        }

                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }

            }
        };
        postRecyclerFragmentRcv.addOnScrollListener(onEndLess);
        recyclerViewAdpter = new PostRecyclerViewAdpter(getActivity(), list, getActivity(), favourites, postFragmentTvNoResultsOrItem);
        postRecyclerFragmentRcv.setAdapter(recyclerViewAdpter);
    }

    private void getPostsFilter(int current_page) {
        Call<Posts> call = getClient().getPostFilter(userData.getApiToken(), keyword, current_page, categoryId);
        list = new ArrayList<>();
        recyclerViewAdpter = new PostRecyclerViewAdpter(getActivity(), list, getActivity(), favourites, postFragmentTvNoResultsOrItem);
        postRecyclerFragmentRcv.setAdapter(recyclerViewAdpter);
        getDataPosts(current_page, call, getActivity(),  postFragmentTvNoResultsOrItem, list, recyclerViewAdpter, postFragmentSrlArticlesListRefresh,true);
    }

    private void getPosts(int current_page) {
        Call<Posts> call = getClient().getPosts(userData.getApiToken(), current_page);
        list = new ArrayList<>();
        recyclerViewAdpter = new PostRecyclerViewAdpter(getActivity(), list, getActivity(), favourites, postFragmentTvNoResultsOrItem);
        postRecyclerFragmentRcv.setAdapter(recyclerViewAdpter);
        getDataPosts(current_page, call, getActivity(), postFragmentTvNoResultsOrItem, list,  recyclerViewAdpter, postFragmentSrlArticlesListRefresh,false);
    }

    @Override
    public void BackPressed() {
        getActivity().finish();
    }

    @OnClick(R.id.post_fragment_img_search)
    public void onViewClicked() {
        keyword = postFragmentEtKeywordSearch.getText().toString().trim();
        if (postFragmentSpCategories.getSelectedItemPosition() == 0 && keyword.equals("")) {
            if (Filter) {
                Filter = false;
                onEndLess.current_page = 1;
                onEndLess.previous_page = 1;
                onEndLess.previousTotal = 0;
                onEndLess.totalItemCount = 0;
                max = 0;

                list = new ArrayList<>();
                recyclerViewAdpter = new PostRecyclerViewAdpter(getActivity(), list, getActivity(), favourites, postFragmentTvNoResultsOrItem);
                postRecyclerFragmentRcv.setAdapter(recyclerViewAdpter);
                getPosts(1);
            }

        } else {
            Filter = true;
            onEndLess.current_page = 1;
            onEndLess.previous_page = 1;
            onEndLess.previousTotal = 0;
            onEndLess.totalItemCount = 0;
            max = 0;
            list = new ArrayList<>();
            recyclerViewAdpter = new PostRecyclerViewAdpter(getActivity(), list, getActivity(), favourites, postFragmentTvNoResultsOrItem);
            postRecyclerFragmentRcv.setAdapter(recyclerViewAdpter);

            getPostsFilter(1);
        }

    }
}
