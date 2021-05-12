package com.aabhishekpatel.jj.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aabhishekpatel.jj.Model.HousesModel;
import com.aabhishekpatel.jj.Model.LandModel;
import com.aabhishekpatel.jj.R;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HousesAdapter extends RecyclerView.Adapter<HousesAdapter.ImageViewHolder> {

    public Context mcontext;
    public List<HousesModel> housesModels;

    private FirebaseUser firebaseUser;

    public HousesAdapter(Context mcontext , List<HousesModel> housesModels){
        this.mcontext = mcontext;
        this.housesModels = housesModels;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.housesproduct,viewGroup, false);
        return new HousesAdapter.ImageViewHolder(view);
    }

    /*------------------------------- ImageViewHolder -------------------------------*/
    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int i) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final HousesModel housesModel = housesModels.get(i);

        imageViewHolder.house_name.setText(housesModels.get(i).getHousesname());
        imageViewHolder.house_rate.setText(housesModels.get(i).getHousesprice());
        final List<SlideModel> adsimages = new ArrayList<>();
        adsimages.add(new SlideModel(housesModels.get(i).getHousesimage1(),
                ScaleTypes.FIT));
        adsimages.add(new SlideModel(housesModels.get(i).getHousesimage2(),
                ScaleTypes.FIT));
        adsimages.add(new SlideModel(housesModels.get(i).getHousesimage3(),
                ScaleTypes.FIT));
        adsimages.add(new SlideModel(housesModels.get(i).getHousesimage4(),
                ScaleTypes.FIT));
        imageViewHolder.house_image.setImageList(adsimages, ScaleTypes.FIT);


        imageViewHolder.house_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String call =  housesModel.getHousesphone().toString();
                if (call.isEmpty()){
                    Toast.makeText(mcontext, "Due to some network issue please try again...", Toast.LENGTH_SHORT).show();
                } else {
                    String s = "tel:" + call;
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(s));
                    mcontext.startActivity(intent);
                }
            }
        });

        imageViewHolder.house_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String sharebody = housesModel.getHousesname().toString().toUpperCase()
                        +"\n\n" + housesModel.getHousesprice().toString()
                        //+"\n" + housesModel.getHousesid()
                        +"\n " + "https://play.google.com/store/apps/details?id=" + mcontext.getPackageCodePath();
                String shareSub = "Try Now J&J";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, sharebody);
                mcontext.startActivity(Intent.createChooser(sharingIntent, "Share By Using"));

            }
        });
    }

    @Override
    public int getItemCount() {
        return housesModels.size();
    }

    /*------------------------------- Hooks -------------------------------*/
    public class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageSlider house_image;
        TextView house_name;
        TextView house_rate;
        Button house_call;
        Button house_share;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            house_image = (ImageSlider) itemView.findViewById(R.id.house_image);
            house_name = (TextView) itemView.findViewById(R.id.house_name);
            house_rate = (TextView) itemView.findViewById(R.id.house_rate);
            house_call = (Button) itemView.findViewById(R.id.house_call);
            house_share = (Button) itemView.findViewById(R.id.house_share);
        }
    }
}

