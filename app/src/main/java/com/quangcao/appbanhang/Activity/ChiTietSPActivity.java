package com.quangcao.appbanhang.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.quangcao.appbanhang.Models.SanPham;
import com.quangcao.appbanhang.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChiTietSPActivity extends AppCompatActivity {

    TextView tv_tenSP, tv_gia, tv_so_luong, tv_mo_ta;
    Context context = ChiTietSPActivity.this;
    String maSP = "";
    String tenSP = "";
    String donGia = "";
    String soLuong = "";
    String moTa = "";

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_spactivity);
        setTitle("Sản Phẩm");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tv_tenSP = findViewById(R.id.tv_tenSP_CT);
        tv_gia = findViewById(R.id.tv_giaSP);
        tv_so_luong = findViewById(R.id.tv_CT_so_luong);
        tv_mo_ta = findViewById(R.id.tv_CT_mo_ta);
        Intent intent = getIntent();

        maSP = intent.getStringExtra("maSP");
        tenSP = intent.getStringExtra("tenSP");
        donGia = intent.getStringExtra("donGia");
        soLuong = intent.getStringExtra("soLuong");
        moTa = intent.getStringExtra("moTa");

        tv_tenSP.setText(tenSP);
        tv_gia.setText(donGia + " VNĐ");
        tv_so_luong.setText("Số lượng : " + soLuong);
        tv_mo_ta.setText("Mô tả : " + moTa);

    }

    public void update_DATA(View view){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.add_sp , null);

        TextView tv_title = layout.findViewById(R.id.tv_tieu_de_dialog);
        Button btn_update = layout.findViewById(R.id.btn_add);
        tv_title.setText("Cập nhật sản phẩm");
        btn_update.setText("Cập Nhật");

        Button btn_update_huy = layout.findViewById(R.id.btn_add_huy);
        EditText edt_tenSP = layout.findViewById(R.id.edt_add_ten_SP);
        EditText edt_Don_Gia = layout.findViewById(R.id.edt_add_gia_SP);
        EditText edt_soLuong = layout.findViewById(R.id.edt_add_soLuong_SP);
        EditText edt_moTa = layout.findViewById(R.id.edt_add_moTa_SP);
        edt_tenSP.setText( tenSP );
        edt_Don_Gia.setText( donGia );
        edt_soLuong.setText( soLuong );
        edt_moTa.setText( moTa );

        AlertDialog.Builder builder = new AlertDialog.Builder(ChiTietSPActivity.this );
        builder.setView(layout);
        builder.setCancelable(false);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_Products(edt_tenSP , edt_Don_Gia , edt_soLuong, edt_moTa);
                tv_tenSP.setText( edt_tenSP.getText() );
                tv_gia.setText( edt_Don_Gia.getText() + " VNĐ");
                tv_so_luong.setText("Số lượng : " + edt_soLuong.getText() );
                tv_mo_ta.setText("Mô tả : " + edt_moTa.getText() );
                alertDialog.dismiss();
            }
        });

        btn_update_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    } // end update data

    private void update_Products(EditText edt_name , EditText edt_price,
                                 EditText edt_so_luong , EditText edt_mo_ta){
        // 1. Tạo request
        RequestQueue queue = Volley.newRequestQueue(context);
        // 2. URL ( API )
        String url = "https://polyprojects.herokuapp.com/mobile/update_products";
        // 3. Truyền tham số
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Lấy về 1 đối tượng
                        try {
                            JSONObject product = new JSONObject(response.toString());
                            // bóc tách đối tượng
                            thong_bao(0 , product.getString("status"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("-------------- Error " , error.getMessage());
                    }
                }
        )
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> stringMap = new HashMap<>();
                stringMap.put("maSP" , maSP );
                stringMap.put("tenSP" , edt_name.getText().toString() );
                stringMap.put("donGia" , edt_price.getText().toString() );
                stringMap.put("soLuong" , edt_so_luong.getText().toString() );
                stringMap.put("moTa" , edt_mo_ta.getText().toString() );

                return stringMap;
            }
        };
        // 4. add request vào xử lí
        queue.add(stringRequest);
    } // end update products

    private void thong_bao(int so , String message){
        switch (so){
            case 0:
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                break;
            case 1:
                break;
        }
    } // end thong bao

}