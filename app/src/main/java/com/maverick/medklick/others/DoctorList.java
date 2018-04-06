package com.maverick.medklick.others;

/**
 * Created by Sudhansu on 22-Mar-18.
 */

public class DoctorList {

    private int doctorNameId, doctorFeesId;

    public DoctorList(int doctorNameId, int doctorFeesId) {
        this.doctorNameId = doctorNameId;
        this.doctorFeesId = doctorFeesId;
    }

    /**
     * Get the string resource ID for the name of the team member
     */
    public int getDoctorNameId() {
        return doctorNameId;
    }

    /**
     * Get the string resource ID for the designation of the team member
     */
    public int getDoctorFeesId() {
        return doctorFeesId;
    }


}
