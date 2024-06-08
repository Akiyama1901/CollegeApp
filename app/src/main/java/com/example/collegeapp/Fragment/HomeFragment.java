package com.example.collegeapp.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.collection.ArraySet;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.collegeapp.BannerDataInfo;
import com.example.collegeapp.R;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private Banner mBanner;
    private List<BannerDataInfo> mBannerDataInfoList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        //初始化控件
        mBanner = view.findViewById(R.id.banner);

        //添加banner数据
        mBannerDataInfoList.add(new BannerDataInfo(R.mipmap.banner1, "这是图片一"));
        mBannerDataInfoList.add(new BannerDataInfo(R.mipmap.banner2, "这是图片二"));
        mBannerDataInfoList.add(new BannerDataInfo(R.mipmap.banner3, "这是图片三"));

        //设置适配器
        mBanner.setAdapter(new BannerImageAdapter<BannerDataInfo>(mBannerDataInfoList) {

            @Override
            public void onBindView(BannerImageHolder holder, BannerDataInfo data, int position, int size) {
                //设置数据
                holder.imageView.setImageResource(data.getImg());
            }
        });

        //添加生命周期观察者
        mBanner.addBannerLifecycleObserver(this);
        //设置指示器
        mBanner.setIndicator(new CircleIndicator(getActivity()));
        //banner点击事件
        mBanner.setOnBannerListener(new OnBannerListener<BannerDataInfo>() {
            @Override
            public void OnBannerClick(BannerDataInfo data, int position) {
                Intent intent =new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://xujc.com/"));
                startActivity(intent);
            }
        });
        //综合教务
        view.findViewById(R.id.all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://jw.xujc.com/"));
                startActivity(intent);
            }
        });
        //教学文件
        view.findViewById(R.id.file).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://teach.xujc.com/"));
                startActivity(intent);
            }
        });
        //体育出勤
        view.findViewById(R.id.sport).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://tyxt.xujc.com:8585/EPLATE/"));
                startActivity(intent);
            }
        });

        //电子邮件
        view.findViewById(R.id.sport).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://mail.xujc.com/extend/gb/"));
                startActivity(intent);
            }
        });

        //学工平台
        view.findViewById(R.id.sport).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://ijg.xujc.com/login"));
                startActivity(intent);
            }
        });

        //新闻中心
        view.findViewById(R.id.sport).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.xujc.com/news/xxxw.html"));
                startActivity(intent);
            }
        });
        //图书查询
        view.findViewById(R.id.sport).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://library.xujc.com/"));
                startActivity(intent);
            }
        });

        //校园服务
        view.findViewById(R.id.sport).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://xyfw.xujc.com/"));
                startActivity(intent);
            }
        });
        return view;
    }
}