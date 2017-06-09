package com.baroghel.juliecentre;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.baroghel.juliecentre.data.SearchSuggestions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 01/05/2017.
 */

public class SearchSuggestionsAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private ArrayList<SearchSuggestions> data;
    private ArrayList<SearchSuggestions> filterData;
    private ValueFilter valueFilter;
    private Resources res;
    private int resultLayout;

    public SearchSuggestionsAdapter(Context context, List<SearchSuggestions> data, Resources res, int resultLayout) {
        this.context = context;
        this.data = new ArrayList<>(data);
        this.filterData = new ArrayList<>();
        this.res = res;
        this.resultLayout = resultLayout;
    }

    @Override
    public int getCount() {
        return filterData.size();
    }

    @Override
    public Object getItem(int position) {
        return filterData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.search_suggestion_item, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) v.findViewById(R.id.search_suggestion_item_title);
            holder.desc = (TextView) v.findViewById(R.id.search_suggestion_item_desc);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        SearchSuggestions suggestions = (SearchSuggestions) getItem(position);
        holder.title.setText(suggestions.getTypeNews().getName());
        holder.desc.setText(suggestions.getDesc());

        return v;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null)
            valueFilter = new ValueFilter();
        return valueFilter;
    }

    private static class ViewHolder {
        public TextView title;
        public TextView desc;
    }

    private class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<SearchSuggestions> filterList = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    if ((data.get(i).getTypeNews().getName().toUpperCase()).contains(constraint.toString().toUpperCase()))
                        filterList.add(data.get(i));
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = 0;
                results.values = new ArrayList<>();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filterData = (ArrayList<SearchSuggestions>) results.values;
            notifyDataSetChanged();
        }
    }
}
