package com.baroghel.juliecentre;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baroghel.juliecentre.data.ShopNews;
import com.baroghel.juliecentre.data.TendanceNews;
import com.baroghel.juliecentre.datastore.DataBaseSQLite;
import com.baroghel.juliecentre.datastore.PreferenceBDD;
import com.baroghel.juliecentre.datastore.ShopBDD;
import com.baroghel.juliecentre.datastore.TendanceBDD;
import com.baroghel.juliecentre.decouverte.DecouverteFragment;
import com.baroghel.juliecentre.newsactivity.NewsActivityFragment;
import com.baroghel.juliecentre.data.SearchSuggestions;
import com.baroghel.juliecentre.preferencefragment.PreferencesFragment;
import com.baroghel.juliecentre.reservations.ReservationFragment;

import java.util.ArrayList;
import java.util.List;

public class  MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Context context;
    private Activity activity;
    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        activity = this;

        DataBaseSQLite dataBaseSQLite = new DataBaseSQLite(this);
        dataBaseSQLite.createDataBase();

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle("Home");

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (id){
                    case R.id.nav_home:
                        mainFragment = new NewsActivityFragment();
                        activity.getFragmentManager().beginTransaction().replace(R.id.main_content, mainFragment).commit();
                        getSupportActionBar().setSubtitle("Home");
                        break;
                    case R.id.nav_preference:
                        mainFragment = new PreferencesFragment();
                        activity.getFragmentManager().beginTransaction().replace(R.id.main_content, mainFragment).commit();
                        getSupportActionBar().setSubtitle("Preference");
                        break;
                    case R.id.nav_reservations:
                        mainFragment = new ReservationFragment();
                        activity.getFragmentManager().beginTransaction().replace(R.id.main_content, mainFragment).commit();
                        getSupportActionBar().setSubtitle("Reservations");
                        break;
                    case R.id.nav_decouverte:
                        mainFragment = new DecouverteFragment();
                        activity.getFragmentManager().beginTransaction().replace(R.id.main_content, mainFragment).commit();
                        getSupportActionBar().setSubtitle("Decouverte");
                        break;
                    default:
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer,
                R.string.close_drawer){
            public void onDrawerClosed(View view){
                Log.d("HomeActivity", "onDrawerClosed");
            }

            public void onDrawerOpened(View view){
                Log.d("HomeActivity", "onDrawerOpened");
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mainFragment = new NewsActivityFragment();
        getFragmentManager().beginTransaction().add(R.id.main_content, mainFragment).commit();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.action_bar_main_activity, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        List<SearchSuggestions> searchSuggestions = getAllSearchSuggestion();

        ListView listSearchSuggestion = (ListView)findViewById(R.id.list_search_suggestions);
        final SearchSuggestionsAdapter adapter = new SearchSuggestionsAdapter(this, searchSuggestions, getResources(), R.id.main_content);
        listSearchSuggestion.setAdapter(adapter);

        final DataBaseSQLite dataBaseSQLite = new DataBaseSQLite(this);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        listSearchSuggestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final SearchSuggestions item = (SearchSuggestions)adapter.getItem(position);
                final PreferenceBDD preferenceBDD = new PreferenceBDD(dataBaseSQLite);
                String message;
                DialogInterface.OnClickListener positiveButton;
                if(item.getDesc().equals("TENDANCE")){
                    List<TendanceNews> tendanceNewsList = preferenceBDD.getAllTendancePreferences();
                    if(tendanceNewsList.contains(item.getTypeNews())){
                        message = "Se desabonner ?";
                        positiveButton = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                preferenceBDD.removeTypePreference(item.getTypeNews().getId());
                                activity.getFragmentManager().beginTransaction().replace(R.id.main_content, mainFragment.newInstance()).commit();
                                dialog.cancel();
                            }
                        };
                    }else {
                        message = "S'abonner ?";
                        positiveButton = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                preferenceBDD.insertTypePreference(item.getTypeNews().getId());
                                activity.getFragmentManager().beginTransaction().replace(R.id.main_content, mainFragment.newInstance()).commit();
                                dialog.cancel();
                            }
                        };
                    }
                }else{
                    List<ShopNews> shopNewsList = preferenceBDD.getAllShopPreferences();
                    if(shopNewsList.contains(item.getTypeNews())){
                        message = "Se desabonner ?";
                        positiveButton = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                preferenceBDD.removeShopPreference(item.getTypeNews().getId());
                                activity.getFragmentManager().beginTransaction().replace(R.id.main_content, mainFragment.newInstance()).commit();
                                dialog.cancel();
                            }
                        };
                    }else {
                        message = "S'abonner ?";
                        positiveButton = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                preferenceBDD.insertShopPreference(item.getTypeNews().getId());
                                activity.getFragmentManager().beginTransaction().replace(R.id.main_content, mainFragment.newInstance()).commit();
                                dialog.cancel();
                            }
                        };
                    }
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(item.getTypeNews().getName())
                        .setMessage(message)
                        .setPositiveButton("Oui", positiveButton)
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.create().show();
            }
        });

        return true;
    }

    private List<SearchSuggestions> getAllSearchSuggestion(){
        DataBaseSQLite dataBaseSQLite = new DataBaseSQLite(this);
        ArrayList<SearchSuggestions> searchSuggestions = new ArrayList<>();

        final TendanceBDD tendanceBDD = new TendanceBDD(dataBaseSQLite);
        List<TendanceNews> tendanceNewsList = tendanceBDD.getAllTendances();
        tendanceBDD.close();
        for(TendanceNews tendanceNews : tendanceNewsList){
            searchSuggestions.add(new SearchSuggestions(tendanceNews));
        }
        ShopBDD shopBDD = new ShopBDD(dataBaseSQLite);
        List<ShopNews> shopNewsList = shopBDD.getAllShops();
        shopBDD.close();
        for(ShopNews shopNews : shopNewsList){
            searchSuggestions.add(new SearchSuggestions(shopNews));
        }
        return searchSuggestions;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
