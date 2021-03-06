package com.example.asm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView imgMaps,imgCourse,imgNews,imgSocial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("ASSIGNMENT");
        setContentView(R.layout.activity_main);
        imgCourse = findViewById(R.id.img_course);
        imgMaps = findViewById(R.id.img_maps);
        imgNews = findViewById(R.id.img_news);
        imgSocial = findViewById(R.id.img_social);

        imgCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Course.class);
                startActivity(intent);
            }
        });

        imgMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Maps.class);
                startActivity(intent);
            }
        });

        imgNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,News.class);
                startActivity(intent);
            }
        });
        imgSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Social.class);
                startActivity(intent);
            }
        });
    }
}