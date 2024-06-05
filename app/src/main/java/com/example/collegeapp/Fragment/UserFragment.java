package com.example.collegeapp.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.collegeapp.UserUI.AboutActivity;
import com.example.collegeapp.UserUI.LoginActivity;
import com.example.collegeapp.UserUI.PhoneActivity;
import com.example.collegeapp.R;
import com.example.collegeapp.UserUI.UpdatePwdActivity;
import com.example.collegeapp.UserUI.UpdateUserInfoActivity;
import com.example.collegeapp.db.UserInfo;

public class UserFragment extends Fragment {
    private View rootview;
    private TextView tv_username;
    private TextView tv_nickname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_user, container, false);

        //初始化控件
        tv_username = rootview.findViewById(R.id.tv_username);
        tv_nickname = rootview.findViewById(R.id.tv_nickname);

        //退出登录
        rootview.findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        //链式调用
                        .setTitle("温馨提示")
                        .setMessage("确定要退出登录吗？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //退出登录
                                //销毁当前页面
                                getActivity().finish();
                                //启动登录页面
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        });

        //修改密码
        rootview.findViewById(R.id.updatePwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), UpdatePwdActivity.class);
                startActivityForResult(intent,1000);
            }
        });


        //关于APP
        rootview.findViewById(R.id.about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });
        //常用电话

        rootview.findViewById(R.id.usefulPhone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), PhoneActivity.class);
                startActivity(intent);
            }
        });

        //全校课表查询
        rootview.findViewById(R.id.allCourseinCollege).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://jw.xujc.com/pub/index.php?c=Default&a=cr"));
                startActivity(intent);
            }

        });

        //OJ平台
        rootview.findViewById(R.id.userinfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://xujcoj.com/"));
                startActivity(intent);
            }
        });

        //个性签名
        rootview.findViewById(R.id.userNick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpdateUserInfoActivity.class);
                startActivityForResult(intent, 2000);
            }
        });
        return rootview;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //绑定数据
        UserInfo userInfo =UserInfo.getUserInfo();
        if(userInfo!=null) {
            tv_username.setText(userInfo.getUsername());
            tv_nickname.setText(userInfo.getNickname());
        }
    }
    //接收

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1000){
            //销毁当前页面
            getActivity().finish();
            //打开登录页面
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            startActivity(intent);
        }
        if (requestCode == 2000) {
            // 重新加载用户信息
            UserInfo userInfo = UserInfo.getUserInfo();
            if (userInfo != null) {
                tv_nickname.setText(userInfo.getNickname());
            }
        }
    }
}
