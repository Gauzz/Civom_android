package com.lateralx.civom.Adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lateralx.civom.Model.RetroPhoto;
import com.lateralx.civom.ProductActivity;
import com.lateralx.civom.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<RetroPhoto> dataList;
    private Context context;
    TextView txtTitle;
    private ImageView coverImage;
    TextView txtDescription;


    public CustomAdapter(Context context,List<RetroPhoto> dataList){
        this.context = context;
        this.dataList = dataList;
    }



    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView txtTitle;
        private ImageView coverImage;
        TextView txtDescription;
        TextView txtThumb;
        TextView txtpar;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtTitle = mView.findViewById(R.id.title0);
            coverImage = mView.findViewById(R.id.coverImage);
            txtDescription=mView.findViewById(R.id.textdescription);
            txtThumb= mView.findViewById(R.id.textthumb);
            txtpar = mView.findViewById(R.id.textpar);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_row, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.txtTitle.setText(dataList.get(position).getName());
      //  holder.txtDescription.setText(dataList.get(position).getDescription());
        ImageLoader imageLoader = ImageLoader.getInstance();

        if(dataList.get(position).getThumbnail_compressed()!= null && dataList.get(position).getThumbnail_compressed()!= ""&& !dataList.get(position).getThumbnail_compressed().isEmpty())
        imageLoader.displayImage("http://35.154.220.170/"+dataList.get(position).getThumbnail_compressed(), holder.coverImage);
        else
            imageLoader.displayImage(String.valueOf(R.drawable.civom_logo_lowres), holder.coverImage);

        holder.txtDescription.setText(dataList.get(position).getDescription());
        holder.txtThumb.setText(dataList.get(position).getThumbnail_original());
        holder.txtpar.setText(dataList.get(position).getFbx());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }



}
