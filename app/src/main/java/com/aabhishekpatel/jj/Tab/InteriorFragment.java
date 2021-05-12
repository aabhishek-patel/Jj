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

import com.aabhishekpatel.jj.Adapter.HousesAdapter;
import com.aabhishekpatel.jj.Adapter.InteriorAdapter;
import com.aabhishekpatel.jj.Model.HousesModel;
import com.aabhishekpatel.jj.Model.InteriorModel;
import com.aabhishekpatel.jj.R;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.Pulse;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class InteriorFragment extends Fragment {

    ImageSlider adsSlider;

    RecyclerView recyclerView;
    InteriorAdapter interiorAdapter;
    List<InteriorModel> interiorModelList;

    DatabaseReference reference;

    private ProgressBar progress_bar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_interior, container, false);

        /*------------------------------- Hooks -------------------------------*/
        progress_bar = view.findViewById(R.id.progress_bar);
        Sprite ThreeBounce = new ThreeBounce();
        progress_bar.setIndeterminateDrawable(ThreeBounce);


        /*------------------------------- Recycleview -------------------------------*/
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        /*------------------------------- Interior_model -------------------------------*/
        interiorModelList = new ArrayList<InteriorModel>();


        /*------------------------------- ADS Slider -------------------------------*/
        /*adsSlider = (ImageSlider) view.findViewById(R.id.ads_image_slider);
        final List<SlideModel> adsimages = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("Interior")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren())
                            adsimages.add(new SlideModel(data.child("interiorimage").getValue().toString(),
                                    data.child("interiorname").getValue().toString(),
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


        /*------------------------------- Load Interior data to recycleview -------------------------------*/
        reference = FirebaseDatabase.getInstance().getReference().child("Interior");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    InteriorModel interiorModel = dataSnapshot1.getValue(InteriorModel.class);
                    interiorModelList.add(interiorModel);
                }
                interiorAdapter = new InteriorAdapter(getContext(),interiorModelList);
                recyclerView.setAdapter(interiorAdapter);
                progress_bar.setVisibility(View.INVISIBLE);
                interiorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getContext(),"Opsss... Somthing is wrong...",Toast.LENGTH_SHORT).show();
                progress_bar.setVisibility(View.INVISIBLE);
            }
        });

        return view;

    }

}
