package com.example.collegeapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.collegeapp.Util.ToastUtil;
import com.example.collegeapp.db.Note;
import com.example.collegeapp.db.NoteDbOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {
    //初始化控件
    private EditText etTitle,etContent;
    private NoteDbOpenHelper mnoteDbOpenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //控件绑定
        etTitle = findViewById(R.id.et_title);
        etContent=findViewById(R.id.et_content);
        mnoteDbOpenHelper=new NoteDbOpenHelper(this);
    }

    public void add(View view) {
        String title =etTitle.getText().toString();
        String content =etContent.getText().toString();
        if(TextUtils.isEmpty(title)){
            ToastUtil.toastShort(this,"标题不能为空");
            return;
        }

        //保存实体
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setCreatedTime(getCurrentTimeFormat());
        long row = mnoteDbOpenHelper.insertDate(note);

        if(row!=-1){
            ToastUtil.toastShort(this,"添加成功！");
            this.finish();
        }else{
            ToastUtil.toastShort(this,"添加失败！");
        }
    }
    private String getCurrentTimeFormat()
    {
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy年MM月dd HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
}