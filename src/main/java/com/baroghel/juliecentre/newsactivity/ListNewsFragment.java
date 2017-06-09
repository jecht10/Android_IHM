package com.baroghel.juliecentre.newsactivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baroghel.juliecentre.R;
import com.baroghel.juliecentre.data.News;
import com.baroghel.juliecentre.newview.NewViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 29/04/2017.
 */

public class ListNewsFragment extends Fragment {
    private static final String NEWS_ID = "id";
    private static final String NEWS_TITLE = "titre";
    private static final String NEWS_IMG = "image";
    private static final String NEWS_CONTENT = "contenu";

    private ArrayList<News> newsArrayList;

    public static Fragment newInstance(List<News> newsList){
        ListNewsFragment fragment = new ListNewsFragment();
        fragment.addList(newsList);
        return fragment;
    }

    public void addList(List<News> newsList){
        this.newsArrayList = new ArrayList<>(newsList);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.list_news_activity, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceSate){
        super.onActivityCreated(savedInstanceSate);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        ListView listView = (ListView)view.findViewById(R.id.list_news);
        final NewsListAdapter adapter = new NewsListAdapter(getActivity(), newsArrayList, getResources());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = (News)adapter.getItem(position);
                Intent intent = new Intent(getActivity(), NewViewActivity.class);
                intent.putExtra(NEWS_ID, news.getId());
                intent.putExtra(NEWS_TITLE, news.getTitre());
                intent.putExtra(NEWS_IMG, news.getImage());
                intent.putExtra(NEWS_CONTENT, news.getDesc());
                getActivity().startActivity(intent);
            }
        });
    }
}
