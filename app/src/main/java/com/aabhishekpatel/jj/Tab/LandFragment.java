package com.aabhishekpatel.jj.Tab;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aabhishekpatel.jj.Adapter.LandAdapter;
import com.aabhishekpatel.jj.Model.LandModel;
import com.aabhishekpatel.jj.R;
import com.denzcoskun.imageslider.ImageSlider;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LandFragment extends Fragment {

    ImageSlider adsSlider;

    RecyclerView recyclerView;
    LandAdapter landAdapter;
    List<LandModel> landModelList;

    DatabaseReference reference;

    private ProgressBar progress_bar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_land, container, false);

        /*------------------------------- Hooks -------------------------------*/
        progress_bar = view.findViewById(R.id.progress_bar);
        Sprite Wave = new Wave();
        progress_bar.setIndeterminateDrawable(Wave);


        /*------------------------------- Recycleview -------------------------------*/
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        landModelList = new ArrayList<LandModel>();

        /*------------------------------- ADS Slider -------------------------------*/
        /*adsSlider = (ImageSlider) view.findViewById(R.id.ads_image_slider);
        final List<SlideModel> adsimages = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("Slider")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren())
                            adsimages.add(new SlideModel(data.child("url").getValue().toString(),
                                    data.child("title").getValue().toString(),
                                    ScaleTypes.FIT));

                        adsSlider.setImageList(adsimages,ScaleTypes.FIT);

                        adsSlider.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onItemSelected(int i) {
                                Toast.makeText(getContext(),adsimages.get(i).getTitle().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        Toast.makeText(getContext(), "Opsss... Somthing is wrong.", Toast.LENGTH_SHORT).show();

                    }
                });*/


        /*------------------------------- Load Land data to recycleview -------------------------------*/
        reference = FirebaseDatabase.getInstance().getReference().child("Land");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    LandModel landModel = dataSnapshot1.getValue(LandModel.class);
                    landModelList.add(landModel);
                }
                landAdapter = new LandAdapter(getContext(),landModelList);
                recyclerView.setAdapter(landAdapter);
                progress_bar.setVisibility(View.INVISIBLE);
                landAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getContext(),"Opsss... Somthing is wrong...",Toast.LENGTH_SHORT).show();
                progress_bar.setVisibility(View.INVISIBLE);
            }
        });

        return  view;
    }
}
