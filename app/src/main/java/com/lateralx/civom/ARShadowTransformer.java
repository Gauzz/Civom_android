package com.lateralx.civom;

import androidx.viewpager.widget.ViewPager;
import androidx.cardview.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.lateralx.civom.Adapter.ARCardAdapter;

public class ARShadowTransformer implements ViewPager.OnPageChangeListener, ViewPager.PageTransformer {

    private ViewPager viewPager;
    private ViewInARActivity viar;
    private com.lateralx.civom.Adapter.ARCardAdapter ARCardAdapter;
    private float lastOffset;
    private boolean scalingEnabled;
    private int mScrollState;
    private int curitem;

    public ARShadowTransformer(ViewPager viewPager, ARCardAdapter adapter) {
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(this);
        ARCardAdapter = adapter;

    }

    public void enableScaling(boolean enable) {
        if (scalingEnabled && !enable) {
            // shrink main card
            CardView currentCard = ARCardAdapter.getCardViewAt(viewPager.getCurrentItem());
            if (currentCard != null) {
                currentCard.animate().scaleY(1);
                currentCard.animate().scaleX(1);
            }
        }else if(!scalingEnabled && enable){
            // grow main card
            CardView currentCard = ARCardAdapter.getCardViewAt(viewPager.getCurrentItem());
            if (currentCard != null) {
                //enlarge the current item
                currentCard.animate().scaleY(1.1f);
                currentCard.animate().scaleX(1.1f);
            }
        }
        scalingEnabled = enable;
    }

    @Override
    public void transformPage(View page, float position) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int realCurrentPosition;
        int nextPosition;
        float baseElevation = ARCardAdapter.getBaseElevation();
        float realOffset;
        boolean goingLeft = lastOffset > positionOffset;

        // If we're going backwards, onPageScrolled receives the last position
        // instead of the current one
        if (goingLeft) {
            realCurrentPosition = position + 1;
            nextPosition = position;
            realOffset = 1 - positionOffset;
        } else {
            nextPosition = position + 1;
            realCurrentPosition = position;
            realOffset = positionOffset;
        }

        // Avoid crash on overscroll
        if (nextPosition > ARCardAdapter.getCount() - 1
                || realCurrentPosition > ARCardAdapter.getCount() - 1) {
            return;
        }

        CardView currentCard = ARCardAdapter.getCardViewAt(realCurrentPosition);

        // This might be null if a fragment is being used
        // and the views weren't created yet
        if (currentCard != null) {
            if (scalingEnabled) {
                currentCard.setScaleX((float) (1 + 0.1 * (1 - realOffset)));
                currentCard.setScaleY((float) (1 + 0.1 * (1 - realOffset)));
            }
            currentCard.setCardElevation((baseElevation + baseElevation
                    * (ARCardAdapter.MAX_ELEVATION_FACTOR - 1) * (1 - realOffset)));
        }

        CardView nextCard = ARCardAdapter.getCardViewAt(nextPosition);

        // We might be scrolling fast enough so that the next (or previous) card
        // was already destroyed or a fragment might not have been created yet
        if (nextCard != null) {
            if (scalingEnabled) {
                nextCard.setScaleX((float) (1 + 0.1 * (realOffset)));
                nextCard.setScaleY((float) (1 + 0.1 * (realOffset)));
            }
            nextCard.setCardElevation((baseElevation + baseElevation
                    * (ARCardAdapter.MAX_ELEVATION_FACTOR - 1) * (realOffset)));
        }

        lastOffset = positionOffset;
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {


            curitem = viewPager.getCurrentItem();

            Toast toasts =
                    Toast.makeText(viewPager.getContext(),String.valueOf(curitem), Toast.LENGTH_LONG);
            toasts.setGravity(Gravity.CENTER, 0, 0);
            toasts.show();
            handleScrollState(state);
            mScrollState = state;
            View v=viewPager.getChildAt(viewPager.getCurrentItem());
            viar.downModel(v);

        //       viar.selectModel(curitem);
    }
    private void handleScrollState(final int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            setNextItemIfNeeded();
        }
    }

    private void setNextItemIfNeeded() {
        if (!isScrollStateSettling()) {
            handleSetNextItem();
        }
    }

    private boolean isScrollStateSettling() {
        return mScrollState == ViewPager.SCROLL_STATE_SETTLING;
    }

    private void handleSetNextItem() {
        final int lastPosition = viewPager.getAdapter().getCount() - 1;
        if (curitem == 0) {
            viewPager.setCurrentItem(lastPosition, false);
        } else if (curitem == lastPosition) {
            viewPager.setCurrentItem(0, false);
        }
    }


    public void setArModel(ViewInARActivity viewInARActivity) {
        viar = viewInARActivity;

    }
}