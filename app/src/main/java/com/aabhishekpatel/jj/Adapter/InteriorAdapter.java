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

import com.aabhishekpatel.jj.Model.InteriorModel;
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

public class InteriorAdapter extends RecyclerView.Adapter<InteriorAdapter.ImageViewHolder>{

    public Context mcontext;
    public List<InteriorModel> interiorModels;

    private FirebaseUser firebaseUser;

    public InteriorAdapter(Context mcontext , List<InteriorModel> interiorModels){
        this.mcontext = mcontext;
        this.interiorModels = interiorModels;


    }

    @NonNull
    @Override
    public InteriorAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.interiorproduct,viewGroup, false);
        return new InteriorAdapter.ImageViewHolder(view);
    }

    /*------------------------------- ImageViewHolder -------------------------------*/
    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder imageViewHolder, int i) {
        
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final InteriorModel interiorModel = interiorModels.get(i);

        imageViewHolder.interior_name.setText(interiorModels.get(i).getInteriorname());
        imageViewHolder.interior_rate.setText(interiorModels.get(i).getInteriorprice());
        final List<SlideModel> adsimages = new ArrayList<>();
        adsimages.add(new SlideModel(interiorModels.get(i).getInteriorimage1(),
                ScaleTypes.FIT));
        adsimages.add(new SlideModel(interiorModels.get(i).getInteriorimage2(),
                ScaleTypes.FIT));
        adsimages.add(new SlideModel(interiorModels.get(i).getInteriorimage3(),
                ScaleTypes.FIT));
        adsimages.add(new SlideModel(interiorModels.get(i).getInteriorimage4(),
                ScaleTypes.FIT));
        imageViewHolder.interior_image.setImageList(adsimages, ScaleTypes.FIT);



        imageViewHolder.interior_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Booking feature is coming soon...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
*/
                String call =  interiorModel.getInteriorphone().toString();
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

        imageViewHolder.interior_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = interiorModel.getInteriorname().toString().toUpperCase()
                        +"\n\n" + interiorModel.getInteriorprice().toString()
                        //+"\n" + interiorModel.getInteriorid()
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
        return interiorModels.size();
    }

    /*------------------------------- Hooks -------------------------------*/
    public class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageSlider interior_image;
        TextView interior_name;
        TextView interior_rate;
        Button interior_call;
        Button interior_share;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            interior_image = (ImageSlider) itemView.findViewById(R.id.interior_image);
            interior_name = (TextView) itemView.findViewById(R.id.interior_name);
            interior_rate = (TextView) itemView.findViewById(R.id.interior_rate);
            interior_call = (Button) itemView.findViewById(R.id.interior_call);
            interior_share = (Button) itemView.findViewById(R.id.interior_share);



        }
    }
}

