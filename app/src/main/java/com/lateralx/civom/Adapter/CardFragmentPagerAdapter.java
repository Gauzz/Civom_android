package com.lateralx.civom.Adapter;

import android.app.ProgressDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.cardview.widget.CardView;

import android.view.ViewGroup;

import com.lateralx.civom.Adapter.CardAdapter;
import com.lateralx.civom.Model.RetroPhoto;
import com.lateralx.civom.fragment.CardFragment;

import java.util.ArrayList;
import java.util.List;

public class CardFragmentPagerAdapter extends FragmentStatePagerAdapter implements CardAdapter {

    private List<CardFragment> fragments;
    private float baseElevation;
    ProgressDialog progressDoalog;
    private List<RetroPhoto> lp;

    public CardFragmentPagerAdapter(FragmentManager fm, float baseElevation, List<RetroPhoto> lp) {
        super(fm);
        fragments = new ArrayList<>();
        this.baseElevation = baseElevation;
        this.lp = lp;
        for(int i = 0; i< lp.size(); i++){

            addCardFragment(new CardFragment());
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
        return CardFragment.getInstance(position,lp);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        fragments.set(position, (CardFragment) fragment);
        return fragment;
    }

    public void addCardFragment(CardFragment fragment) {
        fragments.add(fragment);
    }

}
