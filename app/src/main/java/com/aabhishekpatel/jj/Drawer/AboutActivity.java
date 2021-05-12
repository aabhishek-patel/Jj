package com.aabhishekpatel.jj.Drawer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.sax.Element;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.aabhishekpatel.jj.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class AboutActivity extends AppCompatActivity {

    TextView version, advertise, gmail_text, link_text, fb_text, twitter_text, youtube_text, playstore_text, instagram_text, whatsapp_text, github_text;
    ImageView gmail_image, link_image, fb_image, twitter_image, youtube_image, playstore_image, instagram_image, whatsapp_image, github_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        version = findViewById(R.id.version);
        advertise = findViewById(R.id.advertise);
        gmail_image = findViewById(R.id.gmail_image);
        gmail_text = findViewById(R.id.gmail_text);
        link_image = findViewById(R.id.link_image);
        link_text = findViewById(R.id.link_text);
        fb_image = findViewById(R.id.fb_image);
        fb_text = findViewById(R.id.fb_text);
        twitter_image = findViewById(R.id.twitter_image);
        twitter_text = findViewById(R.id.twitter_text);
        youtube_image = findViewById(R.id.youtube_image);
        youtube_text = findViewById(R.id.youtube_text);
        playstore_image = findViewById(R.id.store_image);
        playstore_text = findViewById(R.id.store_text);
        instagram_image = findViewById(R.id.instagram_image);
        instagram_text = findViewById(R.id.instagram_text);
        whatsapp_image = findViewById(R.id.whatsapp_image);
        whatsapp_text = findViewById(R.id.whatsapp_text);
        github_image = findViewById(R.id.github_image);
        github_text = findViewById(R.id.github_text);


        version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        advertise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ContactActivity.class);
                startActivity(intent);
                finish();
            }
        });

        gmail_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://mail.google.com/mail/u/1/#inbox?compose=new";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        gmail_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://mail.google.com/mail/u/1/#inbox?compose=new";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        link_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "kXSJVhgSJSWPGnnmSmbSB?";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        link_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "kXSJVhgSJSWPGnnmSmbSB?";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        fb_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.facebook.com/jagahzameen/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        fb_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.facebook.com/jagahzameen/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        twitter_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://twitter.com/_aabhishekpatel";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        twitter_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://twitter.com/_aabhishekpatel";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        youtube_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://youtube.com/channel/UCUzgkq9x8VPRubmcE1RjW8Q";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        youtube_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://youtube.com/channel/UCUzgkq9x8VPRubmcE1RjW8Q";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        playstore_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://play.google.com/store/apps/details?id=" + getPackageCodePath();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        playstore_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://play.google.com/store/apps/details?id=" + getPackageCodePath();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        instagram_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://instagram.com/jagahjameen?igshid=18ierbiw9ycun";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        instagram_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://instagram.com/jagahjameen?igshid=1sro4j46fmn9a";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        whatsapp_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://wa.me/message/TLF2T47TUSF7D1";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        whatsapp_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://wa.me/message/TLF2T47TUSF7D1";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        github_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://github.com/aabhishek-patel";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        github_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://github.com/aabhishek-patel";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

}
