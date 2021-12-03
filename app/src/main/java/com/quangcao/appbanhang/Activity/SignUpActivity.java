package com.quangcao.appbanhang.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.quangcao.appbanhang.MainActivity;
import com.quangcao.appbanhang.R;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    EditText edt_tk , edt_mk , edt_lai_mk;
    Context context = SignUpActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edt_tk = findViewById(R.id.edt_dk_tk);
        edt_mk = findViewById(R.id.edt_dk_mk);
        edt_lai_mk = findViewById(R.id.edt_lai_mk);
    }

    public void tao_TK(View view){
        String tk = edt_tk.getText().toString();
        String mk = edt_mk.getText().toString();
        String lai_mk = edt_lai_mk.getText().toString();

        if ( tk.isEmpty() && mk.isEmpty() && lai_mk.isEmpty() ){
            Toast.makeText(getApplicationContext(), "Không được để trống !", Toast.LENGTH_SHORT).show();
        } else if ( tk.isEmpty() ){
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập Tài Khoản", Toast.LENGTH_SHORT).show();
        } else if ( mk.isEmpty() ){
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập Mật Khẩu", Toast.LENGTH_SHORT).show();
        } else if ( lai_mk.isEmpty() ){
            Toast.makeText(getApplicationContext(), "Nhập lại Mật Khẩu", Toast.LENGTH_SHORT).show();
        } else if ( ! mk.equals(lai_mk)  ){
            Toast.makeText(getApplicationContext(), "Bạn nhập sai Mật Khẩu", Toast.LENGTH_SHORT).show();
        } else {
            sign_UP(tk , mk);
        }
    }

    private void sign_UP(String tk, String mk){
        // 1. Tạo request
        RequestQueue queue = Volley.newRequestQueue(context);
        // 2. URL ( API )
        String url = "https://polyprojects.herokuapp.com/mobile/signup";
        // 3. Truyền tham số
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("---------------------" , response);
                        Intent intent = new Intent(SignUpActivity.this , MainActivity.class );
                        intent.putExtra("tk" , tk);
                        intent.putExtra("mk" , mk);
                        startActivity(intent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("----------- Error" , error.getMessage());
                    }
                }
        )
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> stringMap = new HashMap<>();
                stringMap.put("tk" , tk);
                stringMap.put("mk" , mk);
                return stringMap;
            }
        };
        // 4. add request vào xử lí
        queue.add(stringRequest);
    }

    public void ShowHidePass_2(View view) {

        if(view.getId()==R.id.show_pass_btn_2){
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

    public void ShowHidePass_3(View view) {

        if(view.getId()==R.id.show_pass_btn_3){
            if(edt_lai_mk.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.show_eye_icon);
                //Show Password
                edt_lai_mk.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.eye_off_icon);
                //Hide Password
                edt_lai_mk.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }

}