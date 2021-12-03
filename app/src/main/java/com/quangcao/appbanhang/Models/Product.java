package com.quangcao.appbanhang.Models;

public class Product {
    private String maSP;
    private String tenSP;
    private String donGia;
    private String soLuong;
    private String moTa;

    public Product() {
    }

    public Product(String maSP, String tenSP, String donGia, String soLuong, String moTa) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.moTa = moTa;
    }

    public Product(String tenSP, String donGia, String soLuong, String moTa) {
        this.tenSP = tenSP;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.moTa = moTa;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getDonGia() {
        return donGia;
    }

    public void setDonGia(String donGia) {
        this.donGia = donGia;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
