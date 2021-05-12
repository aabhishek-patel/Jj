package com.aabhishekpatel.jj.Tab;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;

import com.aabhishekpatel.jj.Drawer.AboutActivity;
import com.aabhishekpatel.jj.Drawer.ContactActivity;
import com.aabhishekpatel.jj.Drawer.WhyActivity;
import com.aabhishekpatel.jj.Login.EditPofileActivity;
import com.aabhishekpatel.jj.Login.LoginActivity;
import com.aabhishekpatel.jj.Drawer.PrivacyActivity;
import com.aabhishekpatel.jj.Model.InteriorModel;
import com.aabhishekpatel.jj.Model.UserHelperClass;
import com.aabhishekpatel.jj.R;
import com.aabhishekpatel.jj.Utility.NetworkChangeListerner;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.SyncStateContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aabhishekpatel.jj.Tab.ui.main.SectionsPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView navigationView;
    TextView user_name, user_phone;
    CircleImageView user_profile_image;
    FirebaseUser currentUser;
    DatabaseReference databaseReference;
    FirebaseDatabase rootNode;
    View hview;
    private Button mLogoutBtn;
    private FirebaseAuth mAuth;
    String phoneNo;

    String username;

    RecyclerView mrecyclerView;

    LinearLayoutManager mlinearLayoutManager; //for sorting
    SharedPreferences msharedPreferences; //for saving sort settings

    NetworkChangeListerner networkChangeListerner = new NetworkChangeListerner();

    public List<UserHelperClass> userHelperClassesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_main);


        /*------------------------------- SharedPreferences -------------------------------*/
        msharedPreferences = getSharedPreferences("SortSettings", MODE_PRIVATE);
        String mSorting = msharedPreferences.getString("Sort", "Newest"); //where idf no setting is selected newest willbe default

        if (mSorting.equals("Newest")){
            mlinearLayoutManager = new LinearLayoutManager(this);
            //this will load the items from bottom means newest first
            mlinearLayoutManager.setReverseLayout(true);
            mlinearLayoutManager.setStackFromEnd(true);
        }
        else if (mSorting.equals("Oldest")){
            mlinearLayoutManager = new LinearLayoutManager(this);
            //this will load the items from bottom means oldest first
            mlinearLayoutManager.setReverseLayout(false);
            mlinearLayoutManager.setStackFromEnd(false);
        }

        /*------------------------------- Hooks -------------------------------*/

        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer);
        mLogoutBtn = findViewById(R.id.nav_logout);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        phoneNo = prefs.getString("phoneNo","none");
        username = prefs.getString("username","none");

        /*------------------------------- Toolbar -------------------------------*/
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*------------------------------- Navigation Drawer Menu -------------------------------*/
        navigationView.bringToFront();
        navigationView.setItemIconTintList(null);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setHomeButtonEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);

        navigationView = findViewById(R.id.nav_view);
        hview = navigationView.getHeaderView(0);

        //so you set here you want
        user_name = (TextView) hview.findViewById(R.id.user_name);
        user_phone = (TextView) hview.findViewById(R.id.user_phone);
        user_profile_image = (CircleImageView) hview.findViewById(R.id.user_profile_image);


        Glide.with(getApplicationContext()).load(currentUser.getPhotoUrl()).into(user_profile_image);
        user_name.setText(currentUser.getPhoneNumber());
        user_phone.setText(currentUser.getEmail());

        Picasso.get()
                .load(currentUser.getPhotoUrl())
                .placeholder(R.mipmap.ic_launcher)
                .resize(100, 100)
                .centerCrop()
                .into(user_profile_image);

        /*------------------------------- adapter --------------------------------*/
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        /*------------------------------- Floating Action Button -------------------------------*/
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "https://wa.me/message/TLF2T47TUSF7D1";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }



    /*------------------------------- Navigation Drawer Menu Clicked Activity-------------------------------*/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        switch (menuItem.getItemId()) {
            case R.id.nav_why_jj:
                Intent why_intent = new Intent(MainActivity.this, WhyActivity.class);
                startActivity(why_intent);
                break;
            case R.id.nav_privacy:
                Intent privacy_intent = new Intent(MainActivity.this, PrivacyActivity.class);
                startActivity(privacy_intent);
                break;
            case R.id.nav_contact_us:
                Intent contact_us_intent = new Intent(MainActivity.this, ContactActivity.class);
                startActivity(contact_us_intent);
                break;
            case R.id.nav_about_us:
                Intent about_intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(about_intent);
                break;

            case R.id.nav_short:
                showSortDailog();
                break;
            case R.id.nav_share: {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "https://play.google.com/store/apps/details?id=" + getPackageCodePath();
                String shareSub = "Try Now J&J";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share Using"));
            }
            break;
            case R.id.nav_change_language:
                showChangeLanguageDialog();
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginActivity);
                finish();
                break;

            case R.id.nav_covid:
                String url = "https://news.google.com/covid19/map?hl=en-IN&mid=%2Fm%2F01hpnh&gl=IN&ceid=IN%3Aen";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showSortDailog() {
        //option to dispaly in dailog
        String[] sortOptions = {"Newest", "Oldest"};
        //create alert dailog
        AlertDialog.Builder sortBuilder = new AlertDialog.Builder(MainActivity.this);
        sortBuilder.setTitle("Sort by")
                .setIcon(R.drawable.ic_sort)
                .setItems(sortOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //the "i" arguments contains the index postion of the selected items
                        // 0 means "Newest" and 1 means "Oldest"
                        if (i ==0){
                            //sort by newest
                            //Edit our shareprefrences
                            SharedPreferences.Editor editor = msharedPreferences.edit();
                            editor.putString("Sort", "Newest"); //wher 'Sort' is key & 'newest' isvalue
                            editor.apply(); //apply save the valuein our sharedpref
                            recreate();//restart activity to take affect
                        }
                        else if (i ==1){
                            //srt by oldest
                            //Edit our shareprefrences
                            SharedPreferences.Editor editor = msharedPreferences.edit();
                            editor.putString("Sort", "Oldest"); //wher 'Sort' is key & 'oldest' isvalue
                            editor.apply(); //apply save the valuein our sharedpref
                            recreate();//restart activity to take affect

                        }
                    }
                });
        sortBuilder.show();
    }

    /*------------------------------- showChangeLanguageDialog -------------------------------*/
    private void showChangeLanguageDialog() {
        final String[] listItems = {"English" , "हिन्दी"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle(R.string.change_language)
                .setIcon(R.drawable.ic_translate);
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               if (i == 0){
                   //English
                   setLocale("en");
                   recreate();
               }

                if (i == 1){
                    //hindi
                    setLocale("hi");
                    recreate();
                }

                //dismiss alert when Language selected
                dialogInterface.dismiss();
            }
        });
        AlertDialog mDialog = mBuilder.create();
        //show alert dialog
        mDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        //save data to shared preference
        SharedPreferences.Editor editor = getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();
    }

    //load lang saved in shatre4d prefrence
    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);
    }

    /*------------------------------- navBar profile detail -------------------------------*/
    /*public void updateNavHeader() {


    }*/

    /*------------------------------- sendToprofileActivity -------------------------------*/
    private void sendToprofileActivity() {
        Intent profileIntent = new Intent(MainActivity.this, EditPofileActivity.class);
        startActivity(profileIntent);
        finish();
    }

    /*------------------------------- OnStart -------------------------------*/

    @Override
    public void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListerner,filter);
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null){
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListerner);
        super.onStop();
    }

    /*------------------------------- Back pressed -------------------------------*/
    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {

        AlertDialog.Builder alertDailogBuilder = new AlertDialog.Builder(this);
        alertDailogBuilder.setTitle(R.string.leaving_soon);
        alertDailogBuilder.setIcon(R.drawable.ic_exit);
        alertDailogBuilder.setMessage(R.string.sure_exit);
        alertDailogBuilder.setCancelable(false);
        alertDailogBuilder.setPositiveButton(R.string.exit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alertDailogBuilder.setNegativeButton(R.string.stay, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, R.string.welcome, Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialog = alertDailogBuilder.create();
        alertDialog.show();
            //super.onBackPressed();
        }
    }
}