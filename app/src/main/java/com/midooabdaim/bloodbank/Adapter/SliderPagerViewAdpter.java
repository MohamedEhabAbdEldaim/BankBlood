package com.midooabdaim.bloodbank.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.midooabdaim.bloodbank.R;

import java.util.ArrayList;
import java.util.List;


public class SliderPagerViewAdpter extends PagerAdapter {
    private Context context;
    public List<Integer> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private View.OnClickListener action;
    private int btnIcon, activeIcon, anActiveIcon;
    private LayoutInflater layoutInflater;
    public int position = 1;

    public SliderPagerViewAdpter(Context context, View.OnClickListener action, int btnIcon, int activeIcon, int anActiveIcon) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        images = new ArrayList<>();
        titles = new ArrayList<>();
        this.action = action;
        this.btnIcon = btnIcon;
        this.activeIcon = activeIcon;
        this.anActiveIcon = anActiveIcon;
    }

    public void addPage(Integer image, String title) {
        images.add(image);
        titles.add(title);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        try {
            this.position = position;
            View view = layoutInflater.inflate(R.layout.item_pager, container, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.item_pager_img_view);
            TextView textView = (TextView) view.findViewById(R.id.item_pager_txt_view);
            LinearLayout llSliderSteps = (LinearLayout) view.findViewById(R.id.item_pager_ll_slider_steps);
            Button btnSliderAction = (Button) view.findViewById(R.id.item_pager_btn_slider_next);

            btnSliderAction.setBackgroundResource(btnIcon);
            btnSliderAction.setOnClickListener(action);

            imageView.setImageResource(images.get(position));
            textView.setText(titles.get(position));

            for (int i = 0; i < images.size(); i++) {
                ImageView image = createImageView();
                if (i == position) {
                    image.setImageResource(activeIcon);
                } else {
                    image.setImageResource(anActiveIcon);
                }
                llSliderSteps.addView(image);
            }
            container.addView(view);
            return view;
        } catch (Exception e) {
            return null;
        }
    }

    private ImageView createImageView() {
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams((int) context.getResources().getDimension(R.dimen.d10)
                , (int) context.getResources().getDimension(R.dimen.d10));
        lparams.setMargins(4, 4, 4, 4); // left, top, right, bottom

        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(lparams);
        return (imageView);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);

    }
}
