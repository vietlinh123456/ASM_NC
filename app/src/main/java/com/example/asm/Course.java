package com.example.asm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Course extends AppCompatActivity {
    ImageView imgLop,imgStudent,imgXoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("KHÓA HỌC");
        setContentView(R.layout.activity_course);
        imgLop = findViewById(R.id.img2);
        imgStudent = findViewById(R.id.img1);
        imgXoa = findViewById(R.id.img3);
        imgLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Course.this,com.example.asm.QuanLyCourse.activityLop.class);
                startActivity(intent);
            }
        });
        imgStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Course.this,com.example.asm.QuanLyCourse.activitySinhVien.class);
                startActivity(intent);
            }
        });
        imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Course.this,com.example.asm.QuanLyCourse.activityXoaDL.class);
                startActivity(intent);
            }
        });
    }
}