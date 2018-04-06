package com.maverick.medklick.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.maverick.medklick.activities.FindBedActivity;
import com.maverick.medklick.activities.ContactHospitalActivity;
import com.maverick.medklick.activities.DonateBloodActivity;
import com.maverick.medklick.activities.FindAmbulanceActivity;
import com.maverick.medklick.activities.FindDoctorActivity;
import com.maverick.medklick.activities.OtherServicesActivity;
import com.maverick.medklick.activities.PostQueryActivity;
import com.maverick.medklick.R;
import com.maverick.medklick.activities.RequestBloodActivity;
import com.maverick.medklick.activities.RequestHomecareActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private int[] myImageList = new int[]{R.drawable.doctor, R.drawable.bed,
            R.drawable.homecare, R.drawable.blood, R.drawable.ambulance};

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

//        // Spinner element
//        Spinner spinner = view.findViewById(R.id.spinner);
//
//        // Spinner dropdown elements
//        String[] hospitals =
//                {"Choose Hospital", "R.G.H.", "I.G.H.", "Life Line", "Vesaj Patel", "K.T.H."};
//
//        // Creating adapter for spinner
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, hospitals);
//
//        // Drop down layout style - list view with radio button
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // attaching data adapter to spinner
//        spinner.setAdapter(dataAdapter);
//
//        // Set what would happen when an option is selected in the spinner
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int position, long id) {
//
//                switch (position) {
//                    case 1:
//                        Toast.makeText(parent.getContext(), "Selected: R.G.H.", Toast.LENGTH_LONG).show();
//                        break;
//                    case 2:
//                        Toast.makeText(parent.getContext(), "Selected: I.G.H.", Toast.LENGTH_LONG).show();
//                        break;
//                    case 3:
//                        Toast.makeText(parent.getContext(), "Selected: Life Line ", Toast.LENGTH_LONG).show();
//                        break;
//                    case 4:
//                        Toast.makeText(parent.getContext(), "Selected: Vesaj Patel", Toast.LENGTH_LONG).show();
//                        break;
//                    case 5:
//                        Toast.makeText(parent.getContext(), "Selected: K.T.H.", Toast.LENGTH_LONG).show();
//                        break;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Get id of Find a Doctor Linear Layout
        final FrameLayout findDoctor = getView().findViewById(R.id.find_doctor);

        //Set a click listener on that layout
        if (findDoctor != null) {
            findDoctor.setOnClickListener(new View.OnClickListener() {
                // The code in this method will be executed when the Find a Doctor View is clicked on.
                @Override
                public void onClick(View view) {
                    //Create a new intent to open the {@link FindDoctorActivity}
                    Intent findDoctorIntent = new Intent(HomeFragment.this.getActivity(), FindDoctorActivity.class);
                    //Start new activity
                    getActivity().startActivity(findDoctorIntent);
                }
            });
        }

        //Get id of Book a Bed Linear Layout
        final FrameLayout bookBed = getView().findViewById(R.id.find_bed);

        //Set a click listener on that layout
        if (bookBed != null) {
            bookBed.setOnClickListener(new View.OnClickListener() {
                // The code in this method will be executed when the Book a Bed View is clicked on.
                @Override
                public void onClick(View view) {
                    //Create a new intent to open the {@link FindBedActivity}
                    Intent bookBedIntent = new Intent(HomeFragment.this.getActivity(), FindBedActivity.class);
                    //Start new activity
                    getActivity().startActivity(bookBedIntent);
                }
            });
        }

        //Get id of Request Homecare Linear Layout
        final FrameLayout requestHomecare = getView().findViewById(R.id.request_homecare);

        //Set a click listener on that layout
        if (requestHomecare != null) {
            requestHomecare.setOnClickListener(new View.OnClickListener() {
                // The code in this method will be executed when the Request Blood View is clicked on.
                @Override
                public void onClick(View view) {
                    //Create a new intent to open the {@link RequestHomecareActivity}
                    Intent requestHomecareIntent = new Intent(HomeFragment.this.getActivity(), RequestHomecareActivity.class);
                    //Start new activity
                    getActivity().startActivity(requestHomecareIntent);
                }
            });
        }

        //Get id of Donate Blood Linear Layout
        final FrameLayout donateBlood = getView().findViewById(R.id.donate_blood);

        //Set a click listener on that layout
        if (donateBlood != null) {
            donateBlood.setOnClickListener(new View.OnClickListener() {
                // The code in this method will be executed when the Donate Blood View is clicked on.
                @Override
                public void onClick(View view) {
                    //Create a new intent to open the {@link DonateBloodActivity}
                    Intent donateBloodIntent = new Intent(HomeFragment.this.getActivity(), DonateBloodActivity.class);
                    //Start new activity
                    getActivity().startActivity(donateBloodIntent);
                }
            });
        }

        //Get id of Request Blood Linear Layout
        final FrameLayout requestBlood = getView().findViewById(R.id.request_blood);

        //Set a click listener on that layout
        if (requestBlood != null) {
            requestBlood.setOnClickListener(new View.OnClickListener() {
                // The code in this method will be executed when the Request Blood View is clicked on.
                @Override
                public void onClick(View view) {
                    //Create a new intent to open the {@link RequestBloodActivity}
                    Intent requestBloodIntent = new Intent(HomeFragment.this.getActivity(), RequestBloodActivity.class);
                    //Start new activity
                    getActivity().startActivity(requestBloodIntent);
                }
            });
        }

        //Get id of Find an Ambulance Linear Layout
        final FrameLayout findAmbulance = getView().findViewById(R.id.find_ambulance);

        //Set a click listener on that layout
        if (findAmbulance != null) {
            findAmbulance.setOnClickListener(new View.OnClickListener() {
                // The code in this method will be executed when the Find an Ambulance View is clicked on.
                @Override
                public void onClick(View view) {
                    //Create a new intent to open the {@link FindAmbulanceActivity}
                    Intent findAmbulanceIntent = new Intent(HomeFragment.this.getActivity(), FindAmbulanceActivity.class);
                    //Start new activity
                    getActivity().startActivity(findAmbulanceIntent);
                }
            });
        }

        //Get id of Contact Hospital Linear Layout
        final FrameLayout contactHospital = getView().findViewById(R.id.contact_hospital);

        //Set a click listener on that layout
        if (contactHospital != null) {
            contactHospital.setOnClickListener(new View.OnClickListener() {
                // The code in this method will be executed when the Contact Hospital View is clicked on.
                @Override
                public void onClick(View view) {
                    //Create a new intent to open the {@link ContactHospitalActivity}
                    Intent contactHospitalIntent = new Intent(HomeFragment.this.getActivity(), ContactHospitalActivity.class);
                    //Start new activity
                    getActivity().startActivity(contactHospitalIntent);
                }
            });
        }

        //Get id of Post a Query Linear Layout
        final FrameLayout postQuery = getView().findViewById(R.id.post_query);

        //Set a click listener on that layout
        if (postQuery != null) {
            postQuery.setOnClickListener(new View.OnClickListener() {
                // The code in this method will be executed when the Post a Query View is clicked on.
                @Override
                public void onClick(View view) {
                    //Create a new intent to open the {@link PostQueryActivity}
                    Intent postQueryIntent = new Intent(HomeFragment.this.getActivity(), PostQueryActivity.class);
                    //Start new activity
                    getActivity().startActivity(postQueryIntent);
                }
            });
        }

        //Get id of Other Services Linear Layout
        final FrameLayout otherServices = getView().findViewById(R.id.other_services);

        //Set a click listener on that layout
        if (otherServices != null) {
            otherServices.setOnClickListener(new View.OnClickListener() {
                // The code in this method will be executed when the Other Services View is clicked on.
                @Override
                public void onClick(View view) {
                    //Create a new intent to open the {@link OtherServicesActivity}
                    Intent otherServicesIntent = new Intent(HomeFragment.this.getActivity(), OtherServicesActivity.class);
                    //Start new activity
                    getActivity().startActivity(otherServicesIntent);
                }
            });
        }
    }
}
