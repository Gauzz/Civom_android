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

import com.lateralx.civom.Adapter.CardAdapter;
import com.lateralx.civom.Model.RetroPhoto;
import com.lateralx.civom.Network.RetrofitClientInstance;
import com.lateralx.civom.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


public class CardFragment extends Fragment {

    private CardView cardView;
    private String name;
    private List<RetroPhoto> lp;
    private int pos;

    public static Fragment getInstance(int position, List<RetroPhoto> lp) {
        CardFragment f = new CardFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("description",lp.get(position).getDescription());
        args.putString("name",lp.get(position).getName());
        args.putString("thumbnail",lp.get(position).getThumbnail_original());
        args.putInt("pid",lp.get(position).getId());
        args.putString("ar",lp.get(position).getFbx());
        f.setArguments(args);

        return f;
    }

    @SuppressLint("DefaultLocale")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ImageLoader imageLoader = ImageLoader.getInstance();

        View view = inflater.inflate(R.layout.item_productviewpager, container, false);


        cardView = (CardView) view.findViewById(R.id.productcard);
        cardView.setMaxCardElevation(cardView.getCardElevation() * CardAdapter.MAX_ELEVATION_FACTOR);

        TextView title = (TextView) view.findViewById(R.id.titlecard);
        pos = getArguments().getInt("position");
        title.setText(getArguments().getString("name"));
        TextView description = view.findViewById(R.id.productdesc);
        description.setText(getArguments().getString("description"));
        TextView thumbnailimg = view.findViewById(R.id.textthumbnail);
        thumbnailimg.setText(getArguments().getString("thumbnail"));
        TextView textar = view.findViewById(R.id.productar);
        textar.setText(getArguments().getString("ar"));

        ImageView productimage = view.findViewById(R.id.productimage);

        if(getArguments().getString("thumbnail")!= null && getArguments().getString("thumbnail")!= "") {
            imageLoader.displayImage(RetrofitClientInstance.BASE_URL +"/" +getArguments().getString("thumbnail"), productimage);
        }
        else
        {
            imageLoader.displayImage(String.valueOf(R.drawable.product_storychair3x), productimage);
        }



        return view;
    }



    public CardView getCardView() {
        return cardView;
    }

    public void passargs(List<RetroPhoto> lp) {
        this.lp= lp;
    }
}
