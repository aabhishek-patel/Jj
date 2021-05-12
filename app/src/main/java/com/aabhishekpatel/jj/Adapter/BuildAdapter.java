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

import com.aabhishekpatel.jj.Model.BuildModel;
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

public class BuildAdapter extends RecyclerView.Adapter<BuildAdapter.ImageViewHolder>{
    public Context mcontext;
    public List<BuildModel> buildModels;

    private FirebaseUser firebaseUser;

    public BuildAdapter(Context mcontext , List<BuildModel> buildModels){
        this.mcontext = mcontext;
        this.buildModels = buildModels;
    }

    @NonNull
    @Override
    public BuildAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.buildproduct,viewGroup, false);
        return new BuildAdapter.ImageViewHolder(view);
    }

    /*------------------------------- ImageViewHolder -------------------------------*/
    @Override
    public void onBindViewHolder(@NonNull BuildAdapter.ImageViewHolder imageViewHolder, int i) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final BuildModel buildModel = buildModels.get(i);

        imageViewHolder.build_name.setText(buildModels.get(i).getBuildname());
        imageViewHolder.build_rate.setText(buildModels.get(i).getBuildprice());
        final List<SlideModel> adsimages = new ArrayList<>();
        adsimages.add(new SlideModel(buildModels.get(i).getBuildimage1(),
                ScaleTypes.FIT));
        adsimages.add(new SlideModel(buildModels.get(i).getBuildimage2(),
                ScaleTypes.FIT));
        adsimages.add(new SlideModel(buildModels.get(i).getBuildimage3(),
                ScaleTypes.FIT));
        adsimages.add(new SlideModel(buildModels.get(i).getBuildimage4(),
                ScaleTypes.FIT));
        imageViewHolder.build_image.setImageList(adsimages, ScaleTypes.FIT);

        imageViewHolder.build_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String call =  buildModel.getBuildphone().toString();
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

        imageViewHolder.build_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = buildModel.getBuildname().toString().toUpperCase()
                        +"\n\n" + buildModel.getBuildprice().toString()
                        //+"\n" + buildModel.getBuildid()
                        +"\n\n " + "https://play.google.com/store/apps/details?id=" + mcontext.getPackageCodePath();
                String shareSub = "Try Now JJ";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                mcontext.startActivity(Intent.createChooser(sharingIntent, "Share By Using"));
            }
        });
    }

    /*------------------------------- buildModels.size -------------------------------*/
    @Override
    public int getItemCount() {
        return buildModels.size();
    }

    /*------------------------------- Hooks -------------------------------*/
    public class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageSlider build_image;
        TextView build_name;
        TextView build_rate;
        Button build_call;
        Button build_share;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            build_image = (ImageSlider) itemView.findViewById(R.id.build_image);
            build_name = (TextView) itemView.findViewById(R.id.build_name);
            build_rate = (TextView) itemView.findViewById(R.id.build_rate);
            build_call = (Button) itemView.findViewById(R.id.build_call);
            build_share = (Button) itemView.findViewById(R.id.build_share);
        }
    }
}

