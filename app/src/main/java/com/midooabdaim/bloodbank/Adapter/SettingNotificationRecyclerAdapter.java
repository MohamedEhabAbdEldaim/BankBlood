package com.midooabdaim.bloodbank.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.midooabdaim.bloodbank.Data.Model.GenralReqest.GenralReqestData;
import com.midooabdaim.bloodbank.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingNotificationRecyclerAdapter extends RecyclerView.Adapter<SettingNotificationRecyclerAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<GenralReqestData> DataList = new ArrayList<>();
    private List<String> idSelected = new ArrayList<>();
    public List<Integer> id = new ArrayList<>();

    public SettingNotificationRecyclerAdapter(Context context, Activity activity, List<GenralReqestData> DataList, List<String> idSelected) {
        this.context = context;
        this.activity = activity;
        this.DataList = DataList;
        this.idSelected = idSelected;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_check_box, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        holder.itemCheckBoxCh.setText(DataList.get(position).getName());
        if (idSelected.contains(String.valueOf(DataList.get(position).getId()))) {
            holder.itemCheckBoxCh.setChecked(true);
            id.add(DataList.get(position).getId());
        } else {
            holder.itemCheckBoxCh.setChecked(false);
        }
    }

    private void setAction(ViewHolder holder, int position) {
        holder.Position = position;
    }

    @Override
    public int getItemCount() {
        return DataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.item_check_box_ch)
        CheckBox itemCheckBoxCh;
        public int Position;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.item_check_box_ch)
        public void onViewClicked() {
            if (itemCheckBoxCh.isChecked()) {
                id.add(DataList.get(Position).getId());
            } else {
                id.remove(new Integer(DataList.get(Position).getId()));
            }
        }

    }
}
