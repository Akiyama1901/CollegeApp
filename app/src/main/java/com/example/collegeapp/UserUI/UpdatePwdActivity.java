package com.example.collegeapp.UserUI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.collegeapp.R;
import com.example.collegeapp.db.UserDbHelper;
import com.example.collegeapp.db.UserInfo;

public class UpdatePwdActivity extends AppCompatActivity {
    private EditText et_new_password;
    private EditText et_confirm_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pwd);

        // 初始化控件
        et_new_password =findViewById(R.id.et_new_password);
        et_confirm_password=findViewById(R.id.et_confirm_password);

        //修改密码的点击事件
        findViewById(R.id.btn_update_pwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取数据
                String new_pwd= et_new_password.getText().toString();
                String confirm_pwd=et_confirm_password.getText().toString();

                if(TextUtils.isEmpty(new_pwd)||TextUtils.isEmpty(confirm_pwd)){
                    Toast.makeText(UpdatePwdActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                }else if(!new_pwd.equals(confirm_pwd)){
                    Toast.makeText(UpdatePwdActivity.this, "两次输入不一致", Toast.LENGTH_SHORT).show();
                }else{
                    UserInfo  userInfo=UserInfo.getUserInfo();
                    if(userInfo!=null){
                        int row = UserDbHelper.getInstance(UpdatePwdActivity.this).updatePwd(userInfo.getUsername(), new_pwd);
                        if(row>0){
                            Toast.makeText(UpdatePwdActivity.this, "密码修改成功，请重新登录", Toast.LENGTH_SHORT).show();
                            //回传
                            setResult(1000);
                            finish();
                        }else{
                            Toast.makeText(UpdatePwdActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });

        //返回按钮
        findViewById(R.id.toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}