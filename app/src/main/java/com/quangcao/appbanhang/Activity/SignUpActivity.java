package com.quangcao.appbanhang.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.quangcao.appbanhang.MainActivity;
import com.quangcao.appbanhang.R;

public class SignUpActivity extends AppCompatActivity {

    EditText edt_tk , edt_mk , edt_lai_mk;

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
            Intent intent = new Intent(SignUpActivity.this , MainActivity.class );
            intent.putExtra("tk" , tk);
            intent.putExtra("mk" , mk);
            startActivity(intent);
            finish();
        }
    }

}