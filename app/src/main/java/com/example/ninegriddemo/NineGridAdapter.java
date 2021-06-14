package com.example.ninegriddemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class NineGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    OnAddPicturesListener listener;
    private MainActivity context;
    private List<Map<String,Object>> list;
    private View inflater;
    private static final int no = 0;
    private static final int yes = 1;
    public NineGridAdapter(MainActivity context, List<Map<String,Object>> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        int size=list.get(position).size();
        if(size==1)
        {
            return  no;
        }
        else
        {
            return  yes;
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==no)
        {
            inflater= LayoutInflater.from(context).inflate(R.layout.item_add,parent,false);
            RecyclerView.ViewHolder ViewHolder = new NineGridAdapter.ViewHolder(inflater);
            return ViewHolder;}
        else
        {
            inflater= LayoutInflater.from(context).inflate(R.layout.item_show,parent,false);
            RecyclerView.ViewHolder showHolder=new NineGridAdapter.showHolder(inflater);
            return showHolder;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType=getItemViewType(position);
        if(viewType==no)
        {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.add_Image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  listener.onAdd();
                }
            });
        }
        else if(viewType==yes)
        {
            showHolder showHolder=(NineGridAdapter.showHolder)holder;
            Glide.with(context).load(list.get(position).get("uri")).into(showHolder.show_image);
        }
        else
        {

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout add_Image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            add_Image=itemView.findViewById(R.id.add_newsImage);
        }


    }
    class showHolder extends RecyclerView.ViewHolder{
        ImageView show_image;
        public showHolder(@NonNull View itemView) {
            super(itemView);
            show_image=itemView.findViewById(R.id.show_newsImage);
        }
    }
    public void setOnAddPicturesListener(OnAddPicturesListener listener) {
        this.listener = listener;
    }
    private class PicturesClickListener implements View.OnClickListener {

        int position;

        public PicturesClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.add_newsImage:
                    //点击添加按钮
                    if (listener != null)
                        listener.onAdd();
                    break;
            }
        }
    }




}
