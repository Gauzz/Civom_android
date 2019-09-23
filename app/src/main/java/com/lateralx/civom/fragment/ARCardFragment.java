package com.lateralx.civom.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lateralx.civom.Adapter.ARCardAdapter;
import com.lateralx.civom.Model.RetroPhoto;
import com.lateralx.civom.Network.RetrofitClientInstance;
import com.lateralx.civom.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


public class ARCardFragment extends Fragment {

    private CardView cardView;

    public static Fragment getInstance(int position, List<RetroPhoto> lrp) {
        ARCardFragment f = new ARCardFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("thumbnail",lrp.get(position).getThumbnail_compressed());
        args.putString("modelurl",lrp.get(position).getFbx());
        f.setArguments(args);

        return f;
    }

    @SuppressLint("DefaultLocale")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_viewpager, container, false);
        ImageLoader imageLoader = ImageLoader.getInstance();


        cardView = (CardView) view.findViewById(R.id.cardView);
        cardView.setMaxCardElevation(cardView.getCardElevation() * ARCardAdapter.MAX_ELEVATION_FACTOR);
        ImageView modelimg = (ImageView) view.findViewById(R.id.modelimg);
        int i= getArguments().getInt("position");
        TextView t = view.findViewById(R.id.textView6);
        t.setText(getArguments().getString("modelurl"));
        if(getArguments().getString("thumbnail")!= null && getArguments().getString("thumbnail")!= "") {
            imageLoader.displayImage(RetrofitClientInstance.BASE_URL +"/" +getArguments().getString("thumbnail"), modelimg);
        }
        else
        {
            imageLoader.displayImage(String.valueOf(R.drawable.product_storychair3x), modelimg);
        }
        return view;
    }

    public CardView getCardView() {
        return cardView;
    }
}