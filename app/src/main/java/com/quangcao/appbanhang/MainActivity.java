package com.quangcao.appbanhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.quangcao.appbanhang.Activity.DSSanPhamActivity;
import com.quangcao.appbanhang.Activity.SignUpActivity;
import com.quangcao.appbanhang.Models.Product;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText edt_tk , edt_mk;
    CheckBox cb_TK;
    Context context = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_tk = findViewById(R.id.edt_tk);
        edt_mk = findViewById(R.id.edt_mk);
        cb_TK = findViewById(R.id.checkBox_save);
        cb_TK.setChecked(true);

        Intent intent = getIntent();
        String user = intent.getStringExtra("tk");
        String pass = intent.getStringExtra("mk");
        if ( user != null && pass != null ){
            edt_tk.setText(user);
            edt_mk.setText(pass);
        } else {
            SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
            String tk = pref.getString("USERNAME", null);
            String mk = pref.getString("PASSWORD", null);
            boolean nho = pref.getBoolean("REMEMBER", true);
            if (tk != null && mk != null){
                edt_tk.setText(String.valueOf(tk));
                edt_mk.setText(String.valueOf(mk));
                cb_TK.setChecked(true);
//                Log.e("-----------login test", String.valueOf(nho) + "\t" + tk + "\t\t" + mk);
            }
        }
    }

    public void login(View view){
        String tk = edt_tk.getText().toString();
        String mk = edt_mk.getText().toString();

        if ( tk.isEmpty() && mk.isEmpty() ){
            Toast.makeText(getApplicationContext(), "Không được để trống !", Toast.LENGTH_SHORT).show();
        } else if ( tk.isEmpty() ){
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập Tài Khoản", Toast.LENGTH_SHORT).show();
        } else if ( mk.isEmpty() ){
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập Mật Khẩu", Toast.LENGTH_SHORT).show();
        } else {
            dang_nhap(tk , mk);
        }
    }

    public void sign_up(View  view){
        startActivity(new Intent(MainActivity.this , SignUpActivity.class));
    }

    public void ShowHidePass(View view) {

        if(view.getId()==R.id.show_pass_btn){
            if(edt_mk.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.show_eye_icon);
                //Show Password
                edt_mk.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.eye_off_icon);
                //Hide Password
                edt_mk.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }

    public void rememberUser(String u, String p, boolean status) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();

        if (!status) {
            //xoa tinh trang luu tru truoc do
            edit.clear();
        } else {
            //luu du lieu
            edit.putString("USERNAME", u);
            edit.putString("PASSWORD", p);
            edit.putBoolean("REMEMBER", status);
//            Toast.makeText(getApplicationContext(), "Đã nhớ tài khoản",
//                    Toast.LENGTH_SHORT).show();

        }
        // Lưu lại toàn bộ
        if (edit.commit()) {
//            Toast.makeText(getApplicationContext(), "Lưu tài khoản thành công",
//                    Toast.LENGTH_SHORT).show();
        }
        Log.e("----- da luu tai khoan", String.valueOf(status) + "\n\t" + u + "\n\t" + p);
    }

    private void dang_nhap(String userName , String password){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://polyprojects.herokuapp.com/mobile/users";

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
                                JSONObject user = response.getJSONObject(i);
                                // bóc tách đối tượng
                                int id = user.getInt("id");
                                String tk = user.getString("tk");
                                String mk = user.getString("mk");

                                if ( (tk.equals(userName)) && (mk.equals(password)) ){
                                    startActivity(new Intent(MainActivity.this , DSSanPhamActivity.class));
                                    rememberUser(tk , mk , cb_TK.isChecked() );
                                    Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else if ( (tk.equals(userName)) && (! mk.equals(password)) ){
                                    Toast.makeText(getApplicationContext(), "Bạn nhập sai Mật Khẩu", Toast.LENGTH_SHORT).show();

                                }  else if ( (! tk.equals(userName)) && (mk.equals(password)) ){
                                    Toast.makeText(getApplicationContext(), "Bạn nhập sai Tài Khoản", Toast.LENGTH_SHORT).show();
                                }

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
    }

}