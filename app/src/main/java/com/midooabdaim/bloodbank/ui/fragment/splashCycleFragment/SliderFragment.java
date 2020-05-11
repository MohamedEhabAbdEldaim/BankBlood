package com.midooabdaim.bloodbank.ui.fragment.splashCycleFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;


import com.jaeger.library.StatusBarUtil;
import com.midooabdaim.bloodbank.Adapter.SliderPagerViewAdpter;
import com.midooabdaim.bloodbank.R;
import com.midooabdaim.bloodbank.ui.activity.HomeActivity;
import com.midooabdaim.bloodbank.ui.activity.UserRecycleActivity;
import com.midooabdaim.bloodbank.ui.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SliderFragment extends BaseFragment {


    @BindView(R.id.fragment_slider_vp)
    ViewPager fragmentSliderVp;

    Unbinder unbinder;
    private SliderPagerViewAdpter sliderPagerViewAdpter;

    public SliderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intialFragment();
        View view = inflater.inflate(R.layout.fragment_slider, container, false);
        unbinder = ButterKnife.bind(this, view);
        StatusBarUtil.setTranslucent(getActivity());
        View.OnClickListener action = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentItem = fragmentSliderVp.getCurrentItem();
                if (currentItem == sliderPagerViewAdpter.images.size() - 1) {
                    Intent intent = new Intent(getActivity(), UserRecycleActivity.class);
                    getActivity().startActivity(intent);
                    getActivity().finish();

                } else {
                    fragmentSliderVp.setCurrentItem(currentItem + 1);
                }

            }
        };
        sliderPagerViewAdpter = new SliderPagerViewAdpter(getActivity(), action, R.drawable.ic_next,
                R.drawable.check_circle, R.drawable.uncheck_circle);
        sliderPagerViewAdpter.addPage(R.drawable.slider, getString(R.string.slider));
        sliderPagerViewAdpter.addPage(R.drawable.slider1, getString(R.string.slider1));
        sliderPagerViewAdpter.addPage(R.drawable.slider2, getString(R.string.slider2));
        fragmentSliderVp.setAdapter(sliderPagerViewAdpter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void BackPressed() {
        getActivity().finish();
    }
}
