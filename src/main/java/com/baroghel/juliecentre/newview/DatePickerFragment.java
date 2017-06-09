package com.baroghel.juliecentre.newview;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by User on 03/06/2017.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    private String dateValue;
    private int newsId;
    private String newsTitle;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.setTitle("Reserver pour quel jour ?");
        return dialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        dateValue = dayOfMonth + "/" + month + "/" + year;

        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setData(newsId, newsTitle, dateValue);
        timePickerFragment.show(getActivity().getFragmentManager(), "TimePicker");
    }

    public void setData(int newsId, String newsTitle){
        this.newsId = newsId;
        this.newsTitle = newsTitle;
    }
}
