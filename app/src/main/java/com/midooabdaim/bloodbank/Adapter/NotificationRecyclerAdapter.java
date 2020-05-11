package com.midooabdaim.bloodbank.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.midooabdaim.bloodbank.Data.Model.Notification.NotificationData;
import com.midooabdaim.bloodbank.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationRecyclerAdapter extends RecyclerView.Adapter<NotificationRecyclerAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<NotificationData> notificationData = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notfication,
                parent, false);

        return new ViewHolder(view);
    }

    public NotificationRecyclerAdapter(Context context, Activity activity, List<NotificationData> notificationData) {
        this.context = context;
        this.activity = activity;
        this.notificationData = notificationData;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        if (notificationData.get(position).getPivot().getIsRead().equals("0")) {
            holder.itemNotificationImIcon.setImageResource(R.drawable.ic_notifications_black_24dp);
        } else {
            holder.itemNotificationImIcon.setImageResource(R.drawable.ic_notifications_none_black_24dp);
        }
        holder.itemNotificationTvTitle.setText(notificationData.get(position).getTitle());
        holder.itemNotificationTvTime.setText(notificationData.get(position).getUpdatedAt());

    }

    private void setAction(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return notificationData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_notification_im_icon)
        ImageView itemNotificationImIcon;
        @BindView(R.id.item_notification_tv_title)
        TextView itemNotificationTvTitle;
        @BindView(R.id.item_notification_tv_time)
        TextView itemNotificationTvTime;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
