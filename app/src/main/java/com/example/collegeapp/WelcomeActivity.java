package com.example.collegeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    private TextView tvCountdown;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 3000; //设置倒计时时长，单位为毫秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //初始化控件
        tvCountdown = findViewById(R.id.tv_countdown);
        // 启动倒计时
        startCountdown();
    }

    private void startCountdown() {
        countDownTimer =new CountDownTimer(timeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                int secondsRemaining = (int) (millisUntilFinished / 1000);
                tvCountdown.setText(secondsRemaining +" s");
            }

            @Override
            public void onFinish() {
                //跳转到登录页面
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                //倒计时结束后的操作
                finish();
            }
        }.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}