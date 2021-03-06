package com.example.asm.Adapter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;

import com.example.asm.Database.Database;
import com.example.asm.QuanLyCourse.activitySinhVien;
import com.example.asm.R;
import com.example.asm.model.Lop;
import com.example.asm.model.SinhVien;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class AdapterSV extends BaseAdapter {
    Context context;
    List<SinhVien>list;
    List<Lop> listLop;
    List<String> nameClass = new ArrayList<>();
    String maLop="";
    Database db;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public AdapterSV(Context context, List<SinhVien> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder{
        TextView txtStt,txtTen,txtNgay;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view==null){
            holder = new ViewHolder();
            db = new Database(context);
            view = LayoutInflater.from(context).inflate(R.layout.item_ds_sinhvien,viewGroup,false);
            holder.txtStt =(TextView)view.findViewById(R.id.txt_stt2);
            holder.txtTen =(TextView)view.findViewById(R.id.txt_tenSV);
            holder.txtNgay =(TextView)view.findViewById(R.id.txt_ngaySinh);
            view.setTag(holder);
        }else {
            holder=(ViewHolder)view.getTag();
        }
        holder.txtStt.setText(String.valueOf(i+1));
        holder.txtTen.setText(list.get(i).getTen());
        holder.txtNgay.setText(sdf.format(list.get(i).getNgay()));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog =new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_update);

                Button save =(Button)dialog.findViewById(R.id.btn_update);
                final EditText edtT1 =(EditText)dialog.findViewById(R.id.edt_update1);
                final EditText edtT2 =(EditText)dialog.findViewById(R.id.edt_update2);
                final EditText edtT3 =(EditText)dialog.findViewById(R.id.edt_update3);
                final ImageView imgLich =(ImageView)dialog.findViewById(R.id.img_update);
                @SuppressLint("WrongViewCast") final AppCompatSpinner spinner =(AppCompatSpinner)dialog.findViewById(R.id.spinner_update);

                edtT1.setEnabled(false);
                edtT3.setEnabled(false);

                SinhVien sv = list.get(i);
                // TRUYỀN DỮ LIỆU VÀO SPINNER
                db = new Database(context);
                nameClass = db.getAllMaLop();
                nameClass.add(0,"Mã lớp: "+sv.getMaLop());
                ArrayAdapter arrayAdapter = new ArrayAdapter(context,R.layout.support_simple_spinner_dropdown_item,nameClass);
                spinner.setAdapter(arrayAdapter);

                // LẤY MÃ LỚP TỪ SPINNER
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        maLop =(nameClass.get(spinner.getSelectedItemPosition())).substring(8);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

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
                        DatePickerDialog date =new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                Calendar cal =new GregorianCalendar(i,i1,i2);
                                edtT3.setText(sdf.format(cal.getTime()));
                            }
                        },year,month,day);
                        date.show();
                    }
                });
                edtT1.setText(sv.getMaSV());
                edtT2.setText(sv.getTen());
                edtT3.setText(sdf.format(sv.getNgay()));
                
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            SinhVien sv = list.get(i);
                            sv.setMaSV(edtT1.getText().toString());
                            sv.setMaLop(maLop);
                            sv.setTen(edtT2.getText().toString());
                            sv.setNgay(sdf.parse(edtT3.getText().toString()));
                            db = new Database(context);
                            long capnhap = db.updateSV(sv);
                            if (capnhap!=0){
                                Toast.makeText(context,"Update thành công",Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                notifyDataSetChanged();
                            }else {
                                Toast.makeText(context,"Update không thành công",Toast.LENGTH_SHORT).show();
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
                dialog.show();
            }
        });
        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
