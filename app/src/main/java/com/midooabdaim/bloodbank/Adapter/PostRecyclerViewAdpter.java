package com.midooabdaim.bloodbank.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.midooabdaim.bloodbank.Data.Model.User.registerAndLoginAndEdit.UserData;
import com.midooabdaim.bloodbank.Data.Model.postToggleFavourite.PostToggleFavourite;
import com.midooabdaim.bloodbank.Data.Model.posts.PostsData;
import com.midooabdaim.bloodbank.R;
import com.midooabdaim.bloodbank.ui.activity.HomeActivity;
import com.midooabdaim.bloodbank.ui.fragment.HomeFragments.HomeFragment.PostsFragment.PostContentFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.midooabdaim.bloodbank.Data.Api.RetrofitClient.getClient;
import static com.midooabdaim.bloodbank.Data.Local.SharedPrefrance.loadData;
import static com.midooabdaim.bloodbank.Helper.HelperMethod.customToast;
import static com.midooabdaim.bloodbank.Helper.HelperMethod.onLoadImageFromUrl;
import static com.midooabdaim.bloodbank.Helper.HelperMethod.replaceFragment;

public class PostRecyclerViewAdpter extends RecyclerView.Adapter<PostRecyclerViewAdpter.viewHolder> {


    private Context context;
    private List<PostsData> list = new ArrayList<>();
    private Activity activity;
    private boolean favourites;
    private TextView postsFragmentTvNoItems;
    private UserData user;

    public PostRecyclerViewAdpter(Context context, List<PostsData> PostsData, Activity activity, boolean favourites, TextView postsFragmentTvNoItems) {
        this.list = PostsData;
        this.context = context;
        this.activity = activity;
        this.favourites = favourites;
        this.postsFragmentTvNoItems = postsFragmentTvNoItems;
        user = loadData(activity);
    }


    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        holder.setIsRecyclable(false);
        setData(holder, position);
        setAction(holder, position);
    }


    private void setData(viewHolder holder, int position) {
        try {
            holder.itemPostCardView.setRadius(20);
            onLoadImageFromUrl(holder.itemPostImgPost, list.get(position).getThumbnailFullPath(), context);
            holder.itemPostTvTitle.setText(list.get(position).getTitle());
            if (list.get(position).getIsFavourite()) {
                holder.itemPostImgLike.setImageResource(R.drawable.afterlike);
            } else {
                holder.itemPostImgLike.setImageResource(R.drawable.beforelike);
            }

        } catch (Exception e) {

        }
    }

    private void setAction(viewHolder holder, int position) {
        holder.itemPostRlFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavourite(holder, position);
            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity homeActivity = (HomeActivity) activity;
                homeActivity.setLinerUnvisible();
                PostContentFragment postContentFragment = new PostContentFragment();
                postContentFragment.postsData = list.get(position);
                replaceFragment(homeActivity.getSupportFragmentManager(),
                        R.id.home_frame_fragment, postContentFragment);

            }
        });
    }

    private void toggleFavourite(viewHolder holder, int position) {
        PostsData postsData = list.get(position);

        list.get(position).setIsFavourite(!list.get(position).getIsFavourite());

        if (list.get(position).getIsFavourite()) {
            holder.itemPostImgLike.setImageResource(R.drawable.afterlike);
            customToast(activity, activity.getString(R.string.added_to_favourite), false);

        } else {
            holder.itemPostImgLike.setImageResource(R.drawable.beforelike);
            customToast(activity, activity.getString(R.string.removed_from_favourite), false);
            if (favourites) {
                list.remove(position);
                notifyDataSetChanged();
                if (list.size() == 0) {
                    postsFragmentTvNoItems.setVisibility(View.VISIBLE);
                    postsFragmentTvNoItems.setText(activity.getString(R.string.No_Item));
                }
            }
        }

        getClient().getPostToggleFavourite(postsData.getId(), user.getApiToken()).enqueue(new Callback<PostToggleFavourite>() {
            @Override
            public void onResponse(Call<PostToggleFavourite> call, Response<PostToggleFavourite> response) {
                try {
                    if (response.body().getStatus() == 1) {
                    } else {
                        list.get(position).setIsFavourite(!list.get(position).getIsFavourite());
                        if (list.get(position).getIsFavourite()) {
                            holder.itemPostImgLike.setImageResource(R.drawable.afterlike);
                            if (favourites) {
                                list.add(position, postsData);
                                notifyDataSetChanged();
                            }
                        } else {
                            holder.itemPostImgLike.setImageResource(R.drawable.beforelike);
                        }
                    }

                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<PostToggleFavourite> call, Throwable t) {
                try {
                    list.get(position).setIsFavourite(!list.get(position).getIsFavourite());
                    if (list.get(position).getIsFavourite()) {
                        holder.itemPostImgLike.setImageResource(R.drawable.afterlike);
                        if (favourites) {
                            list.add(position, postsData);
                            notifyDataSetChanged();
                        }
                    } else {
                        holder.itemPostImgLike.setImageResource(R.drawable.beforelike);
                    }
                } catch (Exception e) {

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_post_img_post)
        ImageView itemPostImgPost;
        @BindView(R.id.item_post_img_like)
        ImageView itemPostImgLike;
        @BindView(R.id.item_post_rl_favourite)
        RelativeLayout itemPostRlFavourite;
        @BindView(R.id.item_post_tv_title)
        TextView itemPostTvTitle;
        @BindView(R.id.item_post_card_view)
        CardView itemPostCardView;
        private View view;

        public viewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
