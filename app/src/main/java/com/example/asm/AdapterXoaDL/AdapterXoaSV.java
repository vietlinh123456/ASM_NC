package com.example.asm.AdapterXoaDL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asm.Database.Database;
import com.example.asm.R;
import com.example.asm.model.SinhVien;

import java.text.SimpleDateFormat;
import java.util.List;

public class AdapterXoaSV extends BaseAdapter {
    Context context;
    List<SinhVien> list;
    Database db;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public AdapterXoaSV(Context context, List<SinhVien> list) {
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
        TextView txtXoaTen,txtXoaNS;
        ImageView imgXoa;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view==null){
            holder = new ViewHolder();
            db = new Database(context);
            view = LayoutInflater.from(context).inflate(R.layout.item_xoa_dl_sv,viewGroup,false);
            holder.txtXoaTen =(TextView)view.findViewById(R.id.txt_xoa3);
            holder.txtXoaNS =(TextView)view.findViewById(R.id.txt_xoa4);
            holder.imgXoa =(ImageView)view.findViewById(R.id.img_xoa2);
            view.setTag(holder);
        }else {
            holder =(ViewHolder)view.getTag();
        }
        holder.txtXoaTen.setText(list.get(i).getTen());
        holder.txtXoaNS.setText(sdf.format(list.get(i).getNgay()));
        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteSV(list.get(i).getMaSV());
                SinhVien sv = list.get(i);
                list.remove(sv);
                notifyDataSetChanged();
            }
        });
        return view;
    }
}
