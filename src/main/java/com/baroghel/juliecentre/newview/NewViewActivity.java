package com.baroghel.juliecentre.newview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baroghel.juliecentre.R;
import com.baroghel.juliecentre.datastore.DataBaseSQLite;
import com.baroghel.juliecentre.datastore.InscriptionBDD;


/**
 * Created by User on 12/05/2017.
 */

public class NewViewActivity extends AppCompatActivity {
    private final String NEWS_ID = "id";
    private final String NEWS_TITLE = "titre";
    private final String NEWS_IMG = "image";
    private final String NEWS_CONTENT = "contenu";

    private Activity activity;
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_view);

        activity = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.title_news_view);
        TextView content = (TextView) findViewById(R.id.text_news_view);
        ImageView img = (ImageView) findViewById(R.id.backgroundImageView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Intent intent = getIntent();
        title.setText(intent.getStringExtra(NEWS_TITLE));
        content.setText(intent.getStringExtra(NEWS_CONTENT));

        Context imageContext = img.getContext();
        int id = imageContext.getResources().getIdentifier(intent.getStringExtra(NEWS_IMG), "drawable",
                imageContext.getPackageName());
        img.setImageResource(id);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(intent.getStringExtra(NEWS_TITLE));


        DataBaseSQLite dataBaseSQLite = new DataBaseSQLite(this);
        final InscriptionBDD inscriptionBDD = new InscriptionBDD(dataBaseSQLite);
        final int newsId = intent.getIntExtra(NEWS_ID, 0);

        final FloatingActionButton reserverButton = (FloatingActionButton) findViewById(R.id.news_view_add_event);
        if (inscriptionBDD.isNewsAlreadySubscribed(newsId)) {
            reserverButton.setVisibility(View.GONE);
        } else {
            reserverButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerFragment datePickerFrag = new DatePickerFragment();
                    datePickerFrag.setData(newsId, intent.getStringExtra(NEWS_TITLE));
                    datePickerFrag.show(getFragmentManager(), "DatePicker");
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
