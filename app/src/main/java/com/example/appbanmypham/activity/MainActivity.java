package com.example.appbanmypham.activity;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.appbanmypham.R;
import com.example.appbanmypham.adapter.ItemAdapter;
import com.example.appbanmypham.db.SharedPref;
import com.example.appbanmypham.fragment.DanhMucFragment;
import com.example.appbanmypham.fragment.GioHangFragment;
import com.example.appbanmypham.fragment.TaiKhoanFragment;
import com.example.appbanmypham.fragment.ThemSanPhamFragment;
import com.example.appbanmypham.fragment.TrangChuFragment;
import com.example.appbanmypham.model.Item;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ItemAdapter.OnItemClickListener {
    private RecyclerView rcvItem;
    private ItemAdapter itemAdapter;
    private List<Item> itemList;
    private DatabaseReference mDatabase;
    private ImageButton btn1, btn2, btn3;
    private GridLayoutManager gridLayoutManager;
    ViewFlipper viewFlipper;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    BottomNavigationView mnBottom;
    FragmentManager fragmentManager = getSupportFragmentManager();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_trang_chu);
        //tim kiem
        Toolbar toolbar = findViewById(R.id.toolbarArstyHome);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Xử lý khi người dùng nhấn Enter hoặc nút tìm kiếm
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Xử lý khi văn bản tìm kiếm thay đổi
                return false;
            }
        });
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở SearchActivity khi thanh tìm kiếm được nhấp
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                Log.d("MainActivity", "Intent: " + intent.toString());
            }
        });
        //danhmuc
        //viewflipper
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        //
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        rcvItem = findViewById(R.id.rcv_item);
        gridLayoutManager = new GridLayoutManager(this, 2);
        rcvItem.setLayoutManager(gridLayoutManager);
        itemList = new ArrayList<>();
        itemAdapter = new ItemAdapter(itemList);
        itemAdapter.setOnItemClickListener(this);
        rcvItem.setAdapter(itemAdapter);
//        ItemAdapter adapter = new ItemAdapter(getListItem());
//        rcvItem.setAdapter(adapter);
        //bắt sự kiện để chuyển đổi button
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        //fragment
        mnBottom = findViewById(R.id.bottomNav);
        if (SharedPref.read(SharedPref.IS_ADMIN, false)) {
            mnBottom.inflateMenu(R.menu.bottom_nav_menu_admin);
        } else {
            mnBottom.inflateMenu(R.menu.bottom_nav_menu);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Main");
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        // load len Fragment
        mnBottom.setOnItemSelectedListener(getListener());

        // Khởi tạo tham chiếu đến "items" trong Firebase Realtime Database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("items");

        // Đọc dữ liệu từ Firebase Realtime Database
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Xóa dữ liệu cũ trong itemList
                itemList.clear();
                // Duyệt qua từng item và thêm vào itemList
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    // Lấy dữ liệu từ dataSnapshot và chuyển đổi thành đối tượng Item
                    Item item = snapshot.getValue(Item.class);
                    if (item != null) {
                        itemList.add(item);
                    }
                }
                // Cập nhật RecyclerView
                itemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có
                Toast.makeText(MainActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onItemClick(int position) {
        // Lấy ID của sản phẩm được nhấp vào từ danh sách sản phẩm
        String productId = itemList.get(position).getId(); // Giả sử có một đối tượng sản phẩm là itemList và có phương thức getId() để lấy ID của sản phẩm

        // Tạo Intent để chuyển sang ChiTietSanPhamActivity và truyền ID của sản phẩm
        Intent intent = new Intent(MainActivity.this, ChiTietSanPhamActivity.class);
        intent.putExtra("id", productId);
        startActivity(intent);
    }

    //anh xa
    private void initUi() {

    }

    @NonNull
    private NavigationBarView.OnItemSelectedListener getListener() {
        return new NavigationBarView.OnItemSelectedListener() {
            Fragment fmNew;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                // Thêm kiểm tra nếu đang ở TrangChuFragment, không thực hiện replace Fragment
                // Kiểm tra xem fragment hiện tại có phải là DanhMucFragment không
                if (getSupportFragmentManager().findFragmentById(R.id.main_frame) instanceof DanhMucFragment && itemId == R.id.navgigation_danhmuc) {
                    return true;
                }

                // Thêm kiểm tra nếu đang ở TrangChuFragment, không thực hiện replace Fragment
                if (getSupportFragmentManager().findFragmentById(R.id.main_frame) instanceof TrangChuFragment && itemId == R.id.navigation_home) {
                    return true;
                }

                // Xóa Fragment hiện tại trước khi thêm Fragment mới
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_frame);
                if (currentFragment != null) {
                    getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
                }
                // Xóa hết tất cả các fragment khỏi Back Stack
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                if (itemId == R.id.navigation_home) {
                    loadFragment(new TrangChuFragment(), "Trang chủ");
                } else if (itemId == R.id.navgigation_danhmuc) {
                    loadFragment(new DanhMucFragment(), "Danh mục");
                } else if (itemId == R.id.navigation_giohang) {
                    loadFragment(new GioHangFragment(), "Giỏ hàng");
                } else if (itemId == R.id.navigation_account) {
                    loadFragment(new TaiKhoanFragment(), "Tài khoản");
                } else if (itemId == R.id.navigation_themSP) {
                    startActivity(new Intent(MainActivity.this, ThemSanPhamFragment.class));
                }

                return true;
            }
        };
    }

    void loadFragment(Fragment fmNew, String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fmNew).commit();
    }

    //bat su kien chuyen tiep trong button bang position itemid
    @Override
    public void onClick(View v) {
        int itemId;
        if (v.getId() == R.id.btn1) {
            itemId = 0;
        } else if (v.getId() == R.id.btn2) {
            itemId = 10;
        } else if (v.getId() == R.id.btn3) {
            itemId = 20;
        } else {
            // Nếu id của view không phải là btn_mypham1, btn_mypham2, hoặc btn_mypham3, không làm gì cả
            return;
        }
        scrollToItem(itemId);
    }

    private void scrollToItem(int index) {
        if (gridLayoutManager == null) {
            return;
        }
        gridLayoutManager.scrollToPositionWithOffset(index, 0);
    }

    @Override
    public void onDelete(int position) {
        if (SharedPref.read(SharedPref.IS_ADMIN, false)) {
            new AlertDialog.Builder(this).setMessage("Bạn có muốn xóa không ?").setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseDatabase.getInstance().getReference().child("items").child(itemList.get(position).getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(MainActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            itemList.remove(position);
                            itemAdapter.notifyItemRemoved(position);
                        }
                    });
                }
            }).setNegativeButton("Không", (dialog, which) -> {
            }).setIcon(android.R.drawable.ic_dialog_alert).show();
        }
    }
}
