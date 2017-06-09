package com.baroghel.juliecentre.preferencefragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baroghel.juliecentre.MainFragment;
import com.baroghel.juliecentre.R;
import com.baroghel.juliecentre.data.ShopNews;
import com.baroghel.juliecentre.data.TendanceNews;
import com.baroghel.juliecentre.datastore.DataBaseSQLite;
import com.baroghel.juliecentre.datastore.PreferenceBDD;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 13/05/2017.
 */

public class PreferencesFragment extends MainFragment {
    @Override
    public MainFragment newInstance() {
        return new PreferencesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.preference_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        ListView preferenceListView = (ListView)view.findViewById(R.id.preference_list);
        final PreferenceListAdapter adapter = new PreferenceListAdapter(getActivity(), getAllPreferences(), getResources());
        preferenceListView.setAdapter(adapter);

        preferenceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final PreferenceListItem item = (PreferenceListItem)adapter.getItem(position);
                if(item.isSection())
                    return;

                DataBaseSQLite dataBaseSQLite = new DataBaseSQLite(getActivity());
                final PreferenceBDD preferenceBDD = new PreferenceBDD(dataBaseSQLite);
                if (item.isTendance()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(item.getTypeNews().getName())
                            .setMessage("Se desabonner ?")
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    preferenceBDD.removeTypePreference(item.getTypeNews().getId());
                                    adapter.changeData(getAllPreferences());
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    builder.create().show();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(item.getTypeNews().getName())
                            .setMessage("Se desabonner ?")
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    preferenceBDD.removeShopPreference(item.getTypeNews().getId());
                                    adapter.changeData(getAllPreferences());
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    builder.create().show();
                }
            }
        });
    }

    private List<PreferenceListItem> getAllPreferences(){
        DataBaseSQLite dataBaseSQLite = new DataBaseSQLite(getActivity());
        PreferenceBDD preferenceBDD = new PreferenceBDD(dataBaseSQLite);

        List<PreferenceListItem> result = new ArrayList<>();
        result.add(new PreferenceListItem("Magasin"));

        List<ShopNews> shopNewsList = preferenceBDD.getAllShopPreferences();
        for(ShopNews shopNews : shopNewsList){
            result.add(new PreferenceListItem(shopNews));
        }

        result.add(new PreferenceListItem("Tendance"));
        List<TendanceNews> tendanceNewsList = preferenceBDD.getAllTendancePreferences();
        for(TendanceNews tendanceNews : tendanceNewsList){
            result.add(new PreferenceListItem(tendanceNews));
        }

        preferenceBDD.close();

        return result;
    }

}
