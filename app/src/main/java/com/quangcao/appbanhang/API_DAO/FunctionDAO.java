package com.quangcao.appbanhang.API_DAO;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.quangcao.appbanhang.Models.Product;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FunctionDAO {

    // Đọc dữ liệu từ chuỗi
    public void getStringVolley(Context context , TextView textView){
        // 1. Tạo request
        RequestQueue queue = Volley.newRequestQueue(context);
        // 2. URL ( API ) : https://www.google.com/
        String url = "https://www.google.com/";
        // 3. Truyền tham số
        // StringRequest(phương thức , url , xử lí khi thành công , xử lí khi thất bại)
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                // khi thành công
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // vì chuỗi lấy về quá dài nên ta chỉ lấy 500 ký tự
                        textView.setText( "Result : " + response.substring(0 , 500) );
                    }
                },
                // khi thất bại
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText(error.getMessage() );
                    }
                }
        );
        // 4. add request vào xử lí
        queue.add(stringRequest);
    }
    // hàm xử lí JSON ( Mảng của các đối tượng )
    String strJSON = "";
    String errorToString = "";
    public List<Product> get_ALL_Products(Context context , List<Product> productList){
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

                                productList.add(new Product(maSP , tenSP , donGia , soLuong , moTa));
                                Log.e("------------------" , "\n"
                                + maSP + "\n"
                                + tenSP + "\n"
                                + donGia + "\n"
                                + soLuong + "\n"
                                + moTa
                                );
                            } catch (Exception ex){
                                errorToString += ex.getMessage();
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

        Log.e("---------------size", String.valueOf(productList.size()));
        return productList;
    }

}
