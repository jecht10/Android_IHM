package com.baroghel.juliecentre.newview;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import com.baroghel.juliecentre.R;
import com.baroghel.juliecentre.datastore.DataBaseSQLite;
import com.baroghel.juliecentre.datastore.InscriptionBDD;

import java.util.Calendar;

/**
 * Created by User on 03/06/2017.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
    private String dateValue;
    private String timeValue;
    private int newsId;
    private String newsTitle;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), this,
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(getActivity()));
        timePickerDialog.setTitle("Reserver pour quelle heure ?");
        return timePickerDialog;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        timeValue = hourOfDay + "h" + minute;

        SmsManager smsManager = SmsManager.getDefault();
        String receiver = "0601206509";
        String body = "J'aimerai reserver un créneaux le " + dateValue + " à " + timeValue +
                " pour l'evenement " + newsTitle;
        smsManager.sendTextMessage(receiver, null, body, null, null);
        Toast.makeText(getActivity(), "Message envoyé.", Toast.LENGTH_LONG).show();

        InscriptionBDD inscriptionBDD = new InscriptionBDD(new DataBaseSQLite(getActivity()));
        inscriptionBDD.insertNews(newsId, dateValue, timeValue);
        inscriptionBDD.close();

        getActivity().findViewById(R.id.news_view_add_event).setVisibility(View.GONE);
    }

    public void setData(int newsId, String newsTitle, String dateValue){
        this.newsId = newsId;
        this.newsTitle = newsTitle;
        this.dateValue = dateValue;
    }
}
