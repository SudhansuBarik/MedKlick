package com.maverick.medklick.others;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.maverick.medklick.R;

import java.util.ArrayList;

/**
 * Created by sudha on 21-Mar-18.
 */

public class CustomHospitalsInfoAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<HospitalsGroupInfo> hospitalName;

    public CustomHospitalsInfoAdapter(Context context, ArrayList<HospitalsGroupInfo> hospitalList) {
        this.context = context;
        this.hospitalName = hospitalList;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<HospitalsChildInfo> contactList = hospitalName.get(groupPosition).getContactDetails();
        return contactList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View view, ViewGroup parent) {

        HospitalsChildInfo detailInfo = (HospitalsChildInfo) getChild(groupPosition, childPosition);

        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.child_items, null);
        }

        TextView childItem = view.findViewById(R.id.childItem);
        childItem.setText(detailInfo.getName().trim());

        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<HospitalsChildInfo> productList = hospitalName.get(groupPosition).getContactDetails();
        return productList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return hospitalName.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return hospitalName.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view,
                             ViewGroup parent) {

        HospitalsGroupInfo headerInfo = (HospitalsGroupInfo) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.group_items, null);
        }

        TextView heading = (TextView) view.findViewById(R.id.heading);
        heading.setText(headerInfo.getName().trim());

        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
