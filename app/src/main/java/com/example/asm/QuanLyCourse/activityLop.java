package com.example.asm.QuanLyCourse;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.asm.Adapter.AdapterLop;
import com.example.asm.Database.Database;
import com.example.asm.R;
import com.example.asm.model.Lop;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class activityLop extends AppCompatActivity {
    EditText edtMaLop,edtTenLop;
    Button btnBack, btnThem;
    ListView lv;
    AdapterLop adapterLop;
    List<Lop>list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("LỚP");
        setContentView(R.layout.them_lop);
        edtMaLop = findViewById(R.id.edt_maLop);
        edtTenLop = findViewById(R.id.edt_tenLop);
        btnThem = findViewById(R.id.btn_them);
        btnBack = findViewById(R.id.btn_back);
        lv = findViewById(R.id.list_Lop);
        Snackbar.make(findViewById(R.id.activity_lop),"Lưu ý: Trong danh sách lớp, click vào lớp muốn sửa để sửa lại thông tin",6000).show();
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ma = edtMaLop.getText().toString();
                String ten = edtTenLop.getText().toString();
                if (ma.equalsIgnoreCase("")||ten.equalsIgnoreCase("")){
                    Toast.makeText(activityLop.this,"Thông tin không được để trống",Toast.LENGTH_SHORT).show();
                    return;
                }
                Lop lop = new Lop(ma,ten);

                Database db = new Database(activityLop.this);
                long them = db.insertLop(lop);

                list=new ArrayList<>();
                list =db.getAllLop();
                adapterLop =new AdapterLop(activityLop.this,list);
                adapterLop.notifyDataSetChanged();
                lv.setAdapter(adapterLop);

                if (them>0){
                    Toast.makeText(activityLop.this,"thêm thành công",Toast.LENGTH_SHORT).show();
                    edtMaLop.setText("");
                    edtTenLop.setText("");
                }else {
                    Toast.makeText(activityLop.this,"thêm không thành công",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        list=new ArrayList<>();
        Database db = new Database(activityLop.this);
        list =db.getAllLop();
        adapterLop =new AdapterLop(activityLop.this,list);
        adapterLop.notifyDataSetChanged();
        lv.setAdapter(adapterLop);
    }

}
