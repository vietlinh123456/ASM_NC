package com.example.asm.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.asm.model.Lop;
import com.example.asm.model.SinhVien;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    // LỚP
    public static final String TABLE_NAME_LOP="LOP";
    public static final String COLUMN_MALOP="maLop";
    public static final String COLUMN_TENLOP="tenLop";
    // SINH VIÊN
    public static final String TABLE_NAME_SV="SINHVIEN";
    public static final String COLUMN_MASV="maSV";
    public static final String COLUMN_MALOP_SV="maLop";
    public static final String COLUMN_TENSV="tenSV";
    public static final String COLUMN_NGAY="ngaySinh";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SQLiteDatabase sqLiteDatabase;

    public Database(Context context) {
        super(context, "QUANLY.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // BẢNG LỚP
        String SQL_LOP="CREATE TABLE "+
                TABLE_NAME_LOP+" ("+
                COLUMN_MALOP+" NVARCHAR PRIMARY KEY, "+
                COLUMN_TENLOP+" NVARCHAR );";
        // BẢNG SINH VIÊN
        String SQL_SV ="CREATE TABLE "+
                TABLE_NAME_SV+" ("+
                COLUMN_MASV+" NVARCHAR PRIMARY KEY, "+
                COLUMN_MALOP_SV+" NVARCHAR, "+
                COLUMN_TENSV+" NVARCHAR, "+
                COLUMN_NGAY+" DATE );";

        Log.e("SQL",SQL_LOP);
        Log.e("SQL",SQL_SV);
        db.execSQL(SQL_LOP);
        db.execSQL(SQL_SV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_LOP);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_SV);
    }

    // THÊM LỚP
    public long insertLop(Lop lop){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MALOP,lop.getMaLop());
        values.put(COLUMN_TENLOP,lop.getTenLop());
        long them =sqLiteDatabase.insert(TABLE_NAME_LOP,null,values);
        return them;
    }

    // THÊM SINH VIÊN
    public long insertSV(SinhVien sv){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MASV,sv.getMaSV());
        values.put(COLUMN_MALOP_SV,sv.getMaLop());
        values.put(COLUMN_TENSV,sv.getTen());
        values.put(COLUMN_NGAY,sdf.format(sv.getNgay()));
        long them = sqLiteDatabase.insert(TABLE_NAME_SV,null,values);
        return them;
    }

    // LẤY DANH SÁCH LỚP
    public List<Lop> getAllLop(){
        List<Lop>list = new ArrayList<>();
        SQLiteDatabase db =getWritableDatabase();
        String sql = "SELECT * FROM LOP";
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String ma = cursor.getString(0);
                String ten = cursor.getString(1);
                Lop lop = new Lop(ma,ten);
                list.add(lop);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    // LẤY DANH SÁCH MÃ LỚP
    public List<String> getAllMaLop(){
        List<String> list =new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String sql ="SELECT maLop, tenLop FROM LOP";
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String ma = cursor.getString(0);
                String ten = cursor.getString(1);
                String them="Mã lớp: "+ma;
                list.add(them);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    // LẤY DANH SÁCH SINH VIÊN
    public List<SinhVien> getAllSV() throws ParseException {
        List<SinhVien> list = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM SINHVIEN";
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String maSV = cursor.getString(0);
                String maLop = cursor.getString(1);
                String ten = cursor.getString(2);
                Date ngay =sdf.parse(cursor.getString(3));
                SinhVien sv = new SinhVien(maSV,maLop,ten,ngay);
                list.add(sv);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    // UPDATE LỚP
    public long updateLop(Lop lop){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MALOP,lop.getMaLop());
        values.put(COLUMN_TENLOP,lop.getTenLop());
        long update =sqLiteDatabase.update(TABLE_NAME_LOP,values,COLUMN_MALOP+"=?",new String[]{lop.getMaLop()});
        return update;
    }

    // UPDATE SINH VIÊN
    public long updateSV(SinhVien sv){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MASV,sv.getMaSV());
        values.put(COLUMN_MALOP_SV,sv.getMaLop());
        values.put(COLUMN_TENSV,sv.getTen());
        values.put(COLUMN_NGAY,sdf.format(sv.getNgay()));
        long update = sqLiteDatabase.update(TABLE_NAME_SV,values,COLUMN_MASV+"=?",new String[]{sv.getMaSV()});
        return update;
    }

    // DELETE LỚP
    public long deleteLop(String lop){
        sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME_LOP,COLUMN_MALOP+"=?",new String[]{lop});
    }

    // DELETE SINH VIÊN
    public long deleteSV(String sv){
        sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME_SV,COLUMN_MASV+"=?",new String[]{sv});
    }
}
