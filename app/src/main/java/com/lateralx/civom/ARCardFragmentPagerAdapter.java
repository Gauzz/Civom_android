package com.lateralx.civom;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ARCardFragmentPagerAdapter extends FragmentStatePagerAdapter implements ARCardAdapter {

    private List<ARCardFragment> fragments;
    private float baseElevation;

    public ARCardFragmentPagerAdapter(FragmentManager fm, float baseElevation) {
        super(fm);
        fragments = new ArrayList<>();
        this.baseElevation = baseElevation;

        for(int i = 0; i< 8; i++){
            addCardFragment(new ARCardFragment());

        }
    }

    @Override
    public float getBaseElevation() {
        return baseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return fragments.get(position).getCardView();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return ARCardFragment.getInstance(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        fragments.set(position, (ARCardFragment) fragment);
        return fragment;
    }

    public void addCardFragment(ARCardFragment fragment) {
        fragments.add(fragment);
    }

    @Override
    public float getPageWidth(int position) {
        return (0.4f);
    }


}