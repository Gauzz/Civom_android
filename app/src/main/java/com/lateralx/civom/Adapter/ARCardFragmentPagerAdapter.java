package com.lateralx.civom.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.cardview.widget.CardView;
import android.view.ViewGroup;

import com.lateralx.civom.Adapter.ARCardAdapter;
import com.lateralx.civom.Model.RetroPhoto;
import com.lateralx.civom.fragment.ARCardFragment;

import java.util.ArrayList;
import java.util.List;

public class ARCardFragmentPagerAdapter extends FragmentStatePagerAdapter implements ARCardAdapter {

    private List<ARCardFragment> fragments;
    private float baseElevation;
    private List<RetroPhoto> lrp;
    public ARCardFragmentPagerAdapter(FragmentManager fm, float baseElevation, List<RetroPhoto> lrp) {
        super(fm);
        fragments = new ArrayList<>();
        this.baseElevation = baseElevation;
        this.lrp=lrp;
        for(int i = 0; i< lrp.size(); i++){
          if(lrp.get(i).getFbx()!= null && lrp.get(i).getFbx() != "")
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
        return ARCardFragment.getInstance(position,lrp);
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
        return (0.3f);
    }


}