package com.maverick.medklick.others;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maverick.medklick.R;

import java.util.ArrayList;

/**
 * Created by MyPc on 03-May-17.
 */

public class TeamMemberAdapter extends ArrayAdapter<TeamMember> {

    public TeamMemberAdapter(Context context, ArrayList<TeamMember> teamMembers) {
        super(context, 0, teamMembers);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Get the {@link TeamMember} object present at this location
        TeamMember currentTeamMember = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID name_text_view.
        TextView nameTextView = listItemView.findViewById(R.id.ambulance_name_text_view);
        // Get the Name from the currentTeamMember object and set this text on
        // the Name TextView
        nameTextView.setText(currentTeamMember.getmNameId());

        // Find the TextView in the list_item.xml layout with the ID designation_text_view.
        TextView defaultTextView = listItemView.findViewById(R.id.registration_number_text_view);
        // Get the default translation from the currentTeamMember object and set this text on
        // the Designation TextView.
        defaultTextView.setText(currentTeamMember.getmDesignationId());

        // Find the ImageView in the list_item.xml layout with the ID image.
        ImageView imageView = listItemView.findViewById(R.id.image);
        // Check if an image is provided for the name or not
        if (currentTeamMember.hasImage()) {
            // If an image is available, display the provided image based on the resource ID
            imageView.setImageResource(currentTeamMember.getmImageResourceId());
            // Make sure the view is visible
            imageView.setVisibility(View.VISIBLE);
        } else {
            // Otherwise hide the ImageView (set visibility to GONE)
            imageView.setVisibility(View.GONE);
        }

        // Set the theme color for the list item
        View textContainer = listItemView.findViewById(R.id.text_container);
        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), R.color.team_background_color);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(color);

        return listItemView;
    }
}
