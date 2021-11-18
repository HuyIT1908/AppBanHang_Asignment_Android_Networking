package com.quangcao.appbanhang.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.quangcao.appbanhang.Models.SanPham;
import com.quangcao.appbanhang.R;

public class ChiTietSPActivity extends AppCompatActivity {

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String tenSP = getIntent().getStringExtra("tenSP");
        setTitle(tenSP);
    }

    public void update_DATA(View view){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.add_sp , null);

        TextView tv_title = layout.findViewById(R.id.tv_tieu_de_dialog);
        Button btn_add = layout.findViewById(R.id.btn_add);
        tv_title.setText("Cập nhật sản phẩm");
        btn_add.setText("Cập Nhật");

        AlertDialog.Builder builder = new AlertDialog.Builder(ChiTietSPActivity.this );
        builder.setView(layout);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "sua du lieu", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}