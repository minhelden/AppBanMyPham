package com.example.appbanmypham.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.appbanmypham.R;
import com.example.appbanmypham.adapter.LoaiSpAdapter;
import com.example.appbanmypham.adapter.SanPhamMoiAdapter;
import com.example.appbanmypham.fragment.DanhMucFragment;
import com.example.appbanmypham.model.Item;
import com.example.appbanmypham.model.SanPhamMoi;

import java.util.ArrayList;

public class FaceCareActivity extends AppCompatActivity {

    ImageView backBtn;
    RecyclerView rcvSanPham;
    SanPhamMoiAdapter sanPhamMoiAdapter;
    ArrayList<SanPhamMoi> arr_SanPham;
    Button btnLamSachDa, btnDuongAm, btnDacTri, btnChongNang, btnDuongMat, btnMatNa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_care);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FaceCareActivity.this, DanhMucFragment.class);
                startActivity(intent);
            }
        });
        setupRcv();
        setupBtnEvents();

        // Load dữ liệu mặc định khi Activity được khởi tạo
        loadDataLamSachDa();
    }
    private void setupRcv() {
        rcvSanPham = findViewById(R.id.rcvSanPham);
        arr_SanPham = new ArrayList<>();
        sanPhamMoiAdapter = new SanPhamMoiAdapter(this, arr_SanPham);
        rcvSanPham.setAdapter(sanPhamMoiAdapter);
        //rcvSanPham.setLayoutManager(new LinearLayoutManager(this));
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rcvSanPham.setLayoutManager(staggeredGridLayoutManager);
    }
    private void setupBtnEvents() {
        setupBtnLamSachDa();
        setupBtnDuongAm();
        setupBtnDacTri();
        setupBtnChongNang();
        setupBtnDuongMat();
        setupBtnMatNa();
    }
    private void setupBtnMatNa() {
        Button btnMatNa = findViewById(R.id.btnMatNa);
        btnMatNa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa dữ liệu cũ trước khi thêm dữ liệu mới
                arr_SanPham.clear();

                // Gọi phương thức loadData() để tải dữ liệu
                loadDataMatNa();

                // Thông báo cho adapter rằng dữ liệu đã thay đổi
                sanPhamMoiAdapter.notifyDataSetChanged();
            }
        });
    }
    private void setupBtnDuongMat() {
        Button btnDuongMat = findViewById(R.id.btnDuongMat);
        btnDuongMat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa dữ liệu cũ trước khi thêm dữ liệu mới
                arr_SanPham.clear();

                // Gọi phương thức loadData() để tải dữ liệu
                loadDataDuongMat();

                // Thông báo cho adapter rằng dữ liệu đã thay đổi
                sanPhamMoiAdapter.notifyDataSetChanged();
            }
        });
    }
    private void setupBtnChongNang() {
        Button btnChongNang = findViewById(R.id.btnChongNang);
        btnChongNang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa dữ liệu cũ trước khi thêm dữ liệu mới
                arr_SanPham.clear();

                // Gọi phương thức loadData() để tải dữ liệu
                loadDataChongNang();

                // Thông báo cho adapter rằng dữ liệu đã thay đổi
                sanPhamMoiAdapter.notifyDataSetChanged();
            }
        });
    }
    private void setupBtnDacTri() {
        Button btnDacTri = findViewById(R.id.btnDacTri);
        btnDacTri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa dữ liệu cũ trước khi thêm dữ liệu mới
                arr_SanPham.clear();

                // Gọi phương thức loadData() để tải dữ liệu
                loadDataDacTri();

                // Thông báo cho adapter rằng dữ liệu đã thay đổi
                sanPhamMoiAdapter.notifyDataSetChanged();
            }
        });
    }
    private void setupBtnDuongAm() {
        Button btnDuongAm = findViewById(R.id.btnDuongAm);
        btnDuongAm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa dữ liệu cũ trước khi thêm dữ liệu mới
                arr_SanPham.clear();

                // Gọi phương thức loadData() để tải dữ liệu
                loadDataDuongAm();

                // Thông báo cho adapter rằng dữ liệu đã thay đổi
                sanPhamMoiAdapter.notifyDataSetChanged();
            }
        });
    }
    private void setupBtnLamSachDa() {
        Button btnLamSachDa = findViewById(R.id.btnLamSachDa);
        btnLamSachDa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa dữ liệu cũ trước khi thêm dữ liệu mới
                arr_SanPham.clear();

                // Gọi phương thức loadData() để tải dữ liệu
                loadDataLamSachDa();

                // Thông báo cho adapter rằng dữ liệu đã thay đổi
                sanPhamMoiAdapter.notifyDataSetChanged();
            }
        });
    }
    private void loadDataLamSachDa() {
        arr_SanPham.add(new SanPhamMoi(1, "Sữa rửa mặt Cerave", R.drawable.srm_cerave, "400.000đ","", Item.Type_MYPHAM1));
        arr_SanPham.add(new SanPhamMoi(2, "Sữa rửa mặt La Roche-Posay", R.drawable.srm_larocheposay, "400.000đ","", Item.Type_MYPHAM1));
        arr_SanPham.add(new SanPhamMoi(3, "Sữa rửa mặt SVR", R.drawable.srm_svr, "400.000đ","", Item.Type_MYPHAM1));
        arr_SanPham.add(new SanPhamMoi(4, "Nước tẩy trang L'Oreal", R.drawable.tt_loreal, "200.000đ","", Item.Type_MYPHAM2));
        arr_SanPham.add(new SanPhamMoi(5, "Nước tẩy trang La Roche-Posay", R.drawable.tt_larocheposay, "400.000đ","", Item.Type_MYPHAM2));
        arr_SanPham.add(new SanPhamMoi(6, "Nước tẩy trang Bioderma", R.drawable.tt_bioderma, "350.000đ","", Item.Type_MYPHAM2));
    }
    private void loadDataDuongAm() {
        arr_SanPham.add(new SanPhamMoi(7, "Kem dưỡng ẩm La Roche-Posay", R.drawable.da_laroche_b5, "350.000đ","", Item.Type_MYPHAM3));
        arr_SanPham.add(new SanPhamMoi(8, "Gel dưỡng ẩm Neutrogena", R.drawable.da_neutrogena, "250.000đ","", Item.Type_MYPHAM3));
        arr_SanPham.add(new SanPhamMoi(9, "Kem dưỡng ẩm De Klairs", R.drawable.da_klairs, "300.000đ","", Item.Type_MYPHAM2));
        arr_SanPham.add(new SanPhamMoi(10, "Kem dưỡng ẩm Avenè", R.drawable.da_avene, "330.000đ","", Item.Type_MYPHAM2));
    }
    private void loadDataDacTri() {
        arr_SanPham.add(new SanPhamMoi(11, "Kem dưỡng hỗ trợ trị mụn La Roche-Posay", R.drawable.dt_larocheposay, "400.000đ","", Item.Type_MYPHAM1));
        arr_SanPham.add(new SanPhamMoi(12, "Kem giảm mụn Acnes", R.drawable.dt_acnes, "400.000đ","", Item.Type_MYPHAM1));
        arr_SanPham.add(new SanPhamMoi(13, "Gel dưỡng Megaduo giảm mụn, mờ thâm", R.drawable.dt_megadou, "400.000đ","", Item.Type_MYPHAM1));
        arr_SanPham.add(new SanPhamMoi(14, "Serum hỗ trợ giảm mụn Skin1004", R.drawable.dt_serum_skin1004, "200.00đ","", Item.Type_MYPHAM1));
    }
    private void loadDataChongNang() {
        arr_SanPham.add(new SanPhamMoi(15, "Kem chống nắng Acnessa", R.drawable.cn_acness, "400.000đ","", Item.Type_MYPHAM1));
        arr_SanPham.add(new SanPhamMoi(16, "Kem chống nắng La Roche-Posay", R.drawable.cn_laroche, "400.000đ","", Item.Type_MYPHAM1));
        arr_SanPham.add(new SanPhamMoi(17, "Kem chống nắng Skin1004", R.drawable.cn_skin1004, "400.000đ","", Item.Type_MYPHAM1));
        arr_SanPham.add(new SanPhamMoi(18, "Kem chống nắng L'Oreal", R.drawable.cn_loreal, "200.000đ","", Item.Type_MYPHAM2));
    }
    private void loadDataDuongMat() {
        arr_SanPham.add(new SanPhamMoi(23, "Kem dưỡng mắt 1", R.drawable.dm_1, "200.000đ","", Item.Type_MYPHAM1));
        arr_SanPham.add(new SanPhamMoi(24, "Kem dưỡng mắt 2", R.drawable.dm_2, "200.000đ","", Item.Type_MYPHAM1));
        arr_SanPham.add(new SanPhamMoi(25, "Kem dưỡng mắt 3", R.drawable.dm_3, "200.000đ","", Item.Type_MYPHAM1));
        arr_SanPham.add(new SanPhamMoi(26, "Mặt nạ dưỡng mắt", R.drawable.dm_4, "222.000đ","", Item.Type_MYPHAM2));
    }
    private void loadDataMatNa() {
        arr_SanPham.add(new SanPhamMoi(19, "Mặt nạ đất sét Some By Mi ", R.drawable.mn_somebymi, "400.000đ","", Item.Type_MYPHAM1));
        arr_SanPham.add(new SanPhamMoi(20, "Mặt nạ đất sét Re:p", R.drawable.mn_rep, "400.000đ","", Item.Type_MYPHAM1));
        arr_SanPham.add(new SanPhamMoi(21, "Mặt nạ giấy Hydrating", R.drawable.mn_vitamine, "400.000đ","", Item.Type_MYPHAM1));
        arr_SanPham.add(new SanPhamMoi(22, "Mặt nạ giấy Naruko", R.drawable.mn_naruko, "200.000đ","", Item.Type_MYPHAM2));
    }
}