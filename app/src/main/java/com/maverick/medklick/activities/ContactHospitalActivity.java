package com.maverick.medklick.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;

import com.maverick.medklick.R;
import com.maverick.medklick.others.CustomHospitalsInfoAdapter;
import com.maverick.medklick.others.HospitalsChildInfo;
import com.maverick.medklick.others.HospitalsGroupInfo;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ContactHospitalActivity extends AppCompatActivity {

    private LinkedHashMap<String, HospitalsGroupInfo> hospital = new LinkedHashMap<String, HospitalsGroupInfo>();
    private ArrayList<HospitalsGroupInfo> deptList = new ArrayList<HospitalsGroupInfo>();
    private CustomHospitalsInfoAdapter listAdapter;
    private ExpandableListView simpleExpandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_hospital);

        // add data for displaying in expandable list view
        loadData();

        //get reference of the ExpandableListView
        simpleExpandableListView = findViewById(R.id.simpleExpandableListView);

        // create the adapter by passing your ArrayList data
        listAdapter = new CustomHospitalsInfoAdapter(ContactHospitalActivity.this, deptList);

        // attach the adapter to the expandable list view
        simpleExpandableListView.setAdapter(listAdapter);

        // setOnChildClickListener listener for child row click
        simpleExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //get the group header
                HospitalsGroupInfo headerInfo = deptList.get(groupPosition);
                //get the child info
                HospitalsChildInfo detailInfo = headerInfo.getContactDetails().get(childPosition);
                //display it or do something with it
//                Toast.makeText(getBaseContext(), " Hospital And Contact :: " + headerInfo.getName()
//                        + "/" + detailInfo.getName(), Toast.LENGTH_LONG).show();
                return false;
            }
        });

        // setOnGroupClickListener listener for group heading click
        simpleExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //get the group header
                HospitalsGroupInfo headerInfo = deptList.get(groupPosition);
                //display it or do something with it
//                Toast.makeText(getBaseContext(), " Hospital Name :: " + headerInfo.getName(),
//                        Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    // load some initial data into out list
    private void loadData() {
        addHospital("Hi-Tech Medical College and Hospital", "Near Hanuman Vatika, R.G.H. Campus, Rourkela - 769004\nPhone: +91-9437016222");
        addHospital("Ispat General Hospital", "Sector-19, Rourkela - 769005\nPhone: 0661-2646202");
        addHospital("LifeLine Hospital", "D/10, Civil Township, Rourkela - 769004\nPhone: +91-9437352131");
        addHospital("Shanti Memorial Hospital", "Panposh Road, Udit Nagar, Rourkela - 769012\nPhone: +91-9238100216");
        addHospital("Vesaj Patel Hospital", "H/4, Civil Township, Rourkela - 769004\nPhone: 0661-2503261");
    }

    // here we maintain hospital name and contact details
    private int addHospital(String hospitalName, String contactDetails) {
        int groupPosition = 0;
        //check the hash map if the group already exists
        HospitalsGroupInfo headerInfo = hospital.get(hospitalName);
        //add the group if doesn't exists
        if (headerInfo == null) {
            headerInfo = new HospitalsGroupInfo();
            headerInfo.setName(hospitalName);
            hospital.put(hospitalName, headerInfo);
            deptList.add(headerInfo);
        }
        // get the children for the group
        ArrayList<HospitalsChildInfo> productList = headerInfo.getContactDetails();
        // size of the children list
        int listSize = productList.size();
        // add to the counter
        listSize++;
        // create a new child and add that to the group
        HospitalsChildInfo detailInfo = new HospitalsChildInfo();
        detailInfo.setName(contactDetails);
        productList.add(detailInfo);
        headerInfo.setContactDetails(productList);
        // find the group position inside the list
        groupPosition = deptList.indexOf(headerInfo);
        return groupPosition;
    }
}
