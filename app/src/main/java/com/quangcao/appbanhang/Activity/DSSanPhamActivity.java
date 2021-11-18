package com.quangcao.appbanhang.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.quangcao.appbanhang.Models.SanPham;
import com.quangcao.appbanhang.R;

import java.util.ArrayList;
import java.util.List;

public class DSSanPhamActivity extends AppCompatActivity {

    ListView lv_san_pham;
    FloatingActionButton btn_add;
    List<SanPham> sanPhamList;
    DSAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dssan_pham);

        lv_san_pham = findViewById(R.id.lv_san_pham);
        btn_add = findViewById(R.id.fbtn_add_SP);
        sanPhamList = new ArrayList<>();

        sanPhamList.add(new SanPham("kem" , "3000") );
        sanPhamList.add(new SanPham("kem 2" , "3000") );
        sanPhamList.add(new SanPham("kem 3" , "3000") );

        adapter = new DSAdapter();
        lv_san_pham.setAdapter(adapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.add_sp , null);

                Button btn_add = layout.findViewById(R.id.btn_add);

                AlertDialog.Builder builder = new AlertDialog.Builder(DSSanPhamActivity.this );
                builder.setView(layout);

                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sanPhamList.add(new SanPham("new" , "999") );
                        show_DS();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        lv_san_pham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(DSSanPhamActivity.this , ChiTietSPActivity.class)
                        .putExtra("tenSP" , sanPhamList.get(i).getTenSP() )
                );
            }
        });
    }

    private class DSAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return sanPhamList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_sp , viewGroup , false);

            TextView tv_tenSP = view.findViewById(R.id.tv_tenSP);
            TextView tv_giaSP = view.findViewById(R.id.tv_giaSP);
            ImageView imv_delete_SP = view.findViewById(R.id.imv_delete_SP);

            tv_tenSP.setText(sanPhamList.get(i).getTenSP() );
            tv_giaSP.setText( sanPhamList.get(i).getGia() );

            imv_delete_SP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sanPhamList.remove(i);
                    notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "xoas", Toast.LENGTH_SHORT).show();
                }
            });

            return view;
        }
    }

    private void show_DS(){
        adapter.notifyDataSetChanged();
        lv_san_pham.setAdapter(adapter);
    }
}