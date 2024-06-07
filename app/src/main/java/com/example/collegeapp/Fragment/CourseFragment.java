package com.example.collegeapp.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.collegeapp.ColorUtils;
import com.example.collegeapp.db.Course;
import com.example.collegeapp.CornerTextView;
import com.example.collegeapp.R;
import com.example.collegeapp.db.CourseDbOpenHelper;

import java.util.Calendar;
import java.util.List;

public class CourseFragment extends Fragment {

    private LinearLayout[] weekPanels;
    private LinearLayout weekNames;
    private LinearLayout sections;
    private MaterialRefreshLayout mFreshLayout;
    private int itemHeight;
    private int maxSection = 12;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course, container, false);

        weekPanels = new LinearLayout[7];
        weekPanels[0] = view.findViewById(R.id.weekPanel_1);
        weekPanels[1] = view.findViewById(R.id.weekPanel_2);
        weekPanels[2] = view.findViewById(R.id.weekPanel_3);
        weekPanels[3] = view.findViewById(R.id.weekPanel_4);
        weekPanels[4] = view.findViewById(R.id.weekPanel_5);
        weekPanels[5] = view.findViewById(R.id.weekPanel_6);
        weekPanels[6] = view.findViewById(R.id.weekPanel_7);

        weekNames = view.findViewById(R.id.weekNames);
        sections = view.findViewById(R.id.sections);
        mFreshLayout = view.findViewById(R.id.mFreshLayout);

        itemHeight = getResources().getDimensionPixelSize(R.dimen.sectionHeight);

        initWeekNameView();
        initSectionView();
        loadCourseData();
        setRefreshListener();

        return view;
    }

    private void initWeekNameView() {
        for (int i = 0; i < weekPanels.length + 1; i++) {
            TextView tvWeekName = new TextView(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER | Gravity.CENTER_HORIZONTAL;
            if (i != 0) {
                lp.weight = 1;
                tvWeekName.setText("周" + intToZH(i));
                if (i == getWeekDay()) {
                    tvWeekName.setTextColor(Color.parseColor("#FF0000"));
                } else {
                    tvWeekName.setTextColor(Color.parseColor("#4A4A4A"));
                }
            } else {
                lp.weight = 0.8f;
                tvWeekName.setText(getMonth() + "月");
            }
            tvWeekName.setGravity(Gravity.CENTER_HORIZONTAL);
            tvWeekName.setLayoutParams(lp);
            weekNames.addView(tvWeekName);
        }
    }

    private void initSectionView() {
        for (int i = 1; i <= maxSection; i++) {
            TextView tvSection = new TextView(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, getResources().getDimensionPixelSize(R.dimen.sectionHeight));
            lp.gravity = Gravity.CENTER;
            tvSection.setGravity(Gravity.CENTER);
            tvSection.setText(String.valueOf(i));
            tvSection.setLayoutParams(lp);
            sections.addView(tvSection);
        }
    }

    private void loadCourseData() {
        CourseDbOpenHelper dbOpenHelper = new CourseDbOpenHelper(getContext());
        List<Course> courseData = dbOpenHelper.getAllCourses();

        for (Course course : courseData) {
            addCourseView(weekPanels[course.getWeekDay() - 1], course);
        }
    }


    private void addCourseView(LinearLayout weekPanel, Course course) {
        CornerTextView courseView = new CornerTextView(getContext(), ColorUtils.getCourseBgColor(course.getCourseFlag()), 16);
        courseView.setText(course.getCourseName() + "\n" + course.getClassRoom());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                course.getSectionSpan() * itemHeight
        );
        params.topMargin = (course.getSection() - 1) * itemHeight;
        courseView.setLayoutParams(params);
        Log.d("CourseFragment", "Adding course view with height: " + (course.getSectionSpan() * itemHeight) +
                " and top margin: " + ((course.getSection() - 1) * itemHeight));

        weekPanel.addView(courseView);
    }

    private void setRefreshListener() {
        mFreshLayout.setLoadMore(false);
        mFreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                clearChildView();
                loadCourseData();
                mFreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mFreshLayout.finishRefreshing();
                    }
                }, 500);
            }
        });
    }

    private void clearChildView() {
        for (int i = 0; i < weekPanels.length; i++) {
            if (weekPanels[i] != null) {
                if (weekPanels[i].getChildCount() > 0) {
                    weekPanels[i].removeAllViews();
                }
            }
        }
    }

    private int getWeekDay() {
        int w = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        if (w <= 0) {
            w = 7;
        }
        return w;
    }

    private int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static String intToZH(int i) {
        String[] zh = {"零", "一", "二", "三", "四", "五", "六", "日"};
        return zh[i];
    }
}
