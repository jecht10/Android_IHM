package com.baroghel.juliecentre.reservations;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baroghel.juliecentre.R;
import com.baroghel.juliecentre.data.News;
import com.baroghel.juliecentre.datastore.DataBaseSQLite;
import com.baroghel.juliecentre.datastore.InscriptionBDD;
import com.baroghel.juliecentre.datastore.NewsBDD;

import java.util.List;

/**
 * Created by User on 27/05/2017.
 */

public class ReservationListAdapter extends BaseAdapter{
    private Context context;
    private List<ReservationListItem> data;
    private LayoutInflater inflater;
    private Resources resources;

    private final int ANIMATION_DURATION = 200;

    public ReservationListAdapter(Context context, Resources resources){
        this.context = context;
        this.resources = resources;

        this.data = refreshData();
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private void removeReservation(int id){
        for(ReservationListItem reservation : data){
            if(reservation.getId() == id) {
                data.remove(reservation);
                break;
            }
        }
        notifyDataSetChanged();
        DataBaseSQLite dataBaseSQLite = new DataBaseSQLite(context);
        InscriptionBDD inscriptionBDD = new InscriptionBDD(dataBaseSQLite);
        inscriptionBDD.removeNews(id);
        inscriptionBDD.close();
    }

    private List<ReservationListItem> refreshData(){
        DataBaseSQLite dataBaseSQLite = new DataBaseSQLite(context);
        InscriptionBDD inscriptionBDD = new InscriptionBDD(dataBaseSQLite);
        List<ReservationListItem> reservationListItems = inscriptionBDD.getAllReservations();
        inscriptionBDD.close();
        return reservationListItems;
    }

    private void collapse(final View v, Animation.AnimationListener al){
        final int initialHeight = v.getMeasuredHeight();

        Animation animation = new Animation() {
            @Override
            public boolean willChangeBounds() {
                return true;
            }

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else {
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }
        };

        if(al != null){
            animation.setAnimationListener(al);
        }
        animation.setDuration(ANIMATION_DURATION);
        v.startAnimation(animation);
    }

    private void deleteCell(final View v, final int id){
        Animation.AnimationListener al = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ViewHolder holder = (ViewHolder)v.getTag();
                holder.needInflate = true;

                removeReservation(id);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };

        collapse(v, al);
    }

    private News getNewsWithId(int id){
        DataBaseSQLite dataBaseSQLite = new DataBaseSQLite(context);
        NewsBDD newsBDD = new NewsBDD(dataBaseSQLite);
        News news = newsBDD.getNewsWithId(id);
        newsBDD.close();
        return news;
    }

    @Override
    public int getCount() {
        if(data != null)
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
    public View getView(int position, final View convertView, ViewGroup parent) {
        final View v;
        ViewHolder holder;

        if(convertView == null){
            v = inflater.inflate(R.layout.reservation_list_item, parent, false);
            holder = new ViewHolder();
            holder.icon = (ImageView)v.findViewById(R.id.icon_reservation_item);
            holder.title = (TextView)v.findViewById(R.id.title_reservation_item);
            holder.date = (TextView)v.findViewById(R.id.date_reservation_item);
            holder.delete = (ImageButton)v.findViewById(R.id.button_reservation_item);

            v.setTag(holder);
        }else if(((ViewHolder)convertView.getTag()).needInflate){
            v = inflater.inflate(R.layout.reservation_list_item, parent, false);
            holder = new ViewHolder();
            holder.icon = (ImageView)v.findViewById(R.id.icon_reservation_item);
            holder.title = (TextView)v.findViewById(R.id.title_reservation_item);
            holder.date = (TextView)v.findViewById(R.id.date_reservation_item);
            holder.delete = (ImageButton) v.findViewById(R.id.button_reservation_item);

            v.setTag(holder);
        } else {
            v = convertView;
            holder = (ViewHolder)v.getTag();
        }

        final ReservationListItem item = (ReservationListItem)getItem(position);
        final News news = getNewsWithId(item.getId());

        Context imageContext = holder.icon.getContext();
        int idImage = imageContext.getResources().getIdentifier(news.getImage(), "drawable", imageContext.getPackageName());
        holder.icon.setImageResource(idImage);

        holder.title.setText(news.getTitre());
        holder.date.setText(item.getDateReservation() + " " + item.getHeureReservation());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Annuler le rendez-vous " + news.getTitre() + " ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SmsManager smsManager = SmsManager.getDefault();
                        String receiver = "0601206509";
                        String body = "Je souhaiterai annuler le rendez-vous du " + item.getDateReservation()
                                + " à " + item.getHeureReservation() + " pour l'evenement " + news.getTitre();
                        smsManager.sendTextMessage(receiver, null, body, null, null);
                        Toast.makeText(context, "Message envoyé.", Toast.LENGTH_LONG).show();
                        deleteCell(v, item.getId());
                    }
                });
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        return v;
    }

    private class ViewHolder{
        public boolean needInflate = false;
        public ImageView icon;
        public TextView title;
        public TextView date;
        public ImageButton delete;
    }
}
