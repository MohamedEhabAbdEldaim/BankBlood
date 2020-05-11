package com.midooabdaim.bloodbank.ui.fragment.HomeFragments.HomeFragment.PostsFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.midooabdaim.bloodbank.Data.Model.posts.PostsData;
import com.midooabdaim.bloodbank.R;
import com.midooabdaim.bloodbank.ui.activity.HomeActivity;
import com.midooabdaim.bloodbank.ui.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.midooabdaim.bloodbank.Helper.HelperMethod.onLoadImageFromUrl;


public class PostContentFragment extends BaseFragment {


    public PostsData postsData;
    Unbinder unbinder;
    @BindView(R.id.fragment_post_content_image_view)
    ImageView fragmentPostContentImageView;
    @BindView(R.id.fragment_post_content_tv_title)
    TextView fragmentPostContentTvTitle;
    @BindView(R.id.fragment_post_content_like)
    ImageView fragmentPostContentLike;
    @BindView(R.id.fragment_post_content_rl_favourite)
    RelativeLayout fragmentPostContentRlFavourite;
    @BindView(R.id.fragment_post_content_tv_content)
    TextView fragmentPostContentTvContent;

    public PostContentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intialFragment();
        View view = inflater.inflate(R.layout.fragment_post_content, container, false);
        unbinder = ButterKnife.bind(this, view);
        getPostDetails();
        return view;
    }

    private void getPostDetails() {
        try {
            onLoadImageFromUrl(fragmentPostContentImageView, postsData.getThumbnailFullPath(), getActivity());
            fragmentPostContentTvTitle.setText(postsData.getTitle());
            fragmentPostContentTvContent.setText(postsData.getContent());

            if (postsData.getIsFavourite()) {
                fragmentPostContentLike.setImageResource(R.drawable.afterlike);

            } else {
                fragmentPostContentLike.setImageResource(R.drawable.beforelike);

            }
        } catch (Exception e) {

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void BackPressed() {
        HomeActivity homeActivity = (HomeActivity) getActivity();
        homeActivity.setLinervisiable();
        super.BackPressed();
    }


    @OnClick({R.id.fragment_post_img_back, R.id.fragment_post_content_rl_favourite})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_post_img_back:
                BackPressed();
                break;
            case R.id.fragment_post_content_rl_favourite:
                break;
        }
    }
}
