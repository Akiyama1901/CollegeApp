package com.example.collegeapp;

import android.content.Intent;
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

public class EditActivity extends AppCompatActivity {

    private Note note;
    private EditText etTitle,etContent;
    private NoteDbOpenHelper mnoteDbOpenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etTitle = findViewById(R.id.et_title);
        etContent=findViewById(R.id.et_content);

        initData();
    }

    private void initData() {
        Intent intent =getIntent();
        note =(Note) intent.getSerializableExtra("note");
        if(note!=null){
            etTitle.setText(note.getTitle());
            etContent.setText(note.getContent());
        }
        mnoteDbOpenHelper=new NoteDbOpenHelper(this);
    }

    public void save(View view) {
        String title =etTitle.getText().toString();
        String content =etContent.getText().toString();
        if(TextUtils.isEmpty(title)){
            ToastUtil.toastShort(this,"标题不能为空");
            return;
        }

        note.setTitle(title);
        note.setContent(content);
        note.setCreatedTime(getCurrentTimeFormat());
        long rowId = mnoteDbOpenHelper.updateData(note);
        if(rowId!=-1){
            ToastUtil.toastShort(this,"修改成功");
            this.finish();
        }
        else{
            ToastUtil.toastShort(this,"修改失败");
        }
    }
    private String getCurrentTimeFormat()
    {
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy年MM月dd HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
}