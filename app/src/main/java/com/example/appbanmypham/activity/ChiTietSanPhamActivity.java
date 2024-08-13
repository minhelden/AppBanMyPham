package com.example.appbanmypham.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appbanmypham.R;
import com.example.appbanmypham.model.Cart;
import com.example.appbanmypham.model.Item;
import com.example.appbanmypham.model.User;
import com.example.appbanmypham.db.SharedPref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.Objects;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    TextView tensp, giasp, mota;
    Button btn_themvaogiohang;
    ImageView img_hinhanh;
    Toolbar toolbar;
    DatabaseReference mDatabase;
    FirebaseDatabase firebaseDatabase;

    User user;
    Long current = System.currentTimeMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        initView();
        ActionToolBar();
        user = new Gson().fromJson(SharedPref.read(SharedPref.USER_DATA, ""), User.class);
        // Khởi tạo Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();
        // Nhận productId từ Intent
        String productId = getIntent().getStringExtra("id");
        Item item = (Item) Objects.requireNonNull(getIntent().getExtras()).get("data");
        if (item != null) {
            // Hiển thị thông tin chi tiết của sản phẩm
            tensp.setText(item.getName());
            giasp.setText(formatPrice(item.getPrice())); // Định dạng giá sản phẩm
            mota.setText(item.getDescription());

            // Sử dụng Glide để tải và hiển thị hình ảnh
            Glide.with(ChiTietSanPhamActivity.this)
                    .load(item.getImageUrl())
                    .into(img_hinhanh);
        }

        btn_themvaogiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cart cartAdd = new Cart();
                cartAdd.setSoluong(1);
                if (item != null) {
                    cartAdd.setItem(item);
                    cartAdd.setIdsanpham(item.getId());
                }
                if (user != null) {
                    cartAdd.setUsername(user.getUsername());
                }
                FirebaseDatabase.getInstance().getReference().child("cart").orderByChild("username").equalTo(user.getUsername())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.hasChildren()) {
                                    boolean checkDaCoTrongGioHang = false;
                                    for (DataSnapshot appleSnapshot : snapshot.getChildren()) {
                                        Cart cart = appleSnapshot.getValue(Cart.class);
                                        if (cart != null) {
                                            if (cart.getIdsanpham().equals(cartAdd.getIdsanpham())) {
                                                Toast.makeText(ChiTietSanPhamActivity.this, "Sản phẩm đã có trong giỏ hàng", Toast.LENGTH_SHORT).show();
                                                checkDaCoTrongGioHang = true;
                                                break;
                                            }

                                        }
                                    }
                                    if (!checkDaCoTrongGioHang) {
                                        firebaseDatabase.getReference("cart").child(current.toString()).setValue(cartAdd).addOnCompleteListener(task -> {
                                            Toast.makeText(ChiTietSanPhamActivity.this, "Thêm giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                                        }).addOnFailureListener(e -> {
                                            Toast.makeText(ChiTietSanPhamActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                        });
                                    }
                                } else {
                                    firebaseDatabase.getReference("cart").child(current.toString()).setValue(cartAdd).addOnCompleteListener(task -> {
                                        Toast.makeText(ChiTietSanPhamActivity.this, "Thêm giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                                    }).addOnFailureListener(e -> {
                                        Toast.makeText(ChiTietSanPhamActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    });
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

            }
        });

    }

    private void initView() {
        tensp = findViewById(R.id.tv_chitietsp_name);
        giasp = findViewById(R.id.tv_chitietsp_gia);
        mota = findViewById(R.id.tv_chitietsp_mota);
        btn_themvaogiohang = findViewById(R.id.btn_themvaogiohang);
        img_hinhanh = findViewById(R.id.img_chitietsp);
        toolbar = findViewById(R.id.toolbar_chitietsp);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Hàm định dạng giá sản phẩm
    private String formatPrice(double price) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(price) + "đ";
    }
}
