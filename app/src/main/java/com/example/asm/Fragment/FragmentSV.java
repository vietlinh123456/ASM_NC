package com.example.asm.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asm.AdapterXoaDL.AdapterXoaLop;
import com.example.asm.AdapterXoaDL.AdapterXoaSV;
import com.example.asm.Database.Database;
import com.example.asm.R;
import com.example.asm.model.Lop;
import com.example.asm.model.SinhVien;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class FragmentSV extends Fragment {
    public FragmentSV(){

    }

    private View view;
    ListView lv;
    List<SinhVien> list;
    AdapterXoaSV adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_xoa_dl_sv,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = view.findViewById(R.id.list_XoaDL_sv);
        list = new ArrayList<>();
        Database db = new Database(getContext());
        try {
            list = db.getAllSV();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        adapter = new AdapterXoaSV(getActivity(),list);
        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);
    }
}
