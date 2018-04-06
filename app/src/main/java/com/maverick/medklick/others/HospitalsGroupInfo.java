package com.maverick.medklick.others;

import java.util.ArrayList;

/**
 * Created by sudha on 21-Mar-18.
 */

public class HospitalsGroupInfo {

    private String hospitalName;
    private ArrayList<HospitalsChildInfo> list = new ArrayList<HospitalsChildInfo>();

    public String getName() {
        return hospitalName;
    }

    public void setName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public ArrayList<HospitalsChildInfo> getContactDetails() {
        return list;
    }

    public void setContactDetails(ArrayList<HospitalsChildInfo> contactDetails) {
        this.list = contactDetails;
    }

}
