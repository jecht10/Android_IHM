package com.baroghel.juliecentre.newsactivity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baroghel.juliecentre.MainFragment;
import com.baroghel.juliecentre.R;

/**
 * Created by User on 30/04/2017.
 */

public class NewsActivityFragment extends MainFragment {

    public NewsActivityFragment() {

    }

    @Override
    public MainFragment newInstance() {
        NewsActivityFragment fragment = new NewsActivityFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_pager_news_activity, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new ViewPagerNewsActivityAdapter(getChildFragmentManager(), getActivity()));
    }
}
