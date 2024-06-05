package com.example.collegeapp.UserUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.collegeapp.MainActivity;
import com.example.collegeapp.R;
import com.example.collegeapp.db.UserDbHelper;
import com.example.collegeapp.db.UserInfo;

public class LoginActivity extends AppCompatActivity {
    private EditText et_username;
    private EditText et_password;
    private CheckBox checkbox;
    private boolean is_login;
    private SharedPreferences msharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化控件
        et_username=findViewById(R.id.et_username);
        et_password=findViewById(R.id.et_password);
        checkbox =findViewById(R.id.checkbox);

        //获取msharedPreferences实例
        msharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        //是否记住密码
        msharedPreferences.getBoolean("is_login",false);
        if(is_login){
            String username = msharedPreferences.getString("username", null);
            String password = msharedPreferences.getString("password", null);
            //设置到输入框里面
            et_username.setText(username);
            et_password.setText(password);
            checkbox.setChecked(true);
        }
        //点击注册
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册页面
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        //登录逻辑
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=et_username.getText().toString();
                String password=et_password.getText().toString();
                if(TextUtils.isEmpty(username)&&TextUtils.isEmpty((password))){
                    Toast.makeText(LoginActivity.this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
                }else{
                    UserInfo login = UserDbHelper.getInstance(LoginActivity.this).login(username);
                    if(login!=null)
                    {
                        if(username.equals(login.getUsername()) && password.equals(login.getPassword())){
                            SharedPreferences.Editor edit =msharedPreferences.edit();
                            edit.putBoolean("is_login",is_login);
                            edit.putString("username",username);
                            edit.putString("password",password);
                            //提交
                            edit.commit();
                            //登录成功
                            Intent intent =new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "账号暂未注册哦~", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //checkbox的点击事件
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                is_login=isChecked;
            }
        });
    }
}