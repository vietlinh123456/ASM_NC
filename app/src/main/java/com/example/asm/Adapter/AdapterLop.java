package com.example.asm.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm.Database.Database;
import com.example.asm.R;
import com.example.asm.model.Lop;

import java.util.List;

public class AdapterLop extends BaseAdapter {
    Context context;
    List<Lop>list;
    Database db;

    public AdapterLop(Context context, List<Lop> list) {
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
        TextView txtMa,txtTen,txtStt;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view==null){
            holder = new ViewHolder();
        db = new Database(context);
        view = LayoutInflater.from(context).inflate(R.layout.item_ds_lop,viewGroup,false);
        holder.txtStt =(TextView)view.findViewById(R.id.txt_stt);
        holder.txtMa=(TextView)view.findViewById(R.id.txt_ma);
        holder.txtTen=(TextView)view.findViewById(R.id.txt_ten);
        view.setTag(holder);
        }else {
            holder=(ViewHolder)view.getTag();
        }
        holder.txtStt.setText(String.valueOf(i+1));
        holder.txtMa.setText(list.get(i).getMaLop());
        holder.txtTen.setText(list.get(i).getTenLop());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog =new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_update);

                Button save =(Button)dialog.findViewById(R.id.btn_update);
                final EditText edtT1 =(EditText)dialog.findViewById(R.id.edt_update1);
                final EditText edtT2 =(EditText)dialog.findViewById(R.id.edt_update2);
                final EditText edtT3 =(EditText)dialog.findViewById(R.id.edt_update3);

                edtT1.setEnabled(false);
                edtT3.setEnabled(false);

                final Lop lop = list.get(i);
                edtT1.setText(lop.getMaLop());
                edtT2.setText(lop.getTenLop());

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Lop lop1 = list.get(i);
                        lop1.setMaLop(edtT1.getText().toString());
                        lop1.setTenLop(edtT2.getText().toString());
                        db = new Database(context);
                        long capnhap =db.updateLop(lop1);
                        if (capnhap!=0){
                            Toast.makeText(context,"Update thành công",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            notifyDataSetChanged();
                        }else {
                            Toast.makeText(context,"Update không thành công",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });
        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
