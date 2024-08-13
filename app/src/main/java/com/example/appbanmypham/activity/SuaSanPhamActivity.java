package com.example.appbanmypham.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appbanmypham.R;
import com.example.appbanmypham.db.SharedPref;
import com.example.appbanmypham.model.Item;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class SuaSanPhamActivity extends AppCompatActivity {

    private Toolbar toolbar_chitietsp;
    private ImageView img_chitietsp;
    private EditText tv_chitietsp_name;
    private EditText tv_chitietsp_gia;
    private EditText tv_chitietsp_mota;
    private Button btn_themvaogiohang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_san_pham);
        initView();
        Item item = (Item) Objects.requireNonNull(getIntent().getExtras()).get("data");
        if (item != null) {
            // Hiển thị thông tin chi tiết của sản phẩm
            tv_chitietsp_name.setText(item.getName());
            tv_chitietsp_gia.setText(((int) item.getPrice()) + ""); // Định dạng giá sản phẩm
            tv_chitietsp_mota.setText(item.getDescription());

            // Sử dụng Glide để tải và hiển thị hình ảnh
            Glide.with(SuaSanPhamActivity.this)
                    .load(item.getImageUrl())
                    .into(img_chitietsp);
        }
        if (item != null) {
            findViewById(R.id.btn_themvaogiohang).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (TextUtils.isEmpty(tv_chitietsp_name.getText().toString()) || TextUtils.isEmpty(tv_chitietsp_gia.getText().toString()) || TextUtils.isEmpty(tv_chitietsp_mota.getText().toString())) {
                        Toast.makeText(SuaSanPhamActivity.this, "Hãy nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else {
                        HashMap<String, Object> data = new HashMap<>();
                        data.put("name", tv_chitietsp_name.getText().toString());
                        data.put("price", Double.parseDouble(tv_chitietsp_gia.getText().toString()));
                        data.put("description", tv_chitietsp_mota.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("items").child(item.getId())
                                .updateChildren(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(SuaSanPhamActivity.this, "Sửa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }

                }
            });
        }
    }

    private void initView() {
        toolbar_chitietsp = findViewById(R.id.toolbar_chitietsp);
        img_chitietsp = findViewById(R.id.img_chitietsp);
        tv_chitietsp_name = findViewById(R.id.tv_chitietsp_name);
        tv_chitietsp_gia = findViewById(R.id.tv_chitietsp_gia);
        tv_chitietsp_mota = findViewById(R.id.tv_chitietsp_mota);
        btn_themvaogiohang = findViewById(R.id.btn_themvaogiohang);
    }
}