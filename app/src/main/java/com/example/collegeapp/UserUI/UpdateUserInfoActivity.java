package com.example.collegeapp.UserUI;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.collegeapp.R;
import com.example.collegeapp.db.UserDbHelper;
import com.example.collegeapp.db.UserInfo;

public class UpdateUserInfoActivity extends AppCompatActivity {
    private EditText etNickname;
    private Button btnSave;
    private UserDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);
        etNickname = findViewById(R.id.et_nickname);
        btnSave = findViewById(R.id.btn_save);
        dbHelper = UserDbHelper.getInstance(this);

        // 显示当前用户信息
        UserInfo userInfo = UserInfo.getUserInfo();
        if (userInfo != null) {
            etNickname.setText(userInfo.getNickname());
        }

        // 保存按钮点击事件
        btnSave.setOnClickListener(view -> {
            String nickname = etNickname.getText().toString().trim();

            ContentValues values = new ContentValues();
            values.put("nickname", nickname);

            dbHelper.updateUserInfo(userInfo.getUsername(), values);

            // 更新当前用户信息
            userInfo.setNickname(nickname);

            // 返回并刷新 UserFragment
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        });
        //返回
        findViewById(R.id.toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
