package com.cn.travel.activity.first.food;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

import com.cn.travle.R;


public class CommentAdapter extends BaseAdapter {

    Context context;
    //List<Comment> data;
    ArrayList<Comment1> data1;

    public CommentAdapter(Context c, ArrayList<Comment1> data1){
        this.context = c;
        this.data1 = data1;
    }

    @Override
    public int getCount() {
        return data1.size();
    }

    @Override
    public Object getItem(int i) {
        return data1.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        // 重用convertView
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.foodlist_comment_item, null);
            holder.comment_name = (TextView) convertView.findViewById(R.id.comment_name);
            holder.comment_content = (TextView) convertView.findViewById(R.id.comment_content);
            holder.comment_time = (TextView) convertView.findViewById(R.id.comment_time);
            

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        // 适配数据
        holder.comment_name.setText(data1.get(i).getUsername());
        holder.comment_content.setText(data1.get(i).getMessage());
        holder.comment_time.setText( data1.get(i).getTime());
        return convertView;
    }

    /**
     * 添加�?��评论,刷新列表
     * @param comment
     */
    public void addComment(Comment1 comment){
        data1.add(comment);
        notifyDataSetChanged();
    }

    /**
     * 静�?类，便于GC回收
     */
    public static class ViewHolder{
        TextView comment_name;
        TextView comment_content;
        TextView comment_time;
    }
}
