package com.quangcao.appbanhang.Models;

public class SanPham {
    private String tenSP;
    private String gia;

    public SanPham() {
    }

    public SanPham(String tenSP, String gia) {
        this.tenSP = tenSP;
        this.gia = gia;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }
}
