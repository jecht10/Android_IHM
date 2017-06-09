package com.baroghel.juliecentre.reservations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.baroghel.juliecentre.MainFragment;
import com.baroghel.juliecentre.R;

/**
 * Created by User on 25/05/2017.
 */

public class ReservationFragment extends MainFragment{
    @Override
    public MainFragment newInstance() {
        return new ReservationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.reservation_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView reservationList = (ListView)view.findViewById(R.id.reservation_list);
        ReservationListAdapter adapter = new ReservationListAdapter(getActivity(), getResources());

        reservationList.setAdapter(adapter);
    }
}
