package com.lateralx.civom;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class ARCardFragment extends Fragment {

    private CardView cardView;

    public static Fragment getInstance(int position) {
        ARCardFragment f = new ARCardFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        f.setArguments(args);

        return f;
    }

    @SuppressLint("DefaultLocale")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_viewpager, container, false);

        cardView = (CardView) view.findViewById(R.id.cardView);
        cardView.setMaxCardElevation(cardView.getCardElevation() * ARCardAdapter.MAX_ELEVATION_FACTOR);
        ImageView modelimg = (ImageView) view.findViewById(R.id.modelimg);
        int i= getArguments().getInt("position");
        if(i==0)modelimg.setImageResource(R.drawable.cube);
        if(i==1)modelimg.setImageResource(R.drawable.hexagonottoman);
        if(i==2)modelimg.setImageResource(R.drawable.commaottoman);
        if(i==3)modelimg.setImageResource(R.drawable.cornerottoman);
        if(i==4)modelimg.setImageResource(R.drawable.kidneyottoman);
        if(i==5)modelimg.setImageResource(R.drawable.crescentottoman);

        return view;
    }

    public CardView getCardView() {
        return cardView;
    }
}