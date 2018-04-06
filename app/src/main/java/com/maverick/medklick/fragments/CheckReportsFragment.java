package com.maverick.medklick.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maverick.medklick.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CheckReportsFragment extends Fragment {


    public CheckReportsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_reports, container, false);
    }

}
