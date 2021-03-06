package com.example.asm.model;

import java.util.Date;

public class SinhVien {
    public String maSV;
    public String maLop;
    public String ten;
    public Date ngay;

    public SinhVien() {
    }

    public SinhVien(String maSV, String maLop, String ten, Date ngay) {
        this.maSV = maSV;
        this.maLop = maLop;
        this.ten = ten;
        this.ngay = ngay;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }
}
