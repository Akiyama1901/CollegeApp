package com.example.collegeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.collegeapp.Fragment.CourseFragment;
import com.example.collegeapp.Fragment.HomeFragment;
import com.example.collegeapp.Fragment.NoteFragment;
import com.example.collegeapp.Fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private HomeFragment homeFragment;
    private CourseFragment courseFragment;
    private NoteFragment noteFragment;
    private UserFragment userFragment;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控件
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        //点击事件
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                selectedFragment(0);
                } else if (item.getItemId() == R.id.course) {
                    selectedFragment(1);
                } else if (item.getItemId() == R.id.write) {
                    selectedFragment(2);
                } else {
                    selectedFragment(3);
                }

                return true;
            }
        });
        selectedFragment(0);
    }

        //默认首页选中
        private void selectedFragment(int position){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            hideFragment(fragmentTransaction);
            if(position==0){
                if(homeFragment==null){
                    homeFragment = new HomeFragment();
                    fragmentTransaction.add(R.id.content,homeFragment);
                }else{
                    fragmentTransaction.show(homeFragment);
                }
            }
            else if(position==1){
                if(courseFragment==null){
                    courseFragment = new CourseFragment();
                    fragmentTransaction.add(R.id.content,courseFragment);
                }else{
                    fragmentTransaction.show(courseFragment);
                }
            }
            else if(position==2){
                if(noteFragment==null){
                    noteFragment = new NoteFragment();
                    fragmentTransaction.add(R.id.content,noteFragment);
                }else{
                    fragmentTransaction.show(noteFragment);
                }
            }
            else{
                if(userFragment==null){
                    userFragment = new UserFragment();
                    fragmentTransaction.add(R.id.content,userFragment);
                }else{
                    fragmentTransaction.show(userFragment);
                }
            }
            fragmentTransaction.commit();
    }
    private void hideFragment(FragmentTransaction fragmentTransaction){
        if(homeFragment!=null){
            fragmentTransaction.hide(homeFragment);
        }
        if(courseFragment!=null){
            fragmentTransaction.hide(courseFragment);
        }
        if(noteFragment!=null){
            fragmentTransaction.hide(noteFragment);
        }
        if(userFragment!=null){
            fragmentTransaction.hide(userFragment);
        }
    }
}