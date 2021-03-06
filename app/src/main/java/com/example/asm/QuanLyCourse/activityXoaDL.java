package com.example.asm.QuanLyCourse;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.asm.Adapter.PagerAdapter;
import com.example.asm.Database.Database;
import com.example.asm.Fragment.FragmentLop;
import com.example.asm.Fragment.FragmentSV;
import com.example.asm.R;
import com.google.android.material.tabs.TabLayout;

public class activityXoaDL extends AppCompatActivity {
    ViewPager pager;
    Database db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("XÓA DỮ LIỆU");
        setContentView(R.layout.xoa_du_lieu);

        pager = findViewById(R.id.pager1);
        addFragToViewPager(pager);
        TabLayout tab =(TabLayout)this.findViewById(R.id.tab1);
        tab.setupWithViewPager(pager);
        db =new Database(activityXoaDL.this);
    }

    private void addFragToViewPager(ViewPager viewPager){
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addTab(new FragmentLop(),"LỚP");
        adapter.addTab(new FragmentSV(),"SINH VIÊN");
        viewPager.setAdapter(adapter);
    }
}
