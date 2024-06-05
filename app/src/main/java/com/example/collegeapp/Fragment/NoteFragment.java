package com.example.collegeapp.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.collegeapp.AddActivity;
import com.example.collegeapp.MyAdapter;
import com.example.collegeapp.R;
import com.example.collegeapp.UserUI.UpdatePwdActivity;
import com.example.collegeapp.Util.SpfUtil;
import com.example.collegeapp.db.Note;
import com.example.collegeapp.db.NoteDbOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoteFragment extends Fragment {
    //控件声明
    private View rootview;
    private RecyclerView mrecyclerView;
    private List<Note> mNotes;
    private MyAdapter myAdapter;
    private NoteDbOpenHelper mNoteDbOpenHelper;
    //当前列表类型
    private static final int MODE_LINEAR = 0;
    private static final int MODE_GRIM = 1;

    public static final String KEY_LAYOUT_MODE = "key_layout_mode";

    private int currentListLayoutMode = MODE_LINEAR;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_note, container, false);
        Toolbar toolbar = rootview.findViewById(R.id.toolbar);
        //这一行代码给我改了快两天
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        initView();
        initData();
        initEvent();
        rootview.findViewById(R.id.add).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivity(intent);
            }
        });
        return rootview;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshDataFromDb();
        setListLayout();
    }

    private void refreshDataFromDb() {
        mNotes = getDataFromDB();
        myAdapter.refreshData(mNotes);
    }

    private List<Note> getDataFromDB() {
        return mNoteDbOpenHelper.queryAllFromDb();
    }

    private String getCurrentTimeFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    private void initView() {
        mrecyclerView = rootview.findViewById(R.id.rlv);
    }

    private void initData() {
        mNotes = new ArrayList<>();
        mNoteDbOpenHelper = new NoteDbOpenHelper(getActivity());
        //        for (int i = 0; i < 30; i++) {
//            Note note=new Note();
//            note.setTitle("这是标题"+i);
//            note.setContent("这是内容"+i);
//            note.setCreatedTime(getCurrentTimeFormat());
//            mNote.add(note);
//        }
        //onResume拿了一遍数据，这里再拿重复了
//        mNotes= getDataFromDB();
    }

    private void initEvent() {
        myAdapter = new MyAdapter(getActivity(), mNotes);
        mrecyclerView.setAdapter(myAdapter);
        //布局管理器
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        mrecyclerView.setLayoutManager(linearLayoutManager);
//        myAdapter.setViewType(MyAdapter.TYPE_LINEAR_LAYOUT);
        setListLayout();
    }

    private void setListLayout() {
        currentListLayoutMode = SpfUtil.getIntWithDefault(getActivity(), KEY_LAYOUT_MODE, MODE_LINEAR);
        if (currentListLayoutMode == MODE_LINEAR) {
            setToLinearLayout();
        } else {
            setToGrimLayout();
        }
    }

    private void setToLinearLayout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mrecyclerView.setLayoutManager(linearLayoutManager);
        myAdapter.setViewType(MyAdapter.TYPE_LINEAR_LAYOUT);
        myAdapter.notifyDataSetChanged();
    }

    private void setToGrimLayout() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mrecyclerView.setLayoutManager(gridLayoutManager);
        myAdapter.setViewType(MyAdapter.TYPE_GRIM_LAYOUT);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        //创建SearView
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            //用下面这个
            @Override
            public boolean onQueryTextChange(String newText) {
                mNotes = mNoteDbOpenHelper.queryFromDbByTitle(newText);
                //刷新数据
                myAdapter.refreshData(mNotes);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.menu_linear:
                setToLinearLayout();
                currentListLayoutMode = MODE_LINEAR;
                SpfUtil.saveInt(getActivity(), KEY_LAYOUT_MODE, MODE_LINEAR);
                return true;
            case R.id.menu_grid:
                setToGrimLayout();
                currentListLayoutMode = MODE_GRIM;
                SpfUtil.saveInt(getActivity(), KEY_LAYOUT_MODE, MODE_GRIM);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        if (currentListLayoutMode == MODE_LINEAR) {
            MenuItem item = menu.findItem(R.id.menu_linear);
            item.setChecked(true);
        } else {
            menu.findItem(R.id.menu_grid).setChecked(true);
        }
    }
}
