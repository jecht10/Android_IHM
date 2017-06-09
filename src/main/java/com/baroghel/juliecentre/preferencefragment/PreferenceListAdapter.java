package com.baroghel.juliecentre.preferencefragment;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baroghel.juliecentre.R;

import java.util.List;

/**
 * Created by User on 13/05/2017.
 */

public class PreferenceListAdapter extends BaseAdapter {
    private static LayoutInflater inflater;
    private Context context;
    private List<PreferenceListItem> data;
    private Resources res;

    public PreferenceListAdapter(Context context, List<PreferenceListItem> data, Resources res) {
        this.context = context;
        this.data = data;
        this.res = res;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (data != null)
            return data.size();
        return 0;
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
        PreferenceListItem item = (PreferenceListItem) getItem(position);
        TextView title;
        if (item.isSection()) {
            v = inflater.inflate(R.layout.preference_list_section, parent, false);
            title = (TextView) v.findViewById(R.id.preference_section_title);
        } else {
            v = inflater.inflate(R.layout.preference_list_item, parent, false);
            title = (TextView) v.findViewById(R.id.preference_item_title);
        }
        title.setText(item.getTitle());
        return v;
    }

    public void changeData(List<PreferenceListItem> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public TextView title;
    }
}
