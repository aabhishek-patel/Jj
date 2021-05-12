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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

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

public class LandAdapter extends RecyclerView.Adapter<LandAdapter.ImageViewHolder> {

    public Context mcontext;
    public List<LandModel> landModels;

    private static final int REQUEST_CALL =1;

    private FirebaseUser firebaseUser;

    public LandAdapter(Context mcontext, List<LandModel> landModels) {
        this.mcontext = mcontext;
        this.landModels = landModels;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.landproduct, viewGroup, false);
        return new LandAdapter.ImageViewHolder(view);
    }

    /*------------------------------- ImageViewHolder -------------------------------*/
    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int i) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final LandModel landModel = landModels.get(i);

        holder.land_name.setText(landModels.get(i).getLandname());
        holder.land_rate.setText(landModels.get(i).getLandprice());
        final List<SlideModel> adsimages = new ArrayList<>();
        adsimages.add(new SlideModel(landModels.get(i).getLandimage1(),
                ScaleTypes.FIT));
        adsimages.add(new SlideModel(landModels.get(i).getLandimage2(),
                ScaleTypes.FIT));
        adsimages.add(new SlideModel(landModels.get(i).getLandimage3(),
                ScaleTypes.FIT));
        adsimages.add(new SlideModel(landModels.get(i).getLandimage4(),
                ScaleTypes.FIT));
        holder.land_image.setImageList(adsimages, ScaleTypes.FIT);

        holder.land_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Booking feature is coming soon...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
*/
                String call =  landModel.getLandphone().toString();
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

        holder.land_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = landModel.getLandname().toString().toUpperCase()
                        +" \n\n " + landModel.getLandprice().toString()
                        //+"\n " + landModel.getLandid()
                        +"\n\n " + "https://play.google.com/store/apps/details?id=" + mcontext.getPackageCodePath();
                String shareSub = "Try Now J&J";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                mcontext.startActivity(Intent.createChooser(sharingIntent, "Share By Using"));

            }
        });

    }

    @Override
    public int getItemCount() {
        return landModels.size();
    }



    /*------------------------------- Hooks -------------------------------*/
    public class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageSlider land_image;
        TextView land_name;
        TextView land_rate;
        Button land_call;
        Button land_share;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            land_image = (ImageSlider) itemView.findViewById(R.id.land_image);
            land_name = (TextView) itemView.findViewById(R.id.land_name);
            land_rate = (TextView) itemView.findViewById(R.id.land_rate);
            land_call = (Button) itemView.findViewById(R.id.land_call);
            land_share = (Button) itemView.findViewById(R.id.land_share);
        }
    }
}
