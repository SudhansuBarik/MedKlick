package com.maverick.medklick.others;

/**
 * Created by sudha on 21-Mar-18.
 */

public class BloodRequests {

    public String bloodGroup, unitsOfBlood, dateOfRequirement;

    BloodRequests() {
        // Default Constructor
    }

    public BloodRequests(String bloodGroup, String unitsOfBlood, String dateOfRequirement) {
        this.bloodGroup = bloodGroup;
        this.unitsOfBlood = unitsOfBlood;
        this.dateOfRequirement = dateOfRequirement;
    }
}
