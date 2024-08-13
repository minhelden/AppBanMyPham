package com.example.appbanmypham.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appbanmypham.R;
import com.example.appbanmypham.activity.SuaSanPhamActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class ThemSanPhamFragment extends AppCompatActivity {

    private TextInputEditText linkanh;
    private TextInputEditText ten;
    private TextInputEditText mota;
    private TextInputEditText gia;
    private Spinner spinner;
    int type = -1;
    List<Integer> list;

    public ThemSanPhamFragment() {
        // Required empty public constructor
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_them_san_pham);
        initView();
        list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        list.add(10);
        list.add(11);
        list.add(12);
        list.add(13);
        list.add(14);
        list.add(15);
        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);

        spinner.setAdapter(spinnerAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                type = list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        spinner.setAdapter(spinnerAdapter);
       findViewById(R.id.them).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(Objects.requireNonNull(linkanh.getText()).toString()) ||
                        TextUtils.isEmpty(Objects.requireNonNull(ten.getText()).toString()) || TextUtils.isEmpty(Objects.requireNonNull(gia.getText()).toString())
                        || TextUtils.isEmpty(Objects.requireNonNull(mota.getText()).toString())) {
                    Toast.makeText(ThemSanPhamFragment.this, "Hãy nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (type == -1) {
                    Toast.makeText(ThemSanPhamFragment.this, "Chưa chọn ", Toast.LENGTH_SHORT).show();
                } else {
                    String current = System.currentTimeMillis() + "";
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("description", mota.getText().toString());
                    map.put("price", Double.parseDouble(gia.getText().toString()));
                    map.put("type", type);
                    map.put("id", current);
                    map.put("imageUrl", linkanh.getText().toString());
                    map.put("name", ten.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("items").child(current).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(ThemSanPhamFragment.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }



    private void initView() {
        linkanh =findViewById(R.id.linkanh);
        ten = findViewById(R.id.ten);
        mota =findViewById(R.id.mota);
        gia = findViewById(R.id.gia);
        spinner = findViewById(R.id.spinner);
    }
}