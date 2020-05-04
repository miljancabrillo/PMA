package com.pma.adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.pma.R;
import com.pma.fragments.ActivitiesMealsPreviewFragment;
import com.pma.fragments.AddMealGroceriesFragment;
import com.pma.fragments.AddMealPreviewFragment;
import com.pma.fragments.MyMapFragment;

public class AddMealPagerAdapter extends FragmentPagerAdapter {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_title3, R.string.tab_title4};
    private final Context mContext;

    public AddMealPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if (position == 0) return new AddMealGroceriesFragment();
        else return new AddMealPreviewFragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}
