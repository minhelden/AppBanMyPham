package com.example.appbanmypham.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.appbanmypham.R;
import com.example.appbanmypham.fragment.DanhMucFragment;
import com.example.appbanmypham.fragment.GioHangFragment;

public class ThanhToanActivity extends AppCompatActivity {
    RadioGroup radioGroupPayment;
    Button btnDathang;
    ImageView backBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        radioGroupPayment = findViewById(R.id.radioGroupPayment);
        backBtn = findViewById(R.id.backBtn);
        btnDathang = findViewById(R.id.btnDathang);

        // Sự kiện khi click nút "Back"
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                GioHangFragment gioHangFragment = new GioHangFragment();
//                transaction.replace(R.id.navigation_giohang, gioHangFragment);
//                transaction.addToBackStack(null); // Thêm Fragment vào Back Stack
//                transaction.commit();
//
//                // Trả về Fragment GioHang
//                getSupportFragmentManager().popBackStack();
//            }
//        });
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThanhToanActivity.this, GioHangFragment.class);
                startActivity(intent);
            }
        });

        // Sự kiện khi click nút "Đặt hàng"
        btnDathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ThanhToanActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // Xử lý sự kiện khi chọn phương thức thanh toán
        radioGroupPayment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Hủy chọn các RadioButton khác nếu một RadioButton được chọn
                RadioButton checkedRadioButton = findViewById(checkedId);
                for (int i = 0; i < group.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) group.getChildAt(i);
                    if (radioButton != checkedRadioButton) {
                        radioButton.setChecked(false);
                    }
                }
                String paymentMethod = checkedRadioButton.getText().toString();
                if (paymentMethod.equals("Thẻ tín dụng")) {

                    Toast.makeText(ThanhToanActivity.this, "Chọn thanh toán bằng thẻ tín dụng", Toast.LENGTH_SHORT).show();
                } else if (paymentMethod.equals("Thẻ ghi nợ")) {

                    Toast.makeText(ThanhToanActivity.this, "Chọn thanh toán bằng thẻ ghi nợ", Toast.LENGTH_SHORT).show();
                } else if (paymentMethod.equals("PayPal")) {

                    Toast.makeText(ThanhToanActivity.this, "Chọn thanh toán bằng PayPal", Toast.LENGTH_SHORT).show();
                } else if (paymentMethod.equals("Thanh toán khi nhận hàng (COD)")) {

                    Toast.makeText(ThanhToanActivity.this, "Chọn thanh toán bằng (COD)", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}