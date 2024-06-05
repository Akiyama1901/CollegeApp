package com.example.collegeapp.UserUI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.collegeapp.R;
import com.example.collegeapp.db.UserDbHelper;


public class RegisterActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //初始化控件
        et_username=findViewById(R.id.et_username);
        et_password=findViewById(R.id.et_password);
        //返回
        findViewById(R.id.toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //不是跳转 销毁当前页面
                finish();
            }
        });
        //点击注册
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();

                if(TextUtils.isEmpty(username)&&TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "请输入用户名或密码", Toast.LENGTH_SHORT).show();
                }else{
                    int row = UserDbHelper.getInstance(RegisterActivity.this).register(username, password, "这个人很懒，什么都没有留下~");
                    //登录成功
                    if(row>0){
                        Toast.makeText(RegisterActivity.this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
    }
}