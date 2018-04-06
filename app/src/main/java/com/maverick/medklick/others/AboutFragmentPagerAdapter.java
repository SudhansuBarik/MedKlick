package com.maverick.medklick.others;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.maverick.medklick.fragments.AboutMavericksFragment;
import com.maverick.medklick.fragments.AboutMedKlickFragment;
import com.maverick.medklick.fragments.AboutTeamFragment;

/**
 * Created by sudha on 06-Mar-18.
 */

public class AboutFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final int FRAGMENT_COUNT = 3;

    public AboutFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AboutMavericksFragment();
            case 1:
                return new AboutTeamFragment();
            case 2:
                return new AboutMedKlickFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Mavericks";
            case 1:
                return "The Team";
            case 2:
                return "MedKlick";
        }
        return null;
    }
}
