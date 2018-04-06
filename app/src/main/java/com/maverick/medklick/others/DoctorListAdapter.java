package com.maverick.medklick.others;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.maverick.medklick.R;

import java.util.ArrayList;

/**
 * Created by sudha on 23-Mar-18.
 */

public class DoctorListAdapter extends ArrayAdapter<DoctorList> {

    public DoctorListAdapter(Context context, ArrayList<DoctorList> doctorAppointments) {
        super(context, 0, doctorAppointments);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View doctorListItemView = convertView;
        if (doctorListItemView == null) {
            doctorListItemView = LayoutInflater.from(getContext()).inflate(R.layout.select_doctor_list_item, parent, false);
        }

        // Get the {@link DoctorAppointment} object present at this location
        DoctorList currentDoctorList = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID name_text_view.
        TextView doctorNameTextView = doctorListItemView.findViewById(R.id.doctor_name_textView);
        // Get the Name from the currentTeamMember object and set this text on
        // the Name TextView
        doctorNameTextView.setText(currentDoctorList.getDoctorNameId());

        // Find the TextView in the list_item.xml layout with the ID designation_text_view.
        TextView doctorFeeTextView = doctorListItemView.findViewById(R.id.doctor_fee_textView);
        // Get the default translation from the currentTeamMember object and set this text on
        // the Designation TextView.
        doctorFeeTextView.setText(currentDoctorList.getDoctorFeesId());

        // Set the theme color for the list item
        View textContainer = doctorListItemView.findViewById(R.id.doctor_list_container);
        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), R.color.team_background_color);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(color);

        return doctorListItemView;
    }
}
