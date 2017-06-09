package com.baroghel.juliecentre.newsactivity;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baroghel.juliecentre.R;
import com.baroghel.juliecentre.data.News;

import java.util.ArrayList;

/**
 * Created by User on 30/04/2017.
 */

public class NewsListAdapter extends BaseAdapter{
    private Context context;
    private ArrayList data;
    private static LayoutInflater inflater;
    private Resources res;

    public NewsListAdapter(Context context, ArrayList data, Resources res){
        this.context = context;
        this.data = data;
        this.res = res;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(data.size() <= 0)
            return 1;
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder;

        if(v == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item_news_activity, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.titre = (TextView)v.findViewById(R.id.text_list_item_news_activity);
            viewHolder.imageView = (ImageView)v.findViewById(R.id.image_list_item_news_activity);

            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        News news = (News) getItem(position);
        viewHolder.titre.setText(news.getTitre());
        Context imageContext = viewHolder.imageView.getContext();
        int id = imageContext.getResources().getIdentifier(news.getImage(), "drawable", imageContext.getPackageName());
        viewHolder.imageView.setImageResource(id);

        return v;
    }



    private static class ViewHolder{
        public TextView titre;
        public ImageView imageView;
    }
}
