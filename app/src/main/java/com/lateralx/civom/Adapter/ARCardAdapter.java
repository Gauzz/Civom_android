package com.lateralx.civom.Adapter;

import androidx.cardview.widget.CardView;

public interface ARCardAdapter {

    public final int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}
