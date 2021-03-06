package com.example.asm.AdapterXoaDL;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asm.Database.Database;
import com.example.asm.R;
import com.example.asm.model.Lop;

import java.util.List;

public class AdapterXoaLop extends BaseAdapter {
    Context context;
    List<Lop>list;
    Database db;

    public AdapterXoaLop(Context context, List<Lop> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder{
        TextView txtXoaMa,txtXoaTen;
        ImageView imgXoa;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view==null){
            holder= new ViewHolder();
            db = new Database(context);
            view = LayoutInflater.from(context).inflate(R.layout.item_xoa_dl_lop,viewGroup,false);
            holder.txtXoaMa =(TextView)view.findViewById(R.id.txt_xoa1);
            holder.txtXoaTen =(TextView)view.findViewById(R.id.txt_xoa2);
            holder.imgXoa =(ImageView)view.findViewById(R.id.img_xoa1);
            view.setTag(holder);
        }else {
            holder =(ViewHolder)view.getTag();
        }
        holder.txtXoaMa.setText(list.get(i).getMaLop());
        holder.txtXoaTen.setText(list.get(i).getTenLop());
        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteLop(list.get(i).getMaLop());
                Lop lop =list.get(i);
                list.remove(lop);
                notifyDataSetChanged();
            }
        });
        return view;
    }
}
