package com.example.meloobit.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.meloobit.R;
import com.google.android.material.tabs.TabLayout;

public class Top_hits extends Fragment {

    ImageView back;
    TabLayout tab ;
    ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_hits, container, false);

        back = view.findViewById(R.id.bt_back);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        tab = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);
        tab.setupWithViewPager(viewPager);
        MyViewPagerTab myViewPager = new MyViewPagerTab(getActivity().getSupportFragmentManager()
                , FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        myViewPager.addFragment(new Today(),"Today");
        myViewPager.addFragment(new Thisweek(),"This Week");
        viewPager.setAdapter(myViewPager);
        return view;
    }

}