package com.baroghel.juliecentre.reservations;

/**
 * Created by User on 27/05/2017.
 */

public class ReservationListItem {
    private int id;
    private String dateReservation;
    private String heureReservation;

    public ReservationListItem(int id, String dateReservation, String heureReservation){
        this.id = id;
        this.dateReservation = dateReservation;
        this.heureReservation = heureReservation;
    }

    public int getId() {
        return id;
    }

    public String getDateReservation() {
        return dateReservation;
    }

    public String getHeureReservation() {
        return heureReservation;
    }
}
