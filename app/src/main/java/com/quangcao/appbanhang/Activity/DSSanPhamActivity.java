package com.quangcao.appbanhang.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.quangcao.appbanhang.API_DAO.FunctionDAO;
import com.quangcao.appbanhang.Models.Product;
import com.quangcao.appbanhang.Models.SanPham;
import com.quangcao.appbanhang.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DSSanPhamActivity extends AppCompatActivity {

    Context context = DSSanPhamActivity.this;
    ListView lv_san_pham;
    FloatingActionButton btn_add;
    List<Product> sanPhamList;
    DSAdapter adapter;
    FunctionDAO functionDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dssan_pham);

        lv_san_pham = findViewById(R.id.lv_san_pham);
        btn_add = findViewById(R.id.fbtn_add_SP);
        functionDAO = new FunctionDAO();
        sanPhamList = new ArrayList<>();

        adapter = new DSAdapter();
        lv_san_pham.setAdapter(adapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.add_sp , null);

                Button btn_add = layout.findViewById(R.id.btn_add);
                Button btn_add_huy = layout.findViewById(R.id.btn_add_huy);
                EditText edt_name = layout.findViewById(R.id.edt_add_ten_SP);
                EditText edt_gia = layout.findViewById(R.id.edt_add_gia_SP);
                EditText edt_soLuong = layout.findViewById(R.id.edt_add_soLuong_SP);
                EditText edt_moTa = layout.findViewById(R.id.edt_add_moTa_SP);

                AlertDialog.Builder builder = new AlertDialog.Builder(DSSanPhamActivity.this );
                builder.setView(layout);
                builder.setCancelable(false);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (edt_name.getText().toString().isEmpty() ){
                            Toast.makeText(context,
                                    "Bạn chưa nhập tên Sản Phẩm"
                                    , Toast.LENGTH_SHORT).show();
                        } else if (edt_gia.getText().toString().isEmpty() ){
                            Toast.makeText(context,
                                    "Bạn chưa nhập giá Sản Phẩm"
                                    , Toast.LENGTH_SHORT).show();
                        } else if (edt_soLuong.getText().toString().isEmpty() ){
                            Toast.makeText(context,
                                    "Bạn chưa nhập số lượng Sản Phẩm"
                                    , Toast.LENGTH_SHORT).show();
                        } else if (edt_moTa.getText().toString().isEmpty() ){
                            Toast.makeText(context,
                                    "Bạn chưa nhập mô tả Sản Phẩm"
                                    , Toast.LENGTH_SHORT).show();
                        } else {
                            insert_Products(edt_name , edt_gia , edt_soLuong , edt_moTa );
                            alertDialog.dismiss();
                        }

                    } // end onclick
                });

                btn_add_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

            }
        });

        lv_san_pham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DSSanPhamActivity.this , ChiTietSPActivity.class);

                intent.putExtra("maSP" , sanPhamList.get(i).getMaSP() );
                intent.putExtra("tenSP" , sanPhamList.get(i).getTenSP() );
                intent.putExtra("donGia" , sanPhamList.get(i).getDonGia() );
                intent.putExtra("soLuong" , sanPhamList.get(i).getSoLuong() );
                intent.putExtra("moTa" , sanPhamList.get(i).getMoTa() );

                startActivity( intent );
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
        public View getView(int pos, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_sp , viewGroup , false);

            TextView tv_tenSP = view.findViewById(R.id.tv_tenSP);
            TextView tv_giaSP = view.findViewById(R.id.tv_giaSP);
            ImageView imv_delete_SP = view.findViewById(R.id.imv_delete_SP);

            tv_tenSP.setText(sanPhamList.get(pos).getTenSP() );
            tv_giaSP.setText( sanPhamList.get(pos).getDonGia() + " VNĐ" );

            imv_delete_SP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setTitle("Thông báo ").setMessage("Bạn có chắc chắn xóa ?");
                    builder.setCancelable(false);

                    builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            delete_Product(sanPhamList.get(pos).getMaSP() );
                            dialogInterface.dismiss();
                        }
                    });

                    builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });

            return view;
        }
    }

    private void show_DS(){
        adapter.notifyDataSetChanged();
        lv_san_pham.setAdapter(adapter);
    }

    public void get_ALL_Products(){
        sanPhamList.clear();
        // 1. Tạo Request
        RequestQueue queue = Volley.newRequestQueue(context);
        // 2. url
        String url = "https://polyprojects.herokuapp.com/mobile/products";
        // 3. Gọi Request
        /* vì ở đây trong mảng có những đối tượng => gọi mảng trước , đối tượng sau */
        // JsonArrayRequest(url , xử lí khi thành công , xử lí khi thất bại)
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                url,
                // khi thành công
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // chuyển mảng => đối tượng
                        // Đọc từng phần tử và chuyển nó sang đối tượng
                        for (int i = 0; i < response.length() ; i++) {
                            try{
                                // Lấy về 1 đối tượng
                                JSONObject product = response.getJSONObject(i);
                                // bóc tách đối tượng
                                String maSP = product.getString("maSP");
                                String tenSP = product.getString("tenSP");
                                String donGia = product.getString("donGia");
                                String soLuong = product.getString("soLuong");
                                String moTa = product.getString("moTa");

                                sanPhamList.add(new Product(maSP , tenSP , donGia , soLuong , moTa));
//                                Log.e("------------------" , "\n"
//                                        + maSP + "\n"
//                                        + tenSP + "\n"
//                                        + donGia + "\n"
//                                        + soLuong + "\n"
//                                        + moTa
//                                );
                                show_DS();
                            } catch (Exception ex){
                                Log.e("---------------Error  " , ex.getMessage() );
                            }
                        }
                        // hiển thị kết quả trên màn hình

                    }
                },
                // khi thất bại
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("-------------- Error " , error.getMessage());
                    }
                }
        );
        // 4. add Request
        queue.add(jsonArrayRequest);

//        Log.e("---------------size", String.valueOf(sanPhamList.size()));
    }

    private void insert_Products(EditText edt_name , EditText edt_price,
                                 EditText edt_so_luong , EditText edt_mo_ta){
        // 1. Tạo request
        RequestQueue queue = Volley.newRequestQueue(context);
        // 2. URL ( API )
        String url = "https://polyprojects.herokuapp.com/mobile/add_products";
        // 3. Truyền tham số
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        get_ALL_Products();
                        thong_bao(0 , "Thêm sản phẩm thành công");
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
                stringMap.put("tenSP" , edt_name.getText().toString() );
                stringMap.put("donGia" , edt_price.getText().toString() );
                stringMap.put("soLuong" , edt_so_luong.getText().toString() );
                stringMap.put("moTa" , edt_mo_ta.getText().toString() );

                return stringMap;
            }
        };
        // 4. add request vào xử lí
        queue.add(stringRequest);
    }

    private void thong_bao(int so , String message){
        switch (so){
            case 0:
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                break;
            case 1:
                break;
        }
    } // end thong bao

    public void delete_Product(String id){
        // 1. Tạo request
        RequestQueue queue = Volley.newRequestQueue(context);
        // 2. URL ( API )
        String url = "https://polyprojects.herokuapp.com/mobile/delete_products";
        // 3. Truyền tham số
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        get_ALL_Products();
                        thong_bao(0 , "Xóa thành công");
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
                stringMap.put("maSP" , id);
                return stringMap;
            }
        };
        // 4. add request vào xử lí
        queue.add(stringRequest);
    } // end delete


    @Override
    protected void onStart() {
        super.onStart();
        get_ALL_Products();
    }
}