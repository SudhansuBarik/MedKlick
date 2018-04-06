package com.maverick.medklick.others;

/**
 * Created by MyPc on 03-May-17.
 */

public class TeamMember {

    private int mNameId;

    private int mDesignationId;

    private int mImageResourceId = NO_IMAGE_PROVIDED;

    /**
     * Constant value that represents no image was provided for this word
     */
    private static final int NO_IMAGE_PROVIDED = -1;

    /***
     * Create a new TeamMember object
     *
     * @param nameId is a String Resource ID for the Name of the Team Member
     * @param designationId is a String Resource ID for the Designation of he Team Member
     * @param imageResourceId is the Drawable Resource ID for the image associated with each Name
     */
    public TeamMember(int nameId, int designationId, int imageResourceId) {
        mNameId = nameId;
        mDesignationId = designationId;
        mImageResourceId = imageResourceId;
    }

    /**
     * Get the string resource ID for the name of the team member
     */
    public int getmNameId() {
        return mNameId;
    }

    /**
     * Get the string resource ID for the designation of the team member
     */
    public int getmDesignationId() {
        return mDesignationId;
    }

    /***
     * Get the image resource ID for the image associated with the name
     */
    public int getmImageResourceId() {
        return mImageResourceId;
    }

    /**
     * Returns whether or not there is an image for this word.
     */
    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

}
