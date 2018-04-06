package com.maverick.medklick.others;

/**
 * Created by sudha on 22-Mar-18.
 */

public class HomecareRequests {

    public String hospitalName, fromDate, toDate, category;

    public HomecareRequests() {
    }

    public HomecareRequests(String hospitalName, String fromDate, String toDate, String category) {
        this.hospitalName = hospitalName;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.category = category;
    }
}
