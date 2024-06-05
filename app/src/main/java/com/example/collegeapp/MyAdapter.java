package com.example.collegeapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeapp.Util.ToastUtil;
import com.example.collegeapp.db.Note;
import com.example.collegeapp.db.NoteDbOpenHelper;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //数据实体
    private List<Note> mNoteList;
    private LayoutInflater mlayoutInflater;
    private Context mcontext;
    private NoteDbOpenHelper mNoteDbOpenHelper;

    //视图类型
    private int viewType;
    public static int TYPE_LINEAR_LAYOUT=0;
    public static int TYPE_GRIM_LAYOUT=1;


    //构造方法
    public MyAdapter(Context context,List<Note> mNoteList){
        this.mNoteList=mNoteList;
        this.mcontext=context;
        mlayoutInflater=LayoutInflater.from(mcontext);
        mNoteDbOpenHelper =new NoteDbOpenHelper(mcontext);

    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==TYPE_LINEAR_LAYOUT)
        {
            View view = mlayoutInflater.inflate(R.layout.list_item_layout, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }else if(viewType==TYPE_GRIM_LAYOUT){
            View view = mlayoutInflater.inflate(R.layout.list_item_grid_layout, parent, false);
            MyGridViewHolder myGridViewHolder = new MyGridViewHolder(view);
            return myGridViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if(holder==null) return;

        if(holder instanceof MyViewHolder){
            bindMyViewHolder((MyViewHolder)holder,position);
        }else if(holder instanceof MyGridViewHolder) {
            bindMyGridViewHolder((MyGridViewHolder)holder,position);
        }
    }


    public void bindMyViewHolder(MyViewHolder holder,int position){
        Note note =mNoteList.get(position);
        holder.mTvTitle.setText(note.getTitle());
        holder.mTvContent.setText(note.getContent());
        holder.mTvTime.setText(note.getCreatedTime());
        holder.rlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext,EditActivity.class);
                intent.putExtra("note",note);
                mcontext.startActivity(intent);
            }
        });

        //长按弹窗
        holder.rlContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Dialog dialog =new Dialog(mcontext, android.R.style.ThemeOverlay_Material_Dialog_Alert);
                View view=mlayoutInflater.inflate(R.layout.list_item_dialog_layout,null);

                TextView tvDelete =view.findViewById(R.id.tv_delete);
                TextView tvEdit =view.findViewById(R.id.tv_edit);

                //点击事件
                tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int row = mNoteDbOpenHelper.deleteFromDbById(note.getId());
                        if(row>0){
                            removeData(position);
                            ToastUtil.toastShort(mcontext,"删除成功！");
                        }else{
                            ToastUtil.toastShort(mcontext,"删除失败！");
                        }
                        dialog.dismiss();
//                        removeData(position);
//                        如果这样子只是显示删除 数据库里面还没有删除
                    }
                });

                tvEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//跳转就好
                        Intent intent =new Intent(mcontext,EditActivity.class);
                        intent.putExtra("note",note);
                        mcontext.startActivity(intent);
                        dialog.dismiss();//销毁弹窗
                    }
                });


                dialog.setContentView(view);

                dialog.show();
                return false;
            }
        });
    }
    public void bindMyGridViewHolder(MyGridViewHolder holder,int position){
        Note note =mNoteList.get(position);
        holder.mTvTitle.setText(note.getTitle());
        holder.mTvContent.setText(note.getContent());
        holder.mTvTime.setText(note.getCreatedTime());
        holder.rlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext,EditActivity.class);
                intent.putExtra("note",note);
                mcontext.startActivity(intent);
            }
        });

        //长按弹窗
        holder.rlContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Dialog dialog =new Dialog(mcontext, android.R.style.ThemeOverlay_Material_Dialog_Alert);
                View view=mlayoutInflater.inflate(R.layout.list_item_dialog_layout,null);

                TextView tvDelete =view.findViewById(R.id.tv_delete);
                TextView tvEdit =view.findViewById(R.id.tv_edit);

                //点击事件
                tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int row = mNoteDbOpenHelper.deleteFromDbById(note.getId());
                        if(row>0){
                            removeData(position);
                            ToastUtil.toastShort(mcontext,"删除成功！");
                        }else{
                            ToastUtil.toastShort(mcontext,"删除失败！");
                        }
                        dialog.dismiss();
//                        removeData(position);
//                        如果这样子只是显示删除 数据库里面还没有删除
                    }
                });

                tvEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//跳转就好
                        Intent intent =new Intent(mcontext,EditActivity.class);
                        intent.putExtra("note",note);
                        mcontext.startActivity(intent);
                        dialog.dismiss();//销毁弹窗
                    }
                });


                dialog.setContentView(view);

                dialog.show();
                return false;
            }
        });
    }
    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    //刷新函数
    public void refreshData(List<Note> notes)
    {
        this.mNoteList=notes;
        //通知数据刷新了
        //这个代码很重要，给我研究了好几天
        notifyDataSetChanged();
    }

    //删除数据
    public void removeData(int pos)
    {
        mNoteList.remove(pos);
        notifyItemRemoved(pos);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        //控件声明
        TextView mTvTitle;
        TextView mTvContent;
        TextView mTvTime;
        ViewGroup rlContainer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mTvTitle=itemView.findViewById(R.id.tv_title);
            this.mTvContent=itemView.findViewById(R.id.tv_content);
            this.mTvTime=itemView.findViewById(R.id.tv_time);
            this.rlContainer=itemView.findViewById(R.id.rl_item_container);
        }
    }

    class MyGridViewHolder extends RecyclerView.ViewHolder{
        //控件声明
        TextView mTvTitle;
        TextView mTvContent;
        TextView mTvTime;
        ViewGroup rlContainer;

        public MyGridViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mTvTitle=itemView.findViewById(R.id.tv_title);
            this.mTvContent=itemView.findViewById(R.id.tv_content);
            this.mTvTime=itemView.findViewById(R.id.tv_time);
            this.rlContainer=itemView.findViewById(R.id.rl_item_container);
        }
    }
}
