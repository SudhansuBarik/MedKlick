package com.maverick.medklick.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.maverick.medklick.R;
import com.maverick.medklick.others.TeamMember;
import com.maverick.medklick.others.TeamMemberAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutTeamFragment extends Fragment {


    public AboutTeamFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_team, container, false);

        // Create a list of words
        final ArrayList<TeamMember> teamMemberArrayList = new ArrayList<>();

        teamMemberArrayList.add(new TeamMember(R.string.abhijeet_name, R.string.abhijeet_registration_number, R.drawable.mav_abhijeet));
        teamMemberArrayList.add(new TeamMember(R.string.reema_name, R.string.reema_registration_number, R.drawable.mav_reema));
        teamMemberArrayList.add(new TeamMember(R.string.sudhansu_name, R.string.sudhansu_registration_number, R.mipmap.ic_launcher));

        // Create an {@link NameAndDesignationAdapter}, whose data source is a list of {@link NameAndDesignation}s. The
        // adapter knows how to create list items for each item in the list.
        TeamMemberAdapter adapter = new TeamMemberAdapter(getActivity(), teamMemberArrayList);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // fragment_about_team.xmlam.xml layout file.
        ListView listView = view.findViewById(R.id.team_listView);

        // Make the {@link ListView} use the {@link NameAndDesignationAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link NameAndDesignation} in the list.
        listView.setAdapter(adapter);

        return view;
    }
}
