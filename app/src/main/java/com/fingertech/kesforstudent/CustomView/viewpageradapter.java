package com.fingertech.kesforstudent.CustomView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class viewpageradapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragment = new ArrayList<>();
    private final List<String> mFragmentTittlelist = new ArrayList<>();


    public viewpageradapter(FragmentManager fm){
        super(fm);

    }

    @Override
    public Fragment getItem(int i) {
        return mFragment.get(i);
    }


    @Override
    public int getCount() {
        return mFragmentTittlelist.size();
    }

    public void addFragment (Fragment fragment,String title){
        mFragment.add(fragment);
        mFragmentTittlelist.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTittlelist.get(position);
    }
}
