package com.example.asm.QuanLyCourse;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.example.asm.Adapter.AdapterSV;
import com.example.asm.Database.Database;
import com.example.asm.R;
import com.example.asm.model.Lop;
import com.example.asm.model.SinhVien;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class activitySinhVien extends AppCompatActivity {
    AppCompatSpinner spinner;
    Database db;
    List<String> nameClass =new ArrayList<>();
    List<Lop> litLop;
    String maLop="";
    EditText edtMa,edtTen,edtNgay;
    Button btnBack,btnThem;
    ImageView img,imgLich;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    List<SinhVien>listSV;
    AdapterSV adapterSV;
    ListView lv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("SINH VIÊN");
        setContentView(R.layout.them_sinhvien);
        lv = findViewById(R.id.list_sinhVien);
        spinner = findViewById(R.id.spinner);
        img = findViewById(R.id.img_them);
        imgLich = findViewById(R.id.img_lich);
        btnBack = findViewById(R.id.btn_back2);
        btnThem = findViewById(R.id.btn_them2);
        edtMa = findViewById(R.id.edt_MaSV);
        edtTen = findViewById(R.id.edt_tenSV);
        edtNgay = findViewById(R.id.edt_ngaySinh);
        edtNgay.setEnabled(false);
        Snackbar.make(findViewById(R.id.activity_SinhVien),"Lưu ý: Trong danh sách sinh viên, click vào sinh viên muốn sửa để sửa lại thông tin",6000).show();

        // THÊM DỮ LIỆU VÀO SPINNER
        db = new Database(activitySinhVien.this);
        litLop = new ArrayList<>();
        litLop = db.getAllLop();
        nameClass.clear();
        for (int i=0;i<litLop.size();i++){
            nameClass.add("Tên lớp: "+litLop.get(i).getTenLop()+" - "+"Mã lớp: "+litLop.get(i).getMaLop());
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(activitySinhVien.this,R.layout.support_simple_spinner_dropdown_item,nameClass);
        spinner.setAdapter(arrayAdapter);

        // LẤY MÃ LỚP TỪ SPINNER
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maLop =litLop.get(spinner.getSelectedItemPosition()).getMaLop();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // QUAY LẠI ACTIVITY THÊM LỚP
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activitySinhVien.this,com.example.asm.QuanLyCourse.activityLop.class);
                startActivity(intent);
            }
        });

        // DÙNG DATE-PICKER-DIALOG
        final Calendar c = Calendar.getInstance();
        final int year =c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        imgLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog date =new DatePickerDialog(activitySinhVien.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Calendar cal =new GregorianCalendar(i,i1,i2);
                        setDate(cal);
                    }
                },year,month,day);
                date.show();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maSV = edtMa.getText().toString();
                String tenSV = edtTen.getText().toString();
                String ngay = edtNgay.getText().toString();
                if (maSV.equalsIgnoreCase("")||tenSV.equalsIgnoreCase("")||ngay.equalsIgnoreCase("")){
                    Toast.makeText(activitySinhVien.this,"Thông tin không được để trống",Toast.LENGTH_SHORT).show();
                }
                try {
                    SinhVien sv = new SinhVien(maSV,maLop,tenSV,sdf.parse(ngay));
                    db = new Database(activitySinhVien.this);
                    long them = db.insertSV(sv);

                    listSV = new ArrayList<>();
                    try {
                        listSV = db.getAllSV();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    adapterSV = new AdapterSV(activitySinhVien.this,listSV);
                    adapterSV.notifyDataSetChanged();
                    lv.setAdapter(adapterSV);

                    if (them>0){
                        Toast.makeText(activitySinhVien.this,"thêm thành công",Toast.LENGTH_SHORT).show();
                        edtMa.setText("");
                        edtTen.setText("");
                        edtNgay.setText("");
                    }else {
                        Toast.makeText(activitySinhVien.this,"thêm không thành công",Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        listSV = new ArrayList<>();
        try {
            listSV = db.getAllSV();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        adapterSV = new AdapterSV(activitySinhVien.this,listSV);
        adapterSV.notifyDataSetChanged();
        lv.setAdapter(adapterSV);
    }
    public void setDate(final Calendar calendar){
        edtNgay.setText(sdf.format(calendar.getTime()));
    }
}
