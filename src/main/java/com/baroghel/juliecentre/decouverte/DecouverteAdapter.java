package com.baroghel.juliecentre.decouverte;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baroghel.juliecentre.R;
import com.baroghel.juliecentre.data.ShopNews;
import com.baroghel.juliecentre.datastore.DataBaseSQLite;
import com.baroghel.juliecentre.datastore.ShopBDD;
import com.baroghel.juliecentre.shopview.ShopViewActivity;

import java.util.List;

/**
 * Created by User on 27/05/2017.
 */

public class DecouverteAdapter extends RecyclerView.Adapter<DecouverteAdapter.DecourverteViewHolder> {
    private List<ShopNews> data;
    private Context context;

    public DecouverteAdapter(Context context){
        this.context = context;
        this.data = getData();
    }

    private List<ShopNews> getData(){
        DataBaseSQLite dataBaseSQLite = new DataBaseSQLite(context);
        ShopBDD shopBDD = new ShopBDD(dataBaseSQLite);
        List<ShopNews> allShop = shopBDD.getAllShopsWithAllInformations();
        shopBDD.close();
        return allShop;
    }

    @Override
    public DecourverteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.decouverte_recycler_view_item, null);
        DecourverteViewHolder holder = new DecourverteViewHolder(layoutView);
        return holder;
    }

    @Override
    public void onBindViewHolder(DecourverteViewHolder holder, int position) {
        holder.title.setText(data.get(position).getName());

        Context imageContext = holder.icon.getContext();
        int id = imageContext.getResources().getIdentifier(data.get(position).getImage(), "drawable",
                imageContext.getPackageName());
        holder.icon.setImageResource(id);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class DecourverteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView icon;
        public TextView title;
        public int id;

        private final String SHOP_ID = "id";

        public DecourverteViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            icon = (ImageView)itemView.findViewById(R.id.icon_decouverte_recycler_view_item);
            title = (TextView)itemView.findViewById(R.id.title_decouverte_recycler_view);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ShopViewActivity.class);
            intent.putExtra(SHOP_ID, data.get(getPosition()).getId());
            context.startActivity(intent);
        }
    }
}
